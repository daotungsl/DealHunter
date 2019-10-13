package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreCreateDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreUpdate;
import com.focusteam.dealhunter.service.impl.TypeStoreServices;
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
}
