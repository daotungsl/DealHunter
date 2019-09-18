package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.AccountDto;
import com.focusteam.dealhunter.dto.StoreDto;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.Store;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.repository.StoreRepository;
import com.focusteam.dealhunter.repository.TypeStoreRepository;
import com.focusteam.dealhunter.rest.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/so/store")
public class StoreOwnerController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private TypeStoreRepository typeStoreRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createStoreInformation(@RequestBody StoreDto storeDto){


        Optional<Account> optional = accountRepository.findById(storeDto.getAccountId());
        if (optional.isPresent()){
            Account account = optional.get();
            Store store = new Store(storeDto);
            store.setAccount(account);
            storeRepository.save(store);
        }
        return new ResponseEntity<>(new RESTResponse.Success()
                .setStatus(HttpStatus.CREATED.value())
                .setData(storeDto)
                .setMessage("Success!").build(), HttpStatus.CREATED);
    }
}
