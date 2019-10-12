package com.focusteam.dealhunter.service.impl;

import com.focusteam.dealhunter.dto.groupAccountDto.AccountDto;
import com.focusteam.dealhunter.dto.groupAccountDto.AccountLoginDto;
import com.focusteam.dealhunter.dto.groupAccountDto.AccountStoreDto;
import com.focusteam.dealhunter.dto.groupAccountDto.UserInformationDto;
import com.focusteam.dealhunter.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

public interface AccountServices {

    public ResponseEntity<Object> login(@RequestBody AccountLoginDto accountLoginDto, HttpServletRequest request);

    public ResponseEntity<Object> register(@RequestBody @Valid AccountDto accountDto, BindingResult bindingResult, HttpServletRequest request);

    public ResponseEntity<Object> storeRegister(@Valid @RequestBody AccountStoreDto accountStoreDto, BindingResult bindingResult, HttpServletRequest request);

    public Optional<Account> findByToken(String token);

    public ResponseEntity<Object> findById(Long id);

    public ResponseEntity<Object> updateInformation(@RequestBody @Valid UserInformationDto userInformationDto, BindingResult bindingResult, HttpServletRequest request);

    public boolean tokenForOneAccount(String token, String email);

    public boolean tokenForOneAccountId(String token, long id);

    public HttpStatus confirmEmail(@PathVariable String email, @PathVariable int confirm, @PathVariable int status);

    public ResponseEntity<Object> sendMailConfirm(@PathVariable String email, HttpServletRequest request);
}
