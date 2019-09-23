package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.AccountDto;
import com.focusteam.dealhunter.dto.AccountLoginDto;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @CrossOrigin
    @PostMapping("/unauthentic/account/login")
    public ResponseEntity<Object> login(@RequestBody AccountLoginDto accountLoginDto){
        ResponseEntity<Object> response = accountService.login(accountLoginDto);
        return response;
    }

    @CrossOrigin
    @PostMapping("/unauthentic/account/register")
    public ResponseEntity<Object> register(@RequestBody @Valid AccountDto accountDto, BindingResult bindingResult){
        ResponseEntity<Object> response = accountService.register(accountDto, bindingResult);
        return response;
    }


}
