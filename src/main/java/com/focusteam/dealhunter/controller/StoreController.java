package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupStoreDto.StoreAddressCreate;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreAddressUpdate;
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

    // Request - Response Store

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

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        ResponseEntity<Object> response = storeServices.delete(id, request);
        return response;
    }

    // Request - Response Store Address

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/address" , method = RequestMethod.POST)
    public ResponseEntity<Object> createSA(@Valid @RequestBody StoreAddressCreate storeAddressCreate, BindingResult bindingResult, HttpServletRequest request){
        ResponseEntity<Object> response = storeServices.createSA(storeAddressCreate, bindingResult, request);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/address/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneSA(@PathVariable long id){
        ResponseEntity<Object> response = storeServices.getOneSA(id);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/address", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllSA(){
        ResponseEntity<Object> response = storeServices.getAllSA();
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/address/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSA(@PathVariable long id, @Valid @RequestBody StoreAddressUpdate storeAddressUpdate, BindingResult bindingResult, HttpServletRequest request){
        ResponseEntity<Object> response = storeServices.updateSA(id, storeAddressUpdate, bindingResult, request);
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/address/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSA(@PathVariable long id, HttpServletRequest request){
        ResponseEntity<Object> response = storeServices.deleteSA(id, request);
        return response;
    }
}
