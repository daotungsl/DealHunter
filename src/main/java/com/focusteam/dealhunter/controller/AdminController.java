package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.service.impl.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AdminController {
    @Autowired
    private AdminServices adminServices;

    @CrossOrigin
    @RequestMapping(value = "/api/admin/account/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusAccount(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusAccount(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusStore(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusStore(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/store-address/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusStoreAddress(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusStoreAddress(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/voucher/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusVoucher(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusVoucher(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/transaction/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusTransaction(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusTransaction(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/accounts", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllAccount(HttpServletRequest request){
        return adminServices.getAllAccount( request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/accounts/{type}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllAccountByType(@PathVariable int type, HttpServletRequest request){
        return adminServices.getAllAccountByType( type,request);
    }
}
