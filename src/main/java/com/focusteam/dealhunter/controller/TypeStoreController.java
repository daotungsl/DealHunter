package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreCreateDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreUpdate;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherCreateDto;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherUpdate;
import com.focusteam.dealhunter.service.iml.TypeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class TypeStoreController {
    @Autowired
    TypeStoreService typeStoreService;

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/typeStore", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        return typeStoreService.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/typeStore/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        return typeStoreService.getOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/typeStore", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody TypeStoreCreateDto typeStoreCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return typeStoreService.create(typeStoreCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/typeStore/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id , @Valid @RequestBody TypeStoreUpdate typeStoreUpdate, BindingResult bindingResult, HttpServletRequest request){
        return typeStoreService.update(id, typeStoreUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/typeStore/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        return typeStoreService.delete(id, request);
    }
}
