package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupCityDto.CityCreateDto;
import com.focusteam.dealhunter.dto.groupCityDto.CityUpdate;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherCreateDto;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherUpdate;
import com.focusteam.dealhunter.service.iml.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class CityController {
    @Autowired
    private CityService cityService;

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/city", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        return cityService.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/city/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        return cityService.getOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/city", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody CityCreateDto cityCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return cityService.create(cityCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/city/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id , @Valid @RequestBody CityUpdate cityUpdate, BindingResult bindingResult, HttpServletRequest request){
        return cityService.update(id, cityUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/city/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        return cityService.delete(id, request);
    }
}
