package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupCityDto.CityDto;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreAddressDto;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreCreateDto;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreDto;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreUpdate;
import com.focusteam.dealhunter.entity.*;
import com.focusteam.dealhunter.repository.*;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.iml.AccountService;
import com.focusteam.dealhunter.service.iml.StoreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Service("storeService")
public class DefaultStoreService implements StoreServices {
    HashMap<String, String> hashMap = new HashMap<>();
    @Autowired
    AccountService accountService;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TypeStoreRepository typeStoreRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    StoreAddressRepository storeAddressRepository;


    @Override
    public ResponseEntity<Object> create(@Valid StoreCreateDto storeCreateDto, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<TypeStore> typeStoreOptional = typeStoreRepository.findById(storeCreateDto.getTypeStoreId());
        Optional<City> cityOptional = cityRepository.findById(storeCreateDto.getCityId());
        if (accountOptional.isPresent() && accountOptional.get().getId() == storeCreateDto.getAccountId()){
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 0){
                hashMap.clear();
                hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
            }else {
                if (bindingResult.hasErrors()){
                    hashMap.clear();
                    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                    for (FieldError f: fieldErrors
                    ) {
                        hashMap.put(f.getField(), f.getDefaultMessage());
                    }
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Store data has errors !").build(), HttpStatus.FORBIDDEN);
                }else if ( !cityOptional.isPresent()){
                    hashMap.clear();
                    hashMap.put("ID", "No city found with this id = " + storeCreateDto.getCityId() + " !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                }else if (!typeStoreOptional.isPresent()){
                    hashMap.clear();
                    hashMap.put("ID", "No type store found with this id = " + storeCreateDto.getTypeStoreId() + " !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                }else if (!(account.getStore() == null)){
                    hashMap.clear();
                    hashMap.put("Store", "An account can't create more than one store");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Create Error !").build(), HttpStatus.FORBIDDEN);
                } else {
                    Store store = new Store(storeCreateDto);
                    store.setAccount(account);
                    store.setTypeStore(typeStoreOptional.get());

                    StoreAddress storeAddress = new StoreAddress(storeCreateDto);
                    storeAddress.setCity(cityOptional.get());
                    storeAddress.setStore(store);

                    storeRepository.save(store);

                    storeAddressRepository.save(storeAddress);

                    List<StoreAddressDto> list = new ArrayList<>();
                    list.add(new StoreAddressDto(storeAddress));

                    StoreDto storeDto = new StoreDto(store);
                    storeDto.setStoreAddresses(list);

                    return new ResponseEntity<>(new RESTResponse.Success()
                            .setStatus(HttpStatus.CREATED.value())
                            .setData(storeDto)
                            .setMessage("Created city success !").build(), HttpStatus.CREATED);
                }
            }
        }else {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> getOne(long id) {
        Optional<Store> storeOptional = storeRepository.findById(id);
        if (storeOptional.isPresent()){
            Store store = storeOptional.get();
            if (store.getStoreAddresses() != null){
                List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(store.getStoreAddresses());
                List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
                for (StoreAddress s: storeAddressList
                     ) {
                    storeAddressDtoList.add(new StoreAddressDto(s));
                }
                StoreDto storeDto = new StoreDto(store);
                storeDto.setStoreAddresses(storeAddressDtoList);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(storeDto)
                        .setMessage("Store with id = " + id + " !").build(), HttpStatus.OK);
            }else {
                StoreDto storeDto = new StoreDto(store);
                storeDto.setStoreAddresses(null);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(storeDto)
                        .setMessage("Store with id = " + id + " !").build(), HttpStatus.OK);
            }
        }else {
            hashMap.clear();
            hashMap.put("ID", "No store found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<Object> getAll() {
        List<Store> stores = storeRepository.findAll();
        if (!stores.isEmpty()){
            List<StoreDto> storeDtoList = new ArrayList<>();
            StoreDto storeDto = null;
            for (Store store : stores){
                List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(store.getStoreAddresses());
                List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
                for (StoreAddress s: storeAddressList
                ) {
                    storeAddressDtoList.add(new StoreAddressDto(s));
                }
                storeDto = new StoreDto(store);
                storeDto.setStoreAddresses(storeAddressDtoList);

                storeDtoList.add(storeDto);
            }
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(storeDtoList)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
        hashMap.clear();
        hashMap.put("Store", "No store found !");
        return new ResponseEntity<>(new RESTResponse.Error()
                .addErrors(hashMap)
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setData("")
                .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> update(long id, @Valid StoreUpdate storeUpdate, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(storeUpdate.getId());
        Optional<TypeStore> typeStoreOptional = typeStoreRepository.findById(storeUpdate.getTypeStoreId());
        if (accountOptional.get().getStore().getId() != id || accountOptional.get().getTypeAccount() == 0){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (id != storeUpdate.getId()){
            hashMap.clear();
            hashMap.put("ID", "The id in path variable does not match the id in the data update !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Data error !").build(), HttpStatus.UNAUTHORIZED);
        }else if (bindingResult.hasErrors()){
            hashMap.clear();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError f: fieldErrors
            ) {
                hashMap.put(f.getField(), f.getDefaultMessage());
            }
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Store data has errors !").build(), HttpStatus.FORBIDDEN);
        }else if (!storeOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No store found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (!typeStoreOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No type store found with this id = " + storeUpdate.getTypeStoreId() + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else {
            Store store = storeOptional.get();
            store.setName(storeUpdate.getName());
            store.setEmail(storeUpdate.getEmail());
            store.setPhone(storeUpdate.getPhone());
            store.setImages(storeUpdate.getImages());
            store.setStatus(storeUpdate.getStatus());
            store.setAccount(accountOptional.get());
            store.setTypeStore(typeStoreOptional.get());
            storeRepository.save(store);

            List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(storeOptional.get().getStoreAddresses());
            List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
            for (StoreAddress s: storeAddressList
            ) {
                storeAddressDtoList.add(new StoreAddressDto(s));
            }
            StoreDto storeDto = new StoreDto(store);

            storeDto.setStoreAddresses(storeAddressDtoList);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(storeDto)
                    .setMessage("Update city data success !").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> delete(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(id);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getStore().getId() != id || accountOptional.get().getTypeAccount() == 0){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!storeOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No store found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else {
            Store store = storeOptional.get();
            List<StoreAddress> storeAddressList = new ArrayList<>(store.getStoreAddresses());
            List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
            for (StoreAddress s: storeAddressList
            ) {
                storeAddressDtoList.add(new StoreAddressDto(s));
            }
            StoreDto storeDto = new StoreDto(store);

            storeDto.setStoreAddresses(storeAddressDtoList);
            storeRepository.delete(store);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(storeDto)
                    .setMessage("Delete city success !").build(), HttpStatus.OK);
        }
    }
}
