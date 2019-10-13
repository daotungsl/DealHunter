package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionCreateDto;
import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionDto;
import com.focusteam.dealhunter.entity.*;
import com.focusteam.dealhunter.repository.*;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.impl.AccountServices;
import com.focusteam.dealhunter.service.impl.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //OK
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
        } else if (accountOptional.get().getStatus() != 1) {
            hashMap.clear();
            hashMap.put("Email", "You need to confirm your email first !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Email has errors !").build(), HttpStatus.UNAUTHORIZED);
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
        } else if (!voucherOptional.isPresent()) {
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
                    .setData(new TransactionDto(transaction))
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
    }

    //OK
    @Override
    public ResponseEntity<Object> getOneById(long id, HttpServletRequest request) {
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
        }else if (accountOptional.get().getId() != transactionOptional.get().getAccount().getId()){
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

    //OK
    @Override
    public ResponseEntity<Object> getAll(HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getTypeAccount() == 1){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            List<Transaction> transactionLList = transactionRepository.findAll();
            if (!transactionLList.isEmpty()){
                List<TransactionDto> transactionDtos = new ArrayList<>();;
                TransactionDto transactionDto = null;
                for (Transaction transaction: transactionLList
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

    //OK
    @Override
    public ResponseEntity<Object> getAllWithOneAccount(String email, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Account> accountOptional1 = accountRepository.findByUsername(email);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!accountOptional1.isPresent()) {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!accountOptional.get().equals(accountOptional1.get())) {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else {
            List<Transaction> transactions = new ArrayList<>(accountOptional.get().getTransactions());
            if (!transactions.isEmpty()) {
                List<TransactionDto> transactionDtos = new ArrayList<>();
                TransactionDto transactionDto = null;
                for (Transaction transaction : transactions
                ) {
                    transactionDto = new TransactionDto(transaction);
                    transactionDtos.add(transactionDto);
                }
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(transactionDtos)
                        .setMessage("Success!").build(), HttpStatus.OK);
            } else {
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

    @Override
    public ResponseEntity<Object> getAllByStore(String name, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findByNameUnAccent(name);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!storeOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No store found with this id = " + name + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getStore().getId() != storeOptional.get().getId()) {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            List<Transaction> transactions = new ArrayList<>(storeOptional.get().getTransactions());
            if (!transactions.isEmpty()) {
                List<TransactionDto> transactionDtos = new ArrayList<>();
                TransactionDto transactionDto = null;
                for (Transaction transaction : transactions
                ) {
                    transactionDto = new TransactionDto(transaction);
                    transactionDtos.add(transactionDto);
                }
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(transactionDtos)
                        .setMessage("Success!").build(), HttpStatus.OK);
            } else {
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

    @Override
    public ResponseEntity<Object> getAllByStoreAddress(long id, String name, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findByNameUnAccent(name);
        Optional<StoreAddress> storeAddressOptional = storeAddressRepository.findById(id);

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
            hashMap.put("ID", "No store found with this id = " + name + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (!storeAddressOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No store address found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.get().getStore().getId() != storeOptional.get().getId()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (storeOptional.get().getId() != storeAddressOptional.get().getStore().getId()){
            hashMap.clear();
            hashMap.put("Store-Address", storeOptional.get().getName()+ " has no branch at this address !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            List<Transaction> transactions = new ArrayList<>(storeAddressOptional.get().getTransactions());
            if (!transactions.isEmpty()) {
                List<TransactionDto> transactionDtos = new ArrayList<>();
                TransactionDto transactionDto = null;
                for (Transaction transaction : transactions
                ) {
                    transactionDto = new TransactionDto(transaction);
                    transactionDtos.add(transactionDto);
                }
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(transactionDtos)
                        .setMessage("Success!").build(), HttpStatus.OK);
            } else {
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

    @Override
    public ResponseEntity<Object> delete(long id, HttpServletRequest request) {
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
        }else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getTypeAccount() == 1){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!transactionOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No transaction found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else {
            Transaction transaction = transactionOptional.get();
            transactionRepository.delete(transaction);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new TransactionDto(transaction))
                    .setMessage("Delete store address success !").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> countByStore(long id, HttpServletRequest request) {
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
        }else if (!storeOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No store found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getStore().getId() != storeOptional.get().getId()) {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            List<Integer> data = transactionRepository.countByStore(id);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(data)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
    }
}
