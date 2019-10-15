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
        System.out.println("1 Create store processing !");
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        System.out.println("2 Get account done !");
        Optional<TypeStore> typeStoreOptional = typeStoreRepository.findById(storeCreateDto.getTypeStoreId());
        System.out.println("3 type store done !");
        Optional<City> cityOptional = cityRepository.findById(storeCreateDto.getCityId());
        System.out.println("4 Get city done !");
        Optional<Store> storeOptional = storeRepository.findByName(storeCreateDto.getName(), storeCreateDto.getEmail(), storeCreateDto.getPhone());
        System.out.println("5 et Store done !");
        if (accountOptional.isPresent() && accountOptional.get().getId() == storeCreateDto.getAccountId()){
            System.out.println("6 Account exist !");
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 0){
                System.out.println("7 Type account = guest !");
                hashMap.clear();
                hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
            } else if (account.getConfirmEmail() == 0) {
                System.out.println("8 Not confirm email !");
                hashMap.clear();
                hashMap.put("Email", "You need to confirm your email first !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Email has errors !").build(), HttpStatus.UNAUTHORIZED);
            } else if (account.getUserInformation().getFullName() == null
                        || account.getUserInformation().getAvatar() == null
                        || account.getUserInformation().getAddress() == null
                        || account.getUserInformation().getBirthday() == null
                        || account.getUserInformation().getGender() < 0) {
                System.out.println("9 Information Null !");
                hashMap.clear();
                hashMap.put("Information", "You must fill out your information before creating a store !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Information has errors !").build(), HttpStatus.UNAUTHORIZED);
            } else {
                if (bindingResult.hasErrors()) {
                    System.out.println("10 Form error");
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
                            .setMessage("Store data has errors !").build(), HttpStatus.FORBIDDEN);
                } else if (!cityOptional.isPresent()) {
                    System.out.println("11 City not found !");
                    hashMap.clear();
                    hashMap.put("ID", "No city found with this id = " + storeCreateDto.getCityId() + " !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                } else if (storeOptional.isPresent()) {
                    System.out.println("12 Store exist !");
                    if (storeOptional.get().getName().equalsIgnoreCase(storeCreateDto.getName())) {
                        System.out.println("13 Name exist");
                        hashMap.clear();
                        hashMap.put("Name", "This store name has been used to register in another store");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Store name error !").build(), HttpStatus.FORBIDDEN);
                    } else if (storeOptional.get().getEmail().equalsIgnoreCase(storeCreateDto.getEmail())) {
                        System.out.println("14 Email exist");
                        hashMap.clear();
                        hashMap.put("Email", "This email has been used to register in another store");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Store email error !").build(), HttpStatus.FORBIDDEN);
                    } else {
                        System.out.println("15 phone exist");
                        hashMap.clear();
                        hashMap.put("Phone", "This phone has been used to register in another store");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Store phone error !").build(), HttpStatus.FORBIDDEN);
                    }
                } else if (!typeStoreOptional.isPresent()) {
                    System.out.println("16 type store not exist");
                    hashMap.clear();
                    hashMap.put("ID", "No type store found with this id = " + storeCreateDto.getTypeStoreId() + " !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                } else if (account.getStore() != null) {
                    System.out.println("17 Account has a store");
                    hashMap.clear();
                    hashMap.put("Store", "An account can't create more than one store");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Create Error !").build(), HttpStatus.FORBIDDEN);
                } else {
                    System.out.println("18 Store done");
                    Store store = new Store(storeCreateDto);
                    store.setAccount(account);
                    store.setTypeStore(typeStoreOptional.get());

                    StoreAddress storeAddress = new StoreAddress(storeCreateDto);
                    storeAddress.setCity(cityOptional.get());
                    storeAddress.setStore(store);

                    storeRepository.save(store);
                    System.out.println("19 Save store done");

                    storeAddressRepository.save(storeAddress);
                    System.out.println("20 save store address done");

                    List<StoreAddressDto> list = new ArrayList<>();
                    list.add(new StoreAddressDto(storeAddress));

                    StoreDto storeDto = new StoreDto(store);
                    storeDto.setStoreAddresses(list);

                    System.out.println("21 Return done !");

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
