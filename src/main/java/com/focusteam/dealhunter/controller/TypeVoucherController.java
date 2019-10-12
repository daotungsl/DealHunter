package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherCreateDto;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherUpdate;
import com.focusteam.dealhunter.service.impl.TypeVoucherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class TypeVoucherController {
    @Autowired
    private TypeVoucherServices typeVoucherServices;

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/type-vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        return typeVoucherServices.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/type-vouchers/type-voucher/-/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        return typeVoucherServices.getOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/type-vouchers/type-voucher/{name}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneByNameUA(@PathVariable String name){
        return typeVoucherServices.getOneByNameUA(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-vouchers/type-voucher", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody TypeVoucherCreateDto typeVoucherCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return typeVoucherServices.create(typeVoucherCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-vouchers/type-voucher/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id , @Valid @RequestBody TypeVoucherUpdate typeVoucherUpdate, BindingResult bindingResult, HttpServletRequest request){
        return typeVoucherServices.update(id, typeVoucherUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-vouchers/type-voucher/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        return typeVoucherServices.delete(id, request);
    }
}
