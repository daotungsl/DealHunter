package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.*;
import com.focusteam.dealhunter.dto.groupAccountDto.AccountDto;
import com.focusteam.dealhunter.dto.groupAccountDto.AccountInformationDto;
import com.focusteam.dealhunter.dto.groupAccountDto.AccountLoginDto;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.Credential;
import com.focusteam.dealhunter.entity.UserInformation;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.repository.CredentialRepository;
import com.focusteam.dealhunter.repository.UserInformationRepository;
import com.focusteam.dealhunter.rest.RESTLogin;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.iml.AccountService;
import com.focusteam.dealhunter.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

@Service("accountService")
public class DefaultAccountService implements AccountService {
    HashMap<String, String> hashMap = new HashMap<>();

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CredentialRepository credentialRepository;

    @Autowired
    UserInformationRepository userInformationRepository;

    @Override
    public ResponseEntity<Object> login(@RequestBody AccountLoginDto accountLoginDto) {
        Optional<Account> account1 = accountRepository.findByUsername(accountLoginDto.getUsername());
        if (account1.isPresent()){
            Account account2 = account1.get();
            Credential credential = new Credential();
            //String salt = account2.getUserInformation().getSalt();
            accountLoginDto.setPassword(accountLoginDto.getPassword() + (new StringUtil().encryptMD5(account2.getUserInformation().getSalt())));
            //System.out.println(accountLoginDto.getPassword());
            if (accountLoginDto.getPassword().equals(account2.getPassword())){
                //System.out.println("Password equal!");
                if (account2.getToken() == null){
                    saveAccountCredential(account2, credential);
                }else {
                    Optional<Credential> cre = credentialRepository.findByToken(account2.getToken());
                    credential = cre.get();
                    saveAccountCredential(account2, credential);
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
    public ResponseEntity<Object> register(@RequestBody @Valid AccountDto accountDto, BindingResult bindingResult) {
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
                    .setMessage("Register data has errors !").build(), HttpStatus.FORBIDDEN);
        }
        if (!accountDto.getPassword().equals(accountDto.getRepassword())){
            hashMap.clear();
            hashMap.put("Repassword", "The repassword does not match the password !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Register data has errors !").build(), HttpStatus.FORBIDDEN);
        }
        Optional<Account> account = accountRepository.findByUsername(accountDto.getEmail());
        if (!account.isPresent()){
            Optional<UserInformation> userInformation = userInformationRepository.findByPhone(accountDto.getPhone());
            if (!userInformation.isPresent()){
                Account acc = new Account(accountDto);
                String salt = new StringUtil().randomString();
                acc.getUserInformation().setSalt(salt);
                acc.setPassword(new StringUtil().encryptMD5(accountDto.getPassword()) + new StringUtil().encryptMD5(salt));
                accountRepository.save(acc);
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.CREATED.value())
                        .setData(new AccountLoginDto(accountDto.getEmail(), accountDto.getPassword()))
                        .setMessage("Account register success !").build(), HttpStatus.CREATED);
            }
            hashMap.clear();
            hashMap.put("Phone", "A phone number can only register one account");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Register data has errors !").build(), HttpStatus.FORBIDDEN);
        }
        hashMap.clear();
        hashMap.put("Username", "This username has exist !");
        return new ResponseEntity<>(new RESTResponse.Error()
                .addErrors(hashMap)
                .setStatus(HttpStatus.FORBIDDEN.value())
                .setData("")
                .setMessage("Register data has errors !").build(), HttpStatus.FORBIDDEN);
    }

    @Override
    public Optional findByToken(String token) {
        Optional<Credential> cre = credentialRepository.findByToken(token);
        Credential credential = cre.get();
        Optional<Account> acc = accountRepository.findById(credential.getAccount().getId());
        if (acc.isPresent()){
            Account account = acc.get();
            User user = new User(account.getUsername(), account.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public ResponseEntity<Object> findById(Long id) {
        Optional<Account> acc = accountRepository.findById(id);
        if (acc.isPresent()){
            Account account = acc.get();
            AccountInformationDto accountInformationDto = new AccountInformationDto(account);
            CredentialDto credentialDto = new CredentialDto(account.getCredential());
            RESTLogin restLogin = new RESTLogin(accountInformationDto, credentialDto);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.FOUND.value())
                    .setData(restLogin)
                    .setMessage("Found !").build(), HttpStatus.FOUND);
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
            Optional<Account> acc = accountRepository.findByUsername(userInformationDto.getEmail());
            if (acc.isPresent()){
                Account account = acc.get();
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
                hashMap.clear();
                hashMap.put("Phone", "Can't change phone number !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.FORBIDDEN.value())
                        .setData("")
                        .setMessage("Update data has errors !").build(), HttpStatus.FORBIDDEN);
            }
            hashMap.clear();
            hashMap.put("Email", "Can't find account with this email !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setData("")
                    .setMessage("Update data has errors !").build(), HttpStatus.NOT_FOUND);
        }else {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Update data has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public boolean tokenForOneAccount(String token, String email) {
        Optional<Account> acc1 = accountRepository.findByUsername(email);
        Optional<Account> acc2 = accountRepository.findByTokenAccount(token);
        Account account1 = acc1.get();
        Account account2 = acc2.get();
        if (account1.equals(account2)){
            return true;
        }else {
            return false;
        }
    }

    private void saveAccountCredential(Account account, Credential credential){
        credential.setAccessToken("AAWEB-" + UUID.randomUUID().toString().toUpperCase());
        credential.setRefreshToken("RFWEB-" + UUID.randomUUID().toString().toUpperCase());
        credential.setToken(credential.getAccessToken());
        credential.setClientType("WEB");
        credential.setCreated(Calendar.getInstance().getTimeInMillis());
        credential.setExpired(credential.getCreated() + 86400000*3);
        credential.setUpdated(credential.getCreated());
        credential.setStatus(1);
        credential.setAccount(account);
        account.setCredential(credential);
        account.setToken(credential.getAccessToken());
        accountRepository.save(account);
        //credentialRepository.save(credential);
    }


}
