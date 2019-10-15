package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupAccountDto.AccountInformationDto;
import com.focusteam.dealhunter.dto.groupAccountDto.CredentialDto;
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
import com.focusteam.dealhunter.rest.RESTLogin;
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

    // GET ALL
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
    public ResponseEntity<Object> getAllStore(HttpServletRequest request) {
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
            }else {
                hashMap.clear();
                hashMap.put("Store", "No acstorecount found !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<Object> getAllTypeStore(HttpServletRequest request) {
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
        } else {
            List<TypeStore> typeStores = typeStoreRepository.findAll();
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
            }else {
                hashMap.clear();
                hashMap.put("Type-Store", "No type store found !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<Object> getAllTypeVoucher(HttpServletRequest request) {
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
        } else {
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
            }else {
                hashMap.clear();
                hashMap.put("Type-Voucher", "No type voucher found !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<Object> getAllStoreAddress(HttpServletRequest request) {
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
        } else {
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
    }

    @Override
    public ResponseEntity<Object> getAllTransaction(HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (accountOptional.get().getTypeAccount() != 2){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else {
            List<Transaction> transactions = transactionRepository.findAll();
            if (!transactions.isEmpty()){
                List<TransactionDto> transactionDtos = new ArrayList<>();
                TransactionDto transactionDto = null;
                for (Transaction transaction: transactions
                ) {
                    transactionDto = new TransactionDto(transaction);
                    transactionDtos.add(transactionDto);
                }
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(transactionDtos)
                        .setMessage("Success!").build(), HttpStatus.OK);
            }else {
                hashMap.clear();
                hashMap.put("Transaction", "No transaction found !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }
        }
    }


    // GET ONE
    @Override
    public ResponseEntity<Object> getOneAccount(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Account> accountOptional2 = accountRepository.findById(id);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() != 2){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!accountOptional2.isPresent()){
            hashMap.clear();
            hashMap.put("Account", "Not found account with id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setData(StringUtils.EMPTY)
                    .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
        }else {
            Account account = accountOptional2.get();
            AccountInformationDto accountInformationDto = new AccountInformationDto(account);
            CredentialDto credentialDto = new CredentialDto(account.getCredential());
            RESTLogin restLogin = new RESTLogin(accountInformationDto, credentialDto);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(restLogin)
                    .setMessage("Found !").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> getOneStore(long id, HttpServletRequest request) {
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
        } else if (!storeOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No store found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.get().getTypeAccount() != 2){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else {
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
        }
    }

    @Override
    public ResponseEntity<Object> getOneTypeVoucher(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<TypeVoucher> typeVoucherOptional = typeVoucherRepository.findById(id);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!typeVoucherOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No type voucher found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.get().getTypeAccount() != 2){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else {
            TypeVoucher typeVoucher1 = typeVoucherOptional.get();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new TypeVoucherDto(typeVoucher1))
                    .setMessage("Type voucher with id = " + id + " !").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> getOneTypeStore(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<TypeStore> typeStoreOptional = typeStoreRepository.findById(id);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!typeStoreOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No type store  found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.get().getTypeAccount() != 2){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else {
            TypeStore typeStore = typeStoreOptional.get();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new TypeStoreDto(typeStore))
                    .setMessage("Type store with id = " + id + " !").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> getOneStoreAddress(long id, HttpServletRequest request) {
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
        } else if (!storeAddressOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No store address found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.get().getTypeAccount() != 2){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else {
            StoreAddress storeAddress = storeAddressOptional.get();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new StoreAddressDto(storeAddress))
                    .setMessage("Store address with id = " + id + " !").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> getOneTransaction(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!transactionOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No transaction found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.get().getTypeAccount() != 2){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else {
            Transaction transaction = transactionOptional.get();
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new TransactionDto(transaction))
                    .setMessage("Transaction with id = " + id + " !").build(), HttpStatus.OK);
        }
    }

}
