package com.focusteam.dealhunter.service;

import com.focusteam.dealhunter.dto.AccountInformationDto;
import com.focusteam.dealhunter.dto.AccountLoginDto;
import com.focusteam.dealhunter.dto.CredentialDto;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.Credential;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.repository.CityRepository;
import com.focusteam.dealhunter.repository.CredentialRepository;
import com.focusteam.dealhunter.rest.RESTLogin;
import com.focusteam.dealhunter.rest.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service("accountService")
public class DefaultAccountService implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CredentialRepository credentialRepository;

    @Override
    public ResponseEntity<Object> login(@RequestBody AccountLoginDto accountLoginDto) {
        Optional<Account> account = accountRepository.login(accountLoginDto.getUsername(), accountLoginDto.getPassword());
        if (account.isPresent()) {
            Account acc = account.get();

            Credential credential= new Credential();
            credential.setAccessToken("AAWEB-" + UUID.randomUUID().toString());
            credential.setRefreshToken("RFWEB-" + UUID.randomUUID().toString());
            credential.setToken(credential.getAccessToken());
            credential.setClientType("WEB");
            credential.setAccount(acc);
            acc.setCredential(credential);
            acc.setToken(credential.getAccessToken());

            accountRepository.save(acc);
            credentialRepository.save(credential);

            AccountInformationDto accountInformationDto = new AccountInformationDto(acc);
            CredentialDto credentialDto = new CredentialDto(credential);
            RESTLogin restLogin = new RESTLogin(accountInformationDto, credentialDto);

            System.out.println(accountInformationDto.getUsername());
            System.out.println(credentialDto.getAccessToken());

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.ACCEPTED.value())
                    .setData(restLogin)
                    .setMessage("User logged in successfully!").build(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .setStatus(HttpStatus.UNAUTHORIZED.value())
                .setData(StringUtils.EMPTY)
                .setMessage("Invalid email or password").build(), HttpStatus.UNAUTHORIZED);
    }

    @Override
    public Optional findByToken(String token) {
        Optional<Credential> cre = credentialRepository.findByToken(token);
        Credential credential = cre.get();
        Optional<Account> acc = accountRepository.findById(credential.getAccount().getId());
        if (acc.isPresent()){
            Account account = acc.get();
            AccountInformationDto accountInformationDto = new AccountInformationDto(account);
            CredentialDto credentialDto = new CredentialDto(credential);
            RESTLogin restLogin = new RESTLogin(accountInformationDto, credentialDto);
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
}
