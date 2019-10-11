package com.focusteam.dealhunter.service.iml;

import com.focusteam.dealhunter.dto.groupCityDto.CityCreateDto;
import com.focusteam.dealhunter.dto.groupCityDto.CityUpdate;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreCreateDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public interface CityServices {
    public ResponseEntity<Object> create(@Valid @RequestBody CityCreateDto cityCreateDto, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> getOne(@PathVariable long id);
    public ResponseEntity<Object> getOneByNameUA(@PathVariable String name);
    public ResponseEntity<Object> getAll();
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody CityUpdate cityUpdate, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request);
}
