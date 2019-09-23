package com.focusteam.dealhunter.service;

import com.focusteam.dealhunter.dto.AccountDto;
import com.focusteam.dealhunter.dto.AccountLoginDto;
import com.focusteam.dealhunter.dto.UserInformationDto;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.rest.RESTResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

public interface AccountService {

    public ResponseEntity<Object> login(@RequestBody AccountLoginDto accountLoginDto);

    public ResponseEntity<Object> register(@RequestBody @Valid AccountDto accountDto, BindingResult bindingResult);

    public Optional<Account> findByToken(String token);

    public ResponseEntity<Object> findById(Long id);

    public ResponseEntity<Object> updateInformation(@RequestBody @Valid UserInformationDto userInformationDto, BindingResult bindingResult, HttpServletRequest request);

    public boolean tokenForOneAccount(String token, String email);
}
