package com.focusteam.dealhunter.service;

import com.focusteam.dealhunter.dto.TypeVoucherCreateDto;
import com.focusteam.dealhunter.dto.TypeVoucherDto;
import com.focusteam.dealhunter.dto.TypeVoucherUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public interface TypeVoucherService {
    public ResponseEntity<Object> create(@Valid @RequestBody TypeVoucherCreateDto typeVoucherCreateDto, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> getOne(@PathVariable long id);
    public ResponseEntity<Object> getAll();
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody TypeVoucherUpdate typeVoucherUpdate, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request);
}
