package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherCreateDto;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherUpdate;
import com.focusteam.dealhunter.service.iml.TypeVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class TypeVoucherController {
    @Autowired
    private TypeVoucherService typeVoucherService;

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/typeVoucher", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        ResponseEntity<Object> response = typeVoucherService.getAll();
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/typeVoucher/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        ResponseEntity<Object> response = typeVoucherService.getOne(id);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/typeVoucher", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody TypeVoucherCreateDto typeVoucherCreateDto, BindingResult bindingResult, HttpServletRequest request){
        ResponseEntity<Object> response = typeVoucherService.create(typeVoucherCreateDto, bindingResult, request);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/typeVoucher/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id , @Valid @RequestBody TypeVoucherUpdate typeVoucherUpdate, BindingResult bindingResult, HttpServletRequest request){
        ResponseEntity<Object> response = typeVoucherService.update(id, typeVoucherUpdate, bindingResult, request);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/typeVoucher/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        ResponseEntity<Object> response = typeVoucherService.delete(id, request);
        return response;
    }
}
