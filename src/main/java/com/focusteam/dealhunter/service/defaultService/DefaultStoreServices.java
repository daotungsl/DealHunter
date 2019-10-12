package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupStoreDto.*;
import com.focusteam.dealhunter.entity.*;
import com.focusteam.dealhunter.repository.*;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.impl.AccountServices;
import com.focusteam.dealhunter.service.impl.StoreServices;
import com.focusteam.dealhunter.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Service("storeServices")
public class DefaultStoreServices implements StoreServices {
    private HashMap<String, String> hashMap = new HashMap<>();
    @Autowired
    AccountServices accountServices;

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


    // Store CRUD

    @Override
    public ResponseEntity<Object> create(@Valid StoreCreateDto storeCreateDto, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<TypeStore> typeStoreOptional = typeStoreRepository.findById(storeCreateDto.getTypeStoreId());
        Optional<City> cityOptional = cityRepository.findById(storeCreateDto.getCityId());
        Optional<Store> storeOptional = storeRepository.findByName(storeCreateDto.getName(), storeCreateDto.getEmail(), storeCreateDto.getPhone());
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
                } else if (storeOptional.isPresent()) {
                    if (storeOptional.get().getName().equalsIgnoreCase(storeCreateDto.getName())){
                        hashMap.clear();
                        hashMap.put("Name", "This store name has been used to register in another store");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Store name error !").build(), HttpStatus.FORBIDDEN);
                    } else if (storeOptional.get().getEmail().equalsIgnoreCase(storeCreateDto.getEmail())) {
                        hashMap.clear();
                        hashMap.put("Email", "This email has been used to register in another store");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Store email error !").build(), HttpStatus.FORBIDDEN);
                    } else {
                        hashMap.clear();
                        hashMap.put("Phone", "This phone has been used to register in another store");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Store phone error !").build(), HttpStatus.FORBIDDEN);
                    }
                } else if (!typeStoreOptional.isPresent()) {
                    hashMap.clear();
                    hashMap.put("ID", "No type store found with this id = " + storeCreateDto.getTypeStoreId() + " !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                } else if (!(account.getStore() == null)) {
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
                            .setMessage("Created store success !").build(), HttpStatus.CREATED);
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
    public ResponseEntity<Object> getOneByNameUA(String name) {
        Optional<Store> storeOptional = storeRepository.findByNameUnAccent(name);
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
                        .setMessage("Store with id = " + name + " !").build(), HttpStatus.OK);
            }else {
                StoreDto storeDto = new StoreDto(store);
                storeDto.setStoreAddresses(null);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(storeDto)
                        .setMessage("Store with id = " + name + " !").build(), HttpStatus.OK);
            }
        }else {
            hashMap.clear();
            hashMap.put("ID", "No store found with this id = " + name + " !");
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
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getStore().getId() != id || accountOptional.get().getTypeAccount() == 0){
//            hashMap.clear();
//            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
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
        } else if (!storeOptional.get().getName().equals(storeUpdate.getName())) {
            hashMap.clear();
            hashMap.put("Name", "Can't change store name !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Name error !").build(), HttpStatus.FORBIDDEN);
        } else {
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
            for (StoreAddress s : storeAddressList
            ) {
                storeAddressDtoList.add(new StoreAddressDto(s));
            }
            StoreDto storeDto = new StoreDto(store);

            storeDto.setStoreAddresses(storeAddressDtoList);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(storeDto)
                    .setMessage("Update store data success !").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> updateByNameUA(String name, @Valid StoreUpdate storeUpdate, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(storeUpdate.getId());
        Optional<TypeStore> typeStoreOptional = typeStoreRepository.findById(storeUpdate.getTypeStoreId());
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!accountOptional.get().getStore().getNameUnAccent().equals(name) || accountOptional.get().getTypeAccount() == 0){
//            hashMap.clear();
//            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!name.equals(new StringUtil().unAccent(storeUpdate.getName()))){
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
        } else if (!storeOptional.get().getName().equals(storeUpdate.getName())) {
            hashMap.clear();
            hashMap.put("Name", "Can't change store name !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Name error !").build(), HttpStatus.FORBIDDEN);
        } else {
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
            for (StoreAddress s : storeAddressList
            ) {
                storeAddressDtoList.add(new StoreAddressDto(s));
            }
            StoreDto storeDto = new StoreDto(store);

            storeDto.setStoreAddresses(storeAddressDtoList);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(storeDto)
                    .setMessage("Update store data success !").build(), HttpStatus.OK);
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
                    .setMessage("Delete store success !").build(), HttpStatus.OK);
        }
    }

    // Store Address CRUD

    @Override
    public ResponseEntity<Object> createSA(@Valid StoreAddressCreate storeAddressCreate, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(storeAddressCreate.getStoreId());
        Optional<City> cityOptional = cityRepository.findById(storeAddressCreate.getCityId());
        if (!accountOptional.isPresent()){
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
        } else if (accountOptional.get().getStore().getId() != storeAddressCreate.getStoreId() || accountOptional.get().getTypeAccount() == 0) {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!cityOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No city found with this id = " + storeAddressCreate.getCityId() + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
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
                    .setMessage("Store address data has errors !").build(), HttpStatus.FORBIDDEN);
        }else {
            StoreAddress storeAddress = new StoreAddress(storeAddressCreate);
            storeAddress.setCity(cityOptional.get());
            storeAddress.setStore(storeOptional.get());

            storeAddressRepository.save(storeAddress);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.CREATED.value())
                    .setData(new StoreAddressDto(storeAddress))
                    .setMessage("Created store address success !").build(), HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Object> getOneSA(long id) {
        Optional<StoreAddress> storeAddressOptional = storeAddressRepository.findById(id);
        if (storeAddressOptional.isPresent()){
            StoreAddress storeAddress = storeAddressOptional.get();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new StoreAddressDto(storeAddress))
                    .setMessage("Store address with id = " + id + " !").build(), HttpStatus.OK);
        }else {
            hashMap.clear();
            hashMap.put("ID", "No store address found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<Object> getAllSA() {
        List<StoreAddress> storeAddresses = storeAddressRepository.findAll();
        if (!storeAddresses.isEmpty()){
            List<StoreAddressDto> storeAddressDtos = new ArrayList<>();
            StoreAddressDto storeAddressDto = null;
            for (StoreAddress storeAddress: storeAddresses
                 ) {
                storeAddressDto = new StoreAddressDto(storeAddress);
                storeAddressDtos.add(storeAddressDto);
            }
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(storeAddressDtos)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }else {
            hashMap.clear();
            hashMap.put("Store_Address", "No store address found !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> updateSA(long id, @Valid StoreAddressUpdate storeAddressUpdate, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(storeAddressUpdate.getStoreId());
        Optional<City> cityOptional = cityRepository.findById(storeAddressUpdate.getCityId());
        Optional<StoreAddress> storeAddressOptional = storeAddressRepository.findById(id);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (accountOptional.get().getStore().getId() != storeAddressUpdate.getStoreId() || accountOptional.get().getTypeAccount() == 0) {
//            hashMap.clear();
//            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (id != storeAddressUpdate.getId()){
            hashMap.clear();
            hashMap.put("ID", "The id in path variable does not match the id in the data update !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Data error !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!storeAddressOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No store address found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (!storeOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No store found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (!cityOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No city found with this id = " + storeAddressUpdate.getCityId() + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (bindingResult.hasErrors()) {
            hashMap.clear();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError f : fieldErrors
            ) {
                hashMap.put(f.getField(), f.getDefaultMessage());
            }
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Store address data has errors !").build(), HttpStatus.FORBIDDEN);
        } else {
            StoreAddress storeAddress = storeAddressOptional.get();
            storeAddress.setAddress(storeAddressUpdate.getAddress());
            storeAddress.setDescription(storeAddressUpdate.getDescription());
            storeAddress.setStore(storeOptional.get());
            storeAddress.setCity(cityOptional.get());

            storeAddressRepository.save(storeAddress);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new StoreAddressDto(storeAddress))
                    .setMessage("Update store address data success !").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> deleteSA(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<StoreAddress> storeAddressOptional = storeAddressRepository.findById(id);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!storeAddressOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No store address found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (accountOptional.get().getStore().getId() != storeAddressOptional.get().getStore().getId() || accountOptional.get().getTypeAccount() == 0) {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            StoreAddress storeAddress = storeAddressOptional.get();
            storeAddressRepository.delete(storeAddress);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new StoreAddressDto(storeAddress))
                    .setMessage("Delete store address success !").build(), HttpStatus.OK);
        }
    }
}
