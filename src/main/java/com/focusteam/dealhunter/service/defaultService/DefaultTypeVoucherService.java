package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherCreateDto;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherDto;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherUpdate;
import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.entity.TypeVoucher;
import com.focusteam.dealhunter.repository.AccountRepository;
import com.focusteam.dealhunter.repository.CredentialRepository;
import com.focusteam.dealhunter.repository.TypeVoucherRepository;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.iml.TypeVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Service("typeVoucherService")
public class DefaultTypeVoucherService implements TypeVoucherService {
    private HashMap<String, String> hashMap = new HashMap<>();

    @Autowired
    TypeVoucherRepository typeVoucherRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CredentialRepository credentialRepository;

    @Override
    public ResponseEntity<Object> create(@Valid @RequestBody TypeVoucherCreateDto typeVoucherCreateDto, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 1 || account.getTypeAccount() == 0){
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
                            .setMessage("Type voucher data has errors !").build(), HttpStatus.FORBIDDEN);
                }else {
                    Optional<TypeVoucher> typeVoucherOptional = typeVoucherRepository.findByName(typeVoucherCreateDto.getName());
                    if (!typeVoucherOptional.isPresent()){
                        TypeVoucher typeVoucher = new TypeVoucher(typeVoucherCreateDto);
                        typeVoucherRepository.save(typeVoucher);

                        return new ResponseEntity<>(new RESTResponse.Success()
                                .setStatus(HttpStatus.CREATED.value())
                                .setData(new TypeVoucherDto(typeVoucher))
                                .setMessage("Created type voucher success !").build(), HttpStatus.CREATED);
                    }else {
                        hashMap.clear();
                        hashMap.put("Name", "This type voucher name has exist !");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Type voucher data has errors !").build(), HttpStatus.FORBIDDEN);
                    }
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
    public ResponseEntity<Object> getOne(@PathVariable long id) {
        Optional<TypeVoucher> typeVoucherOptional = typeVoucherRepository.findById(id);
        if (typeVoucherOptional.isPresent()){
            TypeVoucher typeVoucher1 = typeVoucherOptional.get();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new TypeVoucherDto(typeVoucher1))
                    .setMessage("Type voucher with id = " + id + " !").build(), HttpStatus.OK);
        }else {
            hashMap.clear();
            hashMap.put("ID", "No type voucher found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<Object> getAll() {
        List<TypeVoucher> typeVouchers = typeVoucherRepository.findAll();
        if (!typeVouchers.isEmpty()){
            List<TypeVoucherDto> typeVoucherDtos = new ArrayList<>();
            TypeVoucherDto typeVoucherDto = null;
            for (TypeVoucher typeVoucher: typeVouchers
                 ) {
                typeVoucherDto = new TypeVoucherDto(typeVoucher);
                typeVoucherDtos.add(typeVoucherDto);
            }
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(typeVoucherDtos)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
        hashMap.clear();
        hashMap.put("TypeVoucher", "No type voucher found !");
        return new ResponseEntity<>(new RESTResponse.Error()
                .addErrors(hashMap)
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setData("")
                .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody TypeVoucherUpdate typeVoucherUpdate, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account1 = accountOptional.get();
            if (account1.getTypeAccount() == 1 || account1.getTypeAccount() == 0){
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
                            .setMessage("Type voucher data has errors !").build(), HttpStatus.FORBIDDEN);
                }else {
                    Optional<TypeVoucher> typeVoucherOptional = typeVoucherRepository.findById(id);
                    if (typeVoucherOptional.isPresent()){
                        TypeVoucher typeVoucher1 = typeVoucherOptional.get();
                        typeVoucher1.setName(typeVoucherUpdate.getName());
                        typeVoucher1.setDescription(typeVoucherUpdate.getDescription());
                        typeVoucher1.setUpdated(Calendar.getInstance().getTimeInMillis());
                        typeVoucher1.setStatus(typeVoucherUpdate.getStatus());

                        typeVoucherRepository.save(typeVoucher1);

                        return new ResponseEntity<>(new RESTResponse.Success()
                                .setStatus(HttpStatus.OK.value())
                                .setData(new TypeVoucherDto(typeVoucher1))
                                .setMessage("Update type voucher data success !").build(), HttpStatus.OK);
                    }else {
                        hashMap.clear();
                        hashMap.put("ID", "No type voucher found with this id !");
                        return new ResponseEntity<>(new RESTResponse.Error()
                                .addErrors(hashMap)
                                .setStatus(HttpStatus.FORBIDDEN.value())
                                .setData("")
                                .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
                    }
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
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            if (account.getTypeAccount() == 1 || account.getTypeAccount() == 0){
                hashMap.clear();
                hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setData("")
                        .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
            }else {
                Optional<TypeVoucher> typeVoucherOptional = typeVoucherRepository.findById(id);
                if (typeVoucherOptional.isPresent()){
                    TypeVoucher typeVoucher = typeVoucherOptional.get();
                    typeVoucherRepository.delete(typeVoucher);
                    return new ResponseEntity<>(new RESTResponse.Success()
                            .setStatus(HttpStatus.OK.value())
                            .setData(new TypeVoucherDto(typeVoucher))
                            .setMessage("Delete type voucher success !").build(), HttpStatus.OK);
                }else {
                    hashMap.clear();
                    hashMap.put("ID", "No type voucher found with this id !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.FORBIDDEN.value())
                            .setData("")
                            .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
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
}
