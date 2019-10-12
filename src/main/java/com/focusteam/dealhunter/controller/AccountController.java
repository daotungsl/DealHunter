package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupAccountDto.AccountDto;
import com.focusteam.dealhunter.dto.groupAccountDto.AccountLoginDto;
import com.focusteam.dealhunter.dto.groupAccountDto.AccountStoreDto;
import com.focusteam.dealhunter.service.impl.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AccountController {
    @Autowired
    private AccountServices accountServices;

    @CrossOrigin
    //@PostMapping("/unauthentic/account/login")
    @RequestMapping(value = "/unauthentic/account/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody AccountLoginDto accountLoginDto, HttpServletRequest request){
        return accountServices.login(accountLoginDto, request);
    }


    //Guest Register
    @CrossOrigin
    //@PostMapping("/unauthentic/account/register")
    @RequestMapping(value = "/unauthentic/account/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody @Valid AccountDto accountDto, BindingResult bindingResult, HttpServletRequest request){
        return accountServices.register(accountDto, bindingResult, request);
    }

    // Store Owner Register
    @CrossOrigin
    @RequestMapping(value = "/unauthentic/store/account/register", method = RequestMethod.POST)
    public ResponseEntity<Object> storeRegister(@RequestBody @Valid AccountStoreDto accountStoreDto, BindingResult bindingResult, HttpServletRequest request){
        return accountServices.storeRegister(accountStoreDto, bindingResult, request);
    }


    @CrossOrigin
    @RequestMapping(value = "/unauthentic/account/{email}/confirm/{confirm}/{status}", method = RequestMethod.GET)
    public HttpStatus confirmEmail(@PathVariable String email,@PathVariable int confirm , @PathVariable int status){
        return accountServices.confirmEmail(email, confirm ,status);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/account/{email}/send-confirm", method = RequestMethod.GET)
    public ResponseEntity<Object> sendConfirmEmail(@PathVariable String email, HttpServletRequest request){
        return accountServices.sendMailConfirm(email, request);
    }
}
