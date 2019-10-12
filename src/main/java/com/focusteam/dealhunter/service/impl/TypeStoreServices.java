package com.focusteam.dealhunter.service.impl;

import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreCreateDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public interface TypeStoreServices {
    public ResponseEntity<Object> create(@Valid @RequestBody TypeStoreCreateDto typeStoreCreateDto, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> getOne(@PathVariable long id);
    public ResponseEntity<Object> getOneByNameUA(@PathVariable String name);
    public ResponseEntity<Object> getAll();
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody TypeStoreUpdate typeStoreUpdate, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request);
}
