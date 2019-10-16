package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupStoreDto.StoreAddressCreate;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreAddressUpdate;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreCreateDto;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreUpdate;
import com.focusteam.dealhunter.service.impl.StoreServices;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/api/stores/store" , method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody StoreCreateDto storeCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return storeServices.create(storeCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/-/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        return storeServices.getOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/{name}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneByNameUA(@PathVariable String name){
        return storeServices.getOneByNameUA(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        return storeServices.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/-/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody StoreUpdate storeUpdate, BindingResult bindingResult, HttpServletRequest request){
        return storeServices.update(id, storeUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/{name}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateByNameUA(@PathVariable String name, @Valid @RequestBody StoreUpdate storeUpdate, BindingResult bindingResult, HttpServletRequest request){
        return storeServices.updateByNameUA(name, storeUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/stores/store/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        return storeServices.delete(id, request);
    }

    // Request - Response Store Address

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/address" , method = RequestMethod.POST)
    public ResponseEntity<Object> createSA(@Valid @RequestBody StoreAddressCreate storeAddressCreate, BindingResult bindingResult, HttpServletRequest request){
        return storeServices.createSA(storeAddressCreate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/address/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneSA(@PathVariable long id){
        return storeServices.getOneSA(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/address", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllSA(){
        return storeServices.getAllSA();
    }

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/address/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSA(@PathVariable long id, @Valid @RequestBody StoreAddressUpdate storeAddressUpdate, BindingResult bindingResult, HttpServletRequest request){
        return storeServices.updateSA(id, storeAddressUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/stores/store/address/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSA(@PathVariable long id, HttpServletRequest request){
        return storeServices.deleteSA(id, request);
    }
}
