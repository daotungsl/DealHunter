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
}
