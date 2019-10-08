package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupAccountDto.AccountDto;
import com.focusteam.dealhunter.dto.groupAccountDto.AccountLoginDto;
import com.focusteam.dealhunter.service.iml.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @CrossOrigin
    //@PostMapping("/unauthentic/account/login")
    @RequestMapping(value = "/unauthentic/account/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody AccountLoginDto accountLoginDto, HttpServletRequest request){
        ResponseEntity<Object> response = accountService.login(accountLoginDto, request);
        return response;
    }

    @CrossOrigin
    //@PostMapping("/unauthentic/account/register")
    @RequestMapping(value = "/unauthentic/account/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody @Valid AccountDto accountDto, BindingResult bindingResult, HttpServletRequest request){
        ResponseEntity<Object> response = accountService.register(accountDto, bindingResult, request);
        return response;
    }


}
