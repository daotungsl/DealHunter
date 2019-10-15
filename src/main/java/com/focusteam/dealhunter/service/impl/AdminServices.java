package com.focusteam.dealhunter.service.impl;

import com.google.gson.internal.$Gson$Preconditions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

public interface AdminServices {
    // STATUS

    public ResponseEntity<Object> statusAccount(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusStore(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusStoreAddress(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusVoucher(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusTransaction(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> getAllAccountByType(@PathVariable int type, HttpServletRequest request);

    public ResponseEntity<Object> statusTypeStore(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusTypeVoucher(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusCity(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    // GET ALL

    public ResponseEntity<Object> getAllAccount(HttpServletRequest request);

    public ResponseEntity<Object> getAllStore(HttpServletRequest request);

    public ResponseEntity<Object> getAllTypeStore(HttpServletRequest request);

    public ResponseEntity<Object> getAllTypeVoucher(HttpServletRequest request);

    public ResponseEntity<Object> getAllStoreAddress(HttpServletRequest request);

    public ResponseEntity<Object> getAllTransaction(HttpServletRequest request);

    // GET ONE

    public ResponseEntity<Object> getOneStore(@PathVariable long id,  HttpServletRequest request);
    public ResponseEntity<Object> getOneTypeVoucher(@PathVariable long id,  HttpServletRequest request);
    public ResponseEntity<Object> getOneTypeStore(@PathVariable long id,  HttpServletRequest request);
    public ResponseEntity<Object> getOneStoreAddress(@PathVariable long id,  HttpServletRequest request);
    public ResponseEntity<Object> getOneTransaction(@PathVariable long id,  HttpServletRequest request);
    public ResponseEntity<Object> getOneAccount(@PathVariable long id,  HttpServletRequest request);
}
