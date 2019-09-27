package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupCityDto.CityCreateDto;
import com.focusteam.dealhunter.dto.groupCityDto.CityUpdate;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.repository.CityRepository;
import com.focusteam.dealhunter.service.iml.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Service("cityService")
public class DefaultCityService implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public ResponseEntity<Object> create(@Valid CityCreateDto cityCreateDto, BindingResult bindingResult, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getOne(long id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(long id, @Valid CityUpdate cityUpdate, BindingResult bindingResult, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(long id, HttpServletRequest request) {
        return null;
    }
}
