package com.focusteam.dealhunter.service.impl;

import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherCreateDto;
import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public interface VoucherServices {
    public ResponseEntity<Object> create(@Valid @RequestBody VoucherCreateDto voucherCreateDto, BindingResult result, HttpServletRequest request);
    public ResponseEntity<Object> getOne(@PathVariable long id);
    public ResponseEntity<Object> getOneByNameUA(@PathVariable String name);
    public ResponseEntity<Object> getOneByStoreNameUA(@PathVariable String sNameUA, @PathVariable String vNameUA);
    public ResponseEntity<Object> getAll();
    public ResponseEntity<Object> getAllByStore(@PathVariable String name);
    public ResponseEntity<Object> getAllByTypeVoucher(@PathVariable String name);
    public ResponseEntity<Object> getAllByCity(@PathVariable String name);
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody VoucherUpdate voucherUpdate, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request);
}
