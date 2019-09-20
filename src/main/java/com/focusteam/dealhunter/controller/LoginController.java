package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.AccountLoginDto;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private AccountService accountService;

    @CrossOrigin
    @PostMapping("/token")
    public ResponseEntity<Object> getToken(@RequestBody AccountLoginDto accountLoginDto){
        ResponseEntity<Object> response = accountService.login(accountLoginDto);
        return response;
    }
}
