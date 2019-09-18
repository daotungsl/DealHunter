package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.AccountDto;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.rest.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/so")
public class RegisterController {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> accountRegister(@RequestBody AccountDto accountDto) {
        Account account = new Account(accountDto);
        accountRepository.save(account);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.CREATED.value())
                .setData(new AccountDto(account))
                .setMessage("Success!").build(), HttpStatus.CREATED);
    }
}
