package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupAccountDto.*;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.Credential;
import com.focusteam.dealhunter.entity.UserInformation;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.repository.CredentialRepository;
import com.focusteam.dealhunter.repository.UserInformationRepository;
import com.focusteam.dealhunter.rest.RESTLogin;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.impl.AccountServices;
import com.focusteam.dealhunter.service.impl.EmailServices;
import com.focusteam.dealhunter.util.StringUtil;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Service("accountServices")
public class DefaultAccountServices implements AccountServices {
    private HashMap<String, String> hashMap = new HashMap<>();
    @Autowired
    Environment environment;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CredentialRepository credentialRepository;

    @Autowired
    UserInformationRepository userInformationRepository;

    @Autowired
    private EmailServices emailServices;

    @Override
    public ResponseEntity<Object> login(@RequestBody AccountLoginDto accountLoginDto, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByUsername(accountLoginDto.getUsername());
        if (accountOptional.isPresent()){
            Account account2 = accountOptional.get();
            Credential credential = new Credential();
            //String salt = account2.getUserInformation().getSalt();
            accountLoginDto.setPassword(accountLoginDto.getPassword() + (new StringUtil().encryptMD5(account2.getUserInformation().getSalt())));
            //System.out.println(accountLoginDto.getPassword());
            if (accountLoginDto.getPassword().equals(account2.getPassword())){
                //System.out.println("Password equal!");
                if (account2.getToken() == null){
                    saveAccountCredential(account2, credential, request);
                }else {
                    Optional<Credential> credentialOptional = credentialRepository.findByToken(account2.getToken());
                    credential = credentialOptional.get();
                    saveAccountCredential(account2, credential, request);
                }
                AccountInformationDto accountInformationDto = new AccountInformationDto(account2);
                CredentialDto credentialDto = new CredentialDto(credential);
                RESTLogin restLogin = new RESTLogin(accountInformationDto, credentialDto);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.ACCEPTED.value())
                        .setData(restLogin)
                        .setMessage("User logged in successfully!").build(), HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("Invalid email or password").build(), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .setStatus(HttpStatus.UNAUTHORIZED.value())
                .setData(StringUtils.EMPTY)
                .setMessage("Invalid email or password").build(), HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<Object> register(@RequestBody @Valid AccountDto accountDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            hashMap.clear();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError f: fieldErrors
                 ) {
                hashMap.put(f.getField(), f.getDefaultMessage());
            }
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData(StringUtils.EMPTY)
                    .setMessage("Register data has errors !").build(), HttpStatus.FORBIDDEN);
        }
        if (!accountDto.getPassword().equals(accountDto.getRepassword())){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData(StringUtils.EMPTY)
                    .setMessage("The re-password does not match the password !").build(), HttpStatus.FORBIDDEN);
        }
        Optional<Account> accountOptional = accountRepository.findByUsername(accountDto.getEmail());
        if (!accountOptional.isPresent()){
            Optional<UserInformation> userInformationOptional = userInformationRepository.findByPhone(accountDto.getPhone());
            if (!userInformationOptional.isPresent()){
                Account acc = new Account(accountDto);
                String salt = new StringUtil().randomString();
                acc.getUserInformation().setSalt(salt);
                acc.setPassword(new StringUtil().encryptMD5(accountDto.getPassword()) + new StringUtil().encryptMD5(salt));
                accountRepository.save(acc);

                String callBack = "http://13.76.164.246:8080/unauthentic/account/"+ accountDto.getEmail() + "/confirm/1/1";
                String messageBody = "Chúng tôi gửi email này ngay sau khi bạn đăng ký tài khoản tại DealHunter. Email này của bạn sẽ được sử dụng trong trường hợp ban quên mật khẩu truy cập tài khoản của mình. Trước tiên bạn cần xác minh email này nhé !";
                emailServices.sendMessageWithAttachment(accountDto.getEmail(), "Xác nhận email", accountDto.getEmail(), callBack, messageBody);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.CREATED.value())
                        .setData(new AccountLoginDto(accountDto.getEmail(), accountDto.getPassword(), request.getHeader("user-agent")))
                        .setMessage("Account register success !").build(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("A phone number can only register one account").build(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .setStatus(HttpStatus.FORBIDDEN.value())
                .setData("")
                .setMessage("This username has exist !").build(), HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<Object> storeRegister(@RequestBody @Valid AccountStoreDto accountStoreDto, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByUsername(accountStoreDto.getEmail());
        Optional<UserInformation> userInformationOptional = userInformationRepository.findByPhone(accountStoreDto.getPhone());
        if (bindingResult.hasErrors()){
            hashMap.clear();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError f: fieldErrors
            ) {
                hashMap.put(f.getField(), f.getDefaultMessage());
            }
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData(StringUtils.EMPTY)
                    .setMessage("Register data has errors !").build(), HttpStatus.FORBIDDEN);
        }else if (!accountStoreDto.getPassword().equals(accountStoreDto.getRepassword())){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData(StringUtils.EMPTY)
                    .setMessage("The re-password does not match the password !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("This username has exist !").build(), HttpStatus.FORBIDDEN);
        } else if (userInformationOptional.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("A phone number can only register one account").build(), HttpStatus.FORBIDDEN);
        }else {
            Account account = new Account(accountStoreDto);
            String salt = new StringUtil().randomString();
            account.getUserInformation().setSalt(salt);
            account.setPassword(new StringUtil().encryptMD5(accountStoreDto.getPassword()) + new StringUtil().encryptMD5(salt));
            accountRepository.save(account);



            String callBack = "http://13.76.164.246:8080/unauthentic/account/"+ accountStoreDto.getEmail() + "/confirm/1/1";
            String mesageBody = "Chúng tôi gửi email này ngay sau khi bạn đăng ký tài khoản tại DealHunter. Email này của bạn sẽ được sử dụng trong trường hợp ban quên mật khẩu truy cập tài khoản của mình. Trước tiên bạn cần xác minh email này nhé !";

            emailServices.sendMessageWithAttachment(accountStoreDto.getEmail(), "Xác nhận email", accountStoreDto.getFullName(), callBack, mesageBody);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.CREATED.value())
                    .setData(new AccountLoginDto(accountStoreDto.getEmail(), accountStoreDto.getPassword(), request.getHeader("user-agent")))
                    .setMessage("Store account register success !").build(), HttpStatus.CREATED);
        }
    }


    @Override
    public Optional findByToken(String token) {
        Optional<Credential> credentialOptional = credentialRepository.findByToken(token);
        if (credentialOptional.isPresent()){
            Credential credential = credentialOptional.get();
            Optional<Account> accountOptional = accountRepository.findById(credential.getAccount().getId());
            if (accountOptional.isPresent()){
                Account account = accountOptional.get();
                User user = new User(account.getUsername(), account.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
                return Optional.of(user);
            }else {
                return Optional.empty();
            }
        }else {
            return Optional.empty();
        }
    }

    @Override
    public ResponseEntity<Object> findById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            AccountInformationDto accountInformationDto = new AccountInformationDto(account);
            CredentialDto credentialDto = new CredentialDto(account.getCredential());
            RESTLogin restLogin = new RESTLogin(accountInformationDto, credentialDto);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(restLogin)
                    .setMessage("Found !").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setData(StringUtils.EMPTY)
                .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> updateInformation(@RequestBody @Valid UserInformationDto userInformationDto, BindingResult bindingResult, HttpServletRequest request) {
        if (tokenForOneAccount(request.getHeader("Authorization"), userInformationDto.getEmail())){
            if (bindingResult.hasErrors()){
                hashMap.clear();
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError f: fieldErrors
                ) {
                    hashMap.put(f.getField(), f.getDefaultMessage());
                }
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.FORBIDDEN.value())
                        .setData("")
                        .setMessage("User information data has errors !").build(), HttpStatus.FORBIDDEN);
            }
            Optional<Account> accountOptional = accountRepository.findByUsername(userInformationDto.getEmail());
            if (accountOptional.isPresent()){
                Account account = accountOptional.get();
                if (userInformationDto.getPhone() != StringUtils.EMPTY && account.getUserInformation().getPhone().equals(userInformationDto.getPhone())){
                    account.getUserInformation().setFullName(userInformationDto.getFullName());
                    account.getUserInformation().setGender(userInformationDto.getGender());
                    account.getUserInformation().setBirthday(userInformationDto.getBirthday());
                    account.getUserInformation().setAvatar(userInformationDto.getAvatar());
                    account.getUserInformation().setAddress(userInformationDto.getAddress());
                    account.getUserInformation().setUpdated(Calendar.getInstance().getTimeInMillis());

                    accountRepository.save(account);

                    return new ResponseEntity<>(new RESTResponse.Success()
                            .setStatus(HttpStatus.OK.value())
                            .setData(new UserInformationDto(account))
                            .setMessage("Update user information data success !").build(), HttpStatus.OK);
                }
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(null)
                        .setStatus(HttpStatus.FORBIDDEN.value())
                        .setData("")
                        .setMessage("Can't change phone number !").build(), HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(null)
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setData("")
                    .setMessage("Can't find account with this email !").build(), HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(null)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public boolean tokenForOneAccount(String token, String email) {
        Optional<Account> accountOptional = accountRepository.findByUsername(email);
        Optional<Account> accountOptional1 = accountRepository.findByTokenAccount(token);
        Account account1 = accountOptional.get();
        Account account2 = accountOptional1.get();
        if (account1.equals(account2)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean tokenForOneAccountId(String token, long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        Optional<Account> accountOptional1 = accountRepository.findByTokenAccount(token);
        Account account1 = accountOptional.get();
        Account account2 = accountOptional1.get();
        if (account1.equals(account2)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public HttpStatus confirmEmail(String email, int confirm, int status) {
        Optional<Account> accountOptional = accountRepository.findByUsername(email);
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            account.setConfirmEmail(confirm);
            account.setStatus(status);
            accountRepository.save(account);
            return HttpStatus.OK;
        }else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @Override
    public ResponseEntity<Object> sendMailConfirm(String email, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(null)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
        }else if (!accountOptional.get().getUsername().equalsIgnoreCase(email)){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(null)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
        }else {
            String callBack = "http://" + InetAddress.getLoopbackAddress().getHostName() + ":" + environment.getProperty("server.port") + "/unauthentic/account/" + email + "/confirm/1/1";
            String messageBody ="Chúng tôi gửi email này ngay sau khi bạn đăng ký tài khoản tại DealHunter. Email này của bạn sẽ được sử dụng trong trường hợp ban quên mật khẩu truy cập tài khoản của mình. Trước tiên bạn cần xác minh email này nhé !";
            emailServices.sendMessageWithAttachment(email, "Xác nhận email", email, callBack, messageBody);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData("Email has been confirmed")
                    .setMessage("Success !").build(), HttpStatus.OK);
        }
    }

    private void saveAccountCredential(Account account, Credential credential, HttpServletRequest request){
        credential.setAccessToken("AAWEB-" + UUID.randomUUID().toString().toUpperCase());
        credential.setRefreshToken("RFWEB-" + UUID.randomUUID().toString().toUpperCase());
        credential.setToken(credential.getAccessToken());
        credential.setClientType("WEB");
        credential.setCreated(Calendar.getInstance().getTimeInMillis());
        credential.setExpired(credential.getCreated() + 86400000*3);
        credential.setUpdated(credential.getCreated());
        credential.setClientType(request.getHeader("user-agent"));
        credential.setStatus(1);
        credential.setAccount(account);
        account.setCredential(credential);
        account.setToken(credential.getAccessToken());
        accountRepository.save(account);
        //credentialRepository.save(credential);
    }
}
