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
        return typeVoucherService.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/typeVoucher/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        return typeVoucherService.getOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/typeVoucher", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody TypeVoucherCreateDto typeVoucherCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return typeVoucherService.create(typeVoucherCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/typeVoucher/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id , @Valid @RequestBody TypeVoucherUpdate typeVoucherUpdate, BindingResult bindingResult, HttpServletRequest request){
        return typeVoucherService.update(id, typeVoucherUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/typeVoucher/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        return typeVoucherService.delete(id, request);
    }
}
