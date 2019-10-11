package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupCityDto.CityCreateDto;
import com.focusteam.dealhunter.dto.groupCityDto.CityUpdate;
import com.focusteam.dealhunter.service.iml.CityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class CityController {
    @Autowired
    private CityServices cityServices;

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/cities/city", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        return cityServices.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/cities/city/-/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        return cityServices.getOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/cities/city/{name}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneByNameUA(@PathVariable String name){
        return cityServices.getOneByNameUA(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/cities/city", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody CityCreateDto cityCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return cityServices.create(cityCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/cities/city/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id , @Valid @RequestBody CityUpdate cityUpdate, BindingResult bindingResult, HttpServletRequest request){
        return cityServices.update(id, cityUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/cities/city/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        return cityServices.delete(id, request);
    }
}
