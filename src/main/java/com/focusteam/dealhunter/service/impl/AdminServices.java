package com.focusteam.dealhunter.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

public interface AdminServices {
    public ResponseEntity<Object> statusAccount(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusStore(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusStoreAddress(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusVoucher(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> statusTransaction(@PathVariable long id, @PathVariable int status, HttpServletRequest request);

    public ResponseEntity<Object> getAllAccount(HttpServletRequest request);

    public ResponseEntity<Object> getAllAccountByType(@PathVariable int type, HttpServletRequest request);

}
