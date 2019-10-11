package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreCreateDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreUpdate;
import com.focusteam.dealhunter.service.iml.TypeStoreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class TypeStoreController {
    @Autowired
    TypeStoreServices typeStoreServices;

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/type-stores", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        return typeStoreServices.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/type-stores/type-store/-/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        return typeStoreServices.getOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/type-store/type-store/{name}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneByNameUA(@PathVariable String name){
        return typeStoreServices.getOneByNameUA(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-stores/type-store", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody TypeStoreCreateDto typeStoreCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return typeStoreServices.create(typeStoreCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-store/type-store/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id , @Valid @RequestBody TypeStoreUpdate typeStoreUpdate, BindingResult bindingResult, HttpServletRequest request){
        return typeStoreServices.update(id, typeStoreUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-stores/type-store/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        return typeStoreServices.delete(id, request);
    }
}
