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

    @CrossOrigin
    @RequestMapping(value = "/api/guest/{email}/history/transactions", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllWithOneAccount(@PathVariable String email, HttpServletRequest request){
        return transactionServices.getAllWithOneAccount(email, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/guest/history/transactions/transaction/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneById(@PathVariable long id, HttpServletRequest request){
        return transactionServices.getOneById(id, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/transactions", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(HttpServletRequest request){
        return transactionServices.getAll(request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/stores/{name}/transactions", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllByStore(@PathVariable String name, HttpServletRequest request){
        return transactionServices.getAllByStore(name, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/stores/{name}/{id}/transactions", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllByStoreAddress(@PathVariable String name, @PathVariable long id, HttpServletRequest request){
        return transactionServices.getAllByStoreAddress(id ,name, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/transactions/transaction/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        return transactionServices.delete(id, request);
    }
}
