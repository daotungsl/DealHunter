package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.StoreAddressDto;
import com.focusteam.dealhunter.dto.StoreDto;
import com.focusteam.dealhunter.dto.StoreId;
import com.focusteam.dealhunter.entity.*;
import com.focusteam.dealhunter.repository.*;
import com.focusteam.dealhunter.rest.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/so/store")
public class StoreOwnerController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private TypeStoreRepository typeStoreRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StoreAddressRepository storeAddressRepository;

    @CrossOrigin
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public ResponseEntity<Object> getStoreInformation(@RequestBody StoreId storeId){
        Optional<Store> storeOptional = storeRepository.findById(storeId.getId());
        if (storeOptional.isPresent()){
            Store store = storeOptional.get();
            StoreDto storeDto = new StoreDto(store);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.CREATED.value())
                    .setData(storeDto)
                    .setMessage("Success!").build(), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new RESTResponse.Error()
                .setStatus(HttpStatus.FORBIDDEN.value())
                .setData("")
                .setMessage("Error!").build(), HttpStatus.FORBIDDEN);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createStoreInformation(@RequestBody StoreDto storeDto){
        Optional<Account> optionalAccount = accountRepository.findById(storeDto.getAccountId());
        Optional<TypeStore> optionalTypeStore = typeStoreRepository.findById(storeDto.getTypeStoreId());
        if (optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            if (optionalTypeStore.isPresent()){
                TypeStore typeStore = optionalTypeStore.get();
                Store store = new Store(storeDto);
                store.setAccount(account);
                store.setTypeStore(typeStore);
                storeRepository.save(store);
            }

        }
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.CREATED.value())
                .setData(storeDto)
                .setMessage("Success!").build(), HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/address")
    public ResponseEntity<Object> updateUserInformation(@RequestBody StoreAddressDto storeAddressDto){
        Optional<Store> optionalStore = storeRepository.findById(storeAddressDto.getStoreId());
        Optional<City> optionalCity = cityRepository.findById(storeAddressDto.getCityId());
        if (optionalStore.isPresent()){
            Store store = optionalStore.get();
            if (optionalCity.isPresent()){
                City city = optionalCity.get();
                StoreAddress storeAddress = StoreAddress.StoreAddressBuilder
                        .aStoreAddress()
                        .withAddress(storeAddressDto.getAddress())
                        .withDescription(storeAddressDto.getDescription())
                        .withCreated(System.currentTimeMillis())
                        .withUpdated(System.currentTimeMillis())
                        .withStatus(0)
                        .withCity(city)
                        .withStore(store)
                        .build();
                storeAddressRepository.save(storeAddress);
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.CREATED.value())
                        .setData(storeAddressDto)
                        .setMessage("Success!").build(), HttpStatus.CREATED);
            }
        }
            return new ResponseEntity<>(new RESTResponse.Error()
                .setStatus(HttpStatus.FORBIDDEN.value())
                .setData("")
                .setMessage("Error!").build(), HttpStatus.FORBIDDEN);
    }
}
