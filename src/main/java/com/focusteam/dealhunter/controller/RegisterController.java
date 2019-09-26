package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.CityDto;
import com.focusteam.dealhunter.entity.City;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.repository.CityRepository;
import com.focusteam.dealhunter.rest.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/so")
public class RegisterController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CityRepository cityRepository;

    @CrossOrigin
    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public ResponseEntity<Object> getListCity(){
        List<City> cities = cityRepository.findAll();
        if (cities != null){
            List<CityDto> cityDtos = new ArrayList<>();
            CityDto cityDto = null;
            for (City city: cities) {
                cityDto = new CityDto(city);
                cityDtos.add(cityDto);
            }
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.CREATED.value())
                    .setData(cityDtos)
                    .setMessage("Success!").build(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .setStatus(HttpStatus.FORBIDDEN.value())
                .setData("")
                .setMessage("Error!").build(), HttpStatus.FORBIDDEN);
    }
}
