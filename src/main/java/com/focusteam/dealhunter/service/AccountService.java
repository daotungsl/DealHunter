package com.focusteam.dealhunter.service;

import com.focusteam.dealhunter.dto.AccountLoginDto;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.rest.RESTResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface AccountService {

    public ResponseEntity<Object> login(@RequestBody AccountLoginDto accountLoginDto);

    public Optional<Account> findByToken(String token);

    public ResponseEntity<Object> findById(Long id);
}
