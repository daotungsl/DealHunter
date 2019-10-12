package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionCreateDto;
import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionDto;
import com.focusteam.dealhunter.entity.*;
import com.focusteam.dealhunter.repository.*;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.iml.AccountServices;
import com.focusteam.dealhunter.service.iml.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@Service("transactionServices")
public class DefaultTransactionServices implements TransactionServices {
    private HashMap<String, String> hashMap = new HashMap<>();

    @Autowired
    AccountServices accountServices;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    StoreAddressRepository storeAddressRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Override
    public ResponseEntity<Object> create(@Valid TransactionCreateDto transactionCreateDto, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(transactionCreateDto.getStoreId());
        Optional<StoreAddress> storeAddressOptional = storeAddressRepository.findById(transactionCreateDto.getStoreAddressId());
        Optional<Voucher> voucherOptional =  voucherRepository.findById(transactionCreateDto.getVoucherId());
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getId() != transactionCreateDto.getAccountId()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() == 1 && accountOptional.get().getStore() != null && accountOptional.get().getStore().getId() == transactionCreateDto.getStoreId()){
            hashMap.clear();
            hashMap.put("Account", "[SORRY] - You can't book a table at your own store!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Account has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!storeOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No store found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (!storeAddressOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("Store_Address", "No store address found !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
        } else if (! voucherOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No voucher found with this id = " + transactionCreateDto.getVoucherId() + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else {
            Transaction transaction = new Transaction(transactionCreateDto);
            transaction.setAccount(accountOptional.get());
            transaction.setStore(storeOptional.get());
            transaction.setStoreAddress(storeAddressOptional.get());
            transaction.setVoucher(voucherOptional.get());

            transactionRepository.save(transaction);

            Voucher voucher = voucherOptional.get();
            voucher.setSlotLeft(voucher.getSlotLeft() - 1);

            voucherRepository.save(voucher);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new TransactionDto(transaction, storeAddressOptional.get()))
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> getOneById(long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAll(HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAllWithOneAccount(String email, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAllByStore(String sName, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getAllByStoreAddress(long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(long id, HttpServletRequest request) {
        return null;
    }
}
