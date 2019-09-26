package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.UserInformationDto;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.service.iml.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @CrossOrigin
    @PutMapping("/api/account/user/update")
    public ResponseEntity<Object> userUpdate(@RequestBody @Valid UserInformationDto userInformationDto, BindingResult bindingResult, HttpServletRequest request){
        ResponseEntity<Object> response = accountService.updateInformation(userInformationDto, bindingResult, request);
        return response;
    }
}
