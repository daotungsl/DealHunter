package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionCreateDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreCreateDto;
import com.focusteam.dealhunter.service.iml.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class TransactionController {
    @Autowired
    TransactionServices transactionServices;

    @CrossOrigin
    @RequestMapping(value = "/api/guest/transactions/transaction", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody TransactionCreateDto transactionCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return transactionServices.create(transactionCreateDto, bindingResult, request);
    }
}
