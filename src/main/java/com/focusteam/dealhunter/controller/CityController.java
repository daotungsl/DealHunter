package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupCityDto.CityCreateDto;
import com.focusteam.dealhunter.dto.groupCityDto.CityUpdate;
import com.focusteam.dealhunter.service.impl.CityServices;
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
}
