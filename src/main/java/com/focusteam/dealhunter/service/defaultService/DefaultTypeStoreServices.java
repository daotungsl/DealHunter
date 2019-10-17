package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreCreateDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreUpdate;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.TypeStore;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.repository.TypeStoreRepository;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.impl.TypeStoreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Service("typeStoreServices")
public class DefaultTypeStoreServices implements TypeStoreServices {
    private HashMap<String, String> hashMap = new HashMap<>();

    @Autowired
    TypeStoreRepository typeStoreRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public ResponseEntity<Object> create(@Valid TypeStoreCreateDto typeStoreCreateDto, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 1 || account.getTypeAccount() == 0){
                return new ResponseEntity<>(new RESTResponse.Error()
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
                            .setMessage("Type store data has errors !").build(), HttpStatus.FORBIDDEN);
                }else {
                    Optional<TypeStore> typeStoreOptional = typeStoreRepository.findByName(typeStoreCreateDto.getName());
                    if (!typeStoreOptional.isPresent()){
                        TypeStore typeStore = new TypeStore(typeStoreCreateDto);
                        typeStoreRepository.save(typeStore);

                        return new ResponseEntity<>(new RESTResponse.Success()
                                .setStatus(HttpStatus.CREATED.value())
                                .setData(new TypeStoreDto(typeStore))
                                .setMessage("Created type store success !").build(), HttpStatus.CREATED);
                    }else {
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("This type store name has exist !").build(), HttpStatus.FORBIDDEN);
                    }
                }
            }
        }else {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> getOne(long id) {
        Optional<TypeStore> typeStoreOptional = typeStoreRepository.findByIdAndStatus(id);
        if (typeStoreOptional.isPresent()){
            TypeStore typeStore = typeStoreOptional.get();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new TypeStoreDto(typeStore))
                    .setMessage("Type store with id = " + id + " !").build(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("No type store found with this id = " + id + " !").build(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<Object> getOneByNameUA(String name) {
        Optional<TypeStore> typeStoreOptional = typeStoreRepository.findByNameUnAccentAndStatus(name);
        if (typeStoreOptional.isPresent()){
            TypeStore typeStore = typeStoreOptional.get();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new TypeStoreDto(typeStore))
                    .setMessage("Type store with id = " + name + " !").build(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("No type store found with this id = " + name + " !").build(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<Object> getAll() {
        List<TypeStore> typeStores = typeStoreRepository.getAllByStatus();
        if (!typeStores.isEmpty()){
            List<TypeStoreDto> typeStoreDtos = new ArrayList<>();
            TypeStoreDto typeStoreDto = null;
            for (TypeStore typeStore: typeStores
            ) {
                typeStoreDto = new TypeStoreDto(typeStore);
                typeStoreDtos.add(typeStoreDto);
            }
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(typeStoreDtos)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setData("")
                .setMessage("No type store found !").build(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> update(long id, @Valid TypeStoreUpdate typeStoreUpdate, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account1 = accountOptional.get();
            if (account1.getTypeAccount() == 1 || account1.getTypeAccount() == 0){
                return new ResponseEntity<>(new RESTResponse.Error()
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
                            .setMessage("Type store data has errors !").build(), HttpStatus.FORBIDDEN);
                }else {
                    Optional<TypeStore> typeStoreOptional = typeStoreRepository.findById(id);
                    if (typeStoreOptional.isPresent()){
                        TypeStore typeStore = typeStoreOptional.get();
                        typeStore.setName(typeStoreUpdate.getName());
                        typeStore.setDescription(typeStoreUpdate.getDescription());
                        typeStore.setUpdated(Calendar.getInstance().getTimeInMillis());
                        typeStore.setStatus(typeStoreUpdate.getStatus());

                        typeStoreRepository.save(typeStore);

                        return new ResponseEntity<>(new RESTResponse.Success()
                                .setStatus(HttpStatus.OK.value())
                                .setData(new TypeStoreDto(typeStore))
                                .setMessage("Update type store data success !").build(), HttpStatus.OK);
                    }else {
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                    }
                }
            }
        }else {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> delete(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 1 || account.getTypeAccount() == 0){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
            }else {
                Optional<TypeStore> typeStoreOptional = typeStoreRepository.findById(id);
                if (typeStoreOptional.isPresent()){
                    TypeStore typeStore = typeStoreOptional.get();
                    typeStoreRepository.delete(typeStore);
                    return new ResponseEntity<>(new RESTResponse.Success()
                            .setStatus(HttpStatus.OK.value())
                            .setData(new TypeStoreDto(typeStore))
                            .setMessage("Delete type store success !").build(), HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                }
            }
        }else {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }
    }
}
