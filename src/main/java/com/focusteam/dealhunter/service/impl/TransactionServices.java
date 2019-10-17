package com.focusteam.dealhunter.service.impl;

import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionCreateDto;
import com.google.gson.internal.$Gson$Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public interface TransactionServices {
    public ResponseEntity<Object> create(@Valid @RequestBody TransactionCreateDto transactionCreateDto, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> getOneById(@PathVariable long id, HttpServletRequest request);
    public ResponseEntity<Object> getAll(HttpServletRequest request);
    public ResponseEntity<Object> getAllWithOneAccount(@PathVariable String email, HttpServletRequest request);
    public ResponseEntity<Object> getAllByStore(@PathVariable String name, HttpServletRequest request);
    public ResponseEntity<Object> getAllByStoreAddress(@PathVariable long id,@PathVariable String name, HttpServletRequest request);

    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request);

    public ResponseEntity<Object> countByStore(@PathVariable long id, HttpServletRequest request);

    ResponseEntity<Object> countByStoreFromTo(@PathVariable long id, @PathVariable long from, @PathVariable long to, HttpServletRequest request);

    ResponseEntity<Object> countByStoreFromToDayString(@PathVariable long id, @PathVariable String from, @PathVariable String to, HttpServletRequest request);

    public HttpStatus confirmTransaction(@PathVariable long id, @PathVariable int status);
}
