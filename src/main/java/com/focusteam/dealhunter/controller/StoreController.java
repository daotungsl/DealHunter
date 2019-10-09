package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupStoreDto.StoreCreateDto;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreUpdate;
import com.focusteam.dealhunter.service.iml.StoreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class StoreController {
    @Autowired
    private StoreServices storeServices;

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store" , method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody StoreCreateDto storeCreateDto, BindingResult bindingResult, HttpServletRequest request){
        ResponseEntity<Object> response = storeServices.create(storeCreateDto, bindingResult, request);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        ResponseEntity<Object> response = storeServices.getOne(id);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        ResponseEntity<Object> response = storeServices.getAll();
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody StoreUpdate storeUpdate, BindingResult bindingResult, HttpServletRequest request){
        ResponseEntity<Object> response = storeServices.update(id, storeUpdate, bindingResult, request);
        return response;
    }
}
