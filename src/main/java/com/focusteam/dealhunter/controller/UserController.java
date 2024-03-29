package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupAccountDto.UserInformationDto;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.service.impl.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    AccountServices accountServices;
    @Autowired
    AccountRepository accountRepository;

    @CrossOrigin
    //@PutMapping("/api/account/user/update")
    @RequestMapping(value = "/api/account/user/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> userUpdate(@RequestBody @Valid UserInformationDto userInformationDto, BindingResult bindingResult, HttpServletRequest request){
        return accountServices.updateInformation(userInformationDto, bindingResult, request);
    }
}
