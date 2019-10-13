package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupAccountDto.AccountInformationDto;
import com.focusteam.dealhunter.dto.groupAccountDto.UserInformationDto;
import com.focusteam.dealhunter.dto.groupCityDto.CityDto;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreAddressDto;
import com.focusteam.dealhunter.dto.groupStoreDto.StoreDto;
import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreDto;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherDto;
import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherDto;
import com.focusteam.dealhunter.entity.*;
import com.focusteam.dealhunter.repository.*;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.impl.AdminServices;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service("adminServices")
public class DefaultAdminService implements AdminServices {
    private HashMap<String, String> hashMap = new HashMap<>();

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StoreAddressRepository storeAddressRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TypeStoreRepository typeStoreRepository;

    @Autowired
    TypeVoucherRepository typeVoucherRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public ResponseEntity<Object> statusAccount(long id, int status, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (status <0 || status >2) {
            hashMap.clear();
            hashMap.put("Status", "status is invalid! (0, 1, 2)");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Status has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else {
            Optional<Account> accountOptional1 = accountRepository.findById(id);
            if (!accountOptional1.isPresent()) {
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            } else if (accountOptional1.get().getStatus() == status) {
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("The status has not changed because this status has been changed !").build(), HttpStatus.NOT_FOUND);
            } else {
                Account account = accountOptional1.get();
                account.setStatus(status);

                accountRepository.save(account);
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(new AccountInformationDto(account))
                        .setMessage("Success !").build(), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<Object> statusStore(long id, int status, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (status <0 || status >2) {
            hashMap.clear();
            hashMap.put("Status", "status is invalid! (0, 1, 2)");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Status has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            Optional<Store> storeOptional = storeRepository.findById(id);
            if (!storeOptional.isPresent()){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }else if (storeOptional.get().getStatus() == status){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("The status has not changed because this status has been changed !").build(), HttpStatus.NOT_FOUND);
            }else {
                Store store = storeOptional.get();
                store.setStatus(status);
                storeRepository.save(store);

                List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(store.getStoreAddresses());
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
                        .setMessage("Success!").build(), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<Object> statusStoreAddress(long id, int status, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (status <0 || status >2) {
            hashMap.clear();
            hashMap.put("Status", "status is invalid! (0, 1, 2)");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Status has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            Optional<StoreAddress> storeAddressOptional = storeAddressRepository.findById(id);
            if (!storeAddressOptional.isPresent()){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }else if (storeAddressOptional.get().getStatus() == status){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("The status has not changed because this status has been changed !").build(), HttpStatus.NOT_FOUND);
            }else {
                StoreAddress storeAddress = storeAddressOptional.get();
                storeAddress.setStatus(status);

                storeAddressRepository.save(storeAddress);
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(new StoreAddressDto(storeAddress))
                        .setMessage("Success !").build(), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<Object> statusVoucher(long id, int status, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (status <0 || status >2) {
            hashMap.clear();
            hashMap.put("Status", "status is invalid! (0, 1, 2)");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Status has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            Optional<Voucher> voucherOptional = voucherRepository.findById(id);
            if (!voucherOptional.isPresent()){
                hashMap.clear();
                hashMap.put("ID", "No voucher found with this id = " + id + " !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.FORBIDDEN.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
            }else if (voucherOptional.get().getStatus() == status){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("The status has not changed because this status has been changed !").build(), HttpStatus.NOT_FOUND);
            }else {
                Voucher voucher = voucherOptional.get();
                voucher.setStatus(status);

                voucherRepository.save(voucher);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(new VoucherDto(voucher))
                        .setMessage("Success").build(), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<Object> statusTransaction(long id, int status, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (status <0 || status >2) {
            hashMap.clear();
            hashMap.put("Status", "status is invalid! (0, 1, 2)");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Status has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            Optional<Transaction> transactionOptional = transactionRepository.findById(id);
            if (!transactionOptional.isPresent()){
                hashMap.clear();
                hashMap.put("ID", "No transaction found with this id = " + id + " !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.FORBIDDEN.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
            }else if (transactionOptional.get().getStatus() == status){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("The status has not changed because this status has been changed !").build(), HttpStatus.NOT_FOUND);
            }else {
                Transaction transaction = transactionOptional.get();
                transaction.setStatus(status);

                transactionRepository.save(transaction);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(new TransactionDto(transaction))
                        .setMessage("Success").build(), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<Object> getAllAccount(HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            List<Account> accounts = accountRepository.findAll();
            if (!accounts.isEmpty()){
                List<AccountInformationDto> accountInformationDtos = new ArrayList<>();
                AccountInformationDto accountInformationDto = null;
                for (Account account : accounts){
                    accountInformationDto = new AccountInformationDto(account);
                    accountInformationDtos.add(accountInformationDto);
                }
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(accountInformationDtos)
                        .setMessage("Success!").build(), HttpStatus.OK);
            }else {
                hashMap.clear();
                hashMap.put("Account", "No account found !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<Object> getAllAccountByType(int type, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (type <0 || type >2) {
            hashMap.clear();
            hashMap.put("Type-Account", "Type-Account is invalid! (0, 1, 2)");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Type-Account has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            List<Account> accounts = accountRepository.countAccountByType(type);
            if (!accounts.isEmpty()){
                List<AccountInformationDto> accountInformationDtos = new ArrayList<>();
                AccountInformationDto accountInformationDto = null;
                for (Account account : accounts){
                    accountInformationDto = new AccountInformationDto(account);
                    accountInformationDtos.add(accountInformationDto);
                }
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(accountInformationDtos)
                        .setMessage("Success!").build(), HttpStatus.OK);
            }else {
                hashMap.clear();
                hashMap.put("Account", "No account found !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<Object> statusTypeStore(long id, int status, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (status <0 || status >2) {
            hashMap.clear();
            hashMap.put("Status", "status is invalid! (0, 1, 2)");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Status has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            Optional<TypeStore> typeStoreOptional = typeStoreRepository.findById(id);
            if (!typeStoreOptional.isPresent()){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }else if (typeStoreOptional.get().getStatus() == status){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("The status has not changed because this status has been changed !").build(), HttpStatus.NOT_FOUND);
            }else {
                TypeStore typeStore = typeStoreOptional.get();
                typeStore.setStatus(status);
                typeStoreRepository.save(typeStore);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(new TypeStoreDto(typeStore))
                        .setMessage("Success!").build(), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<Object> statusTypeVoucher(long id, int status, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (status <0 || status >2) {
            hashMap.clear();
            hashMap.put("Status", "status is invalid! (0, 1, 2)");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Status has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            Optional<TypeVoucher> typeVoucherOptional = typeVoucherRepository.findById(id);
            if (!typeVoucherOptional.isPresent()){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }else if (typeVoucherOptional.get().getStatus() == status){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("The status has not changed because this status has been changed !").build(), HttpStatus.NOT_FOUND);
            }else {
                TypeVoucher typeVoucher = typeVoucherOptional.get();
                typeVoucher.setStatus(status);
                typeVoucherRepository.save(typeVoucher);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(new TypeVoucherDto(typeVoucher))
                        .setMessage("Success!").build(), HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<Object> statusCity(long id, int status, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (status <0 || status >2) {
            hashMap.clear();
            hashMap.put("Status", "status is invalid! (0, 1, 2)");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Status has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            Optional<City> cityOptional = cityRepository.findById(id);
            if (!cityOptional.isPresent()){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }else if (cityOptional.get().getStatus() == status){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData(StringUtils.EMPTY)
                        .setMessage("The status has not changed because this status has been changed !").build(), HttpStatus.NOT_FOUND);
            }else {
                City city = cityOptional.get();
                city.setStatus(status);
                cityRepository.save(city);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(new CityDto(city))
                        .setMessage("Success!").build(), HttpStatus.OK);
            }
        }
    }
}
