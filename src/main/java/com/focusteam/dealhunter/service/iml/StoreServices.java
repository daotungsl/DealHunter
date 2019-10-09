package com.focusteam.dealhunter.service.iml;

import com.focusteam.dealhunter.dto.groupStoreDto.StoreAddressCreate;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreAddressUpdate;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreCreateDto;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public interface StoreServices {
    public ResponseEntity<Object> create(@Valid @RequestBody StoreCreateDto storeCreateDto, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> getOne(@PathVariable long id);
    public ResponseEntity<Object> getAll();
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody StoreUpdate storeUpdate, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request);


    public ResponseEntity<Object> createSA(@Valid @RequestBody StoreAddressCreate storeAddressCreate, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> getOneSA(@PathVariable long id);
    public ResponseEntity<Object> getAllSA();
    public ResponseEntity<Object> updateSA(@PathVariable long id, @Valid @RequestBody StoreAddressUpdate storeAddressUpdate, BindingResult bindingResult, HttpServletRequest request);
    public ResponseEntity<Object> deleteSA(@PathVariable long id, HttpServletRequest request);
}
