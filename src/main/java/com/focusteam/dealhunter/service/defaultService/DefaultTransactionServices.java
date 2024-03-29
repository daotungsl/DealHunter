package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionCreateDto;
import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionDto;
import com.focusteam.dealhunter.entity.*;
import com.focusteam.dealhunter.repository.*;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.impl.AccountServices;
import com.focusteam.dealhunter.service.impl.EmailServices;
import com.focusteam.dealhunter.service.impl.TransactionServices;
import com.focusteam.dealhunter.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

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

    @Autowired
    private EmailServices emailServices;

    //OK
    @Override
    public ResponseEntity<Object> create(@Valid TransactionCreateDto transactionCreateDto, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(transactionCreateDto.getStoreId());
        Optional<StoreAddress> storeAddressOptional = storeAddressRepository.findById(transactionCreateDto.getStoreAddressId());
        Optional<Voucher> voucherOptional =  voucherRepository.findById(transactionCreateDto.getVoucherId());
        if (!accountOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getId() != transactionCreateDto.getAccountId()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() == 1 && accountOptional.get().getStore() != null && accountOptional.get().getStore().getId() == transactionCreateDto.getStoreId()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[SORRY] - You can't book a table at your own store!").build(), HttpStatus.UNAUTHORIZED);
        } else if (accountOptional.get().getStatus() != 1) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("You need to confirm your email first !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!storeOptional.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("No store found with this id !").build(), HttpStatus.FORBIDDEN);
        } else if (!storeAddressOptional.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.NOT_FOUND.value())
                    .setData("")
                    .setMessage("No store address found !").build(), HttpStatus.NOT_FOUND);
        } else if (!voucherOptional.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("No voucher found with this id = \" + transactionCreateDto.getVoucherId() + \" !").build(), HttpStatus.FORBIDDEN);
        } else {
            Voucher voucher = voucherOptional.get();
            Transaction transaction = new Transaction(transactionCreateDto);

            transaction.setCodeId(voucher.getCodeSale() + Calendar.getInstance().getTimeInMillis());
            transaction.setAccount(accountOptional.get());
            transaction.setStore(storeOptional.get());
            transaction.setStoreAddress(storeAddressOptional.get());
            transaction.setVoucher(voucherOptional.get());

            transactionRepository.save(transaction);

            voucher.setSlotLeft(voucher.getSlotLeft() - 1);

            voucherRepository.save(voucher);
            TransactionDto transactionDto = new TransactionDto(transaction);

            String callBack = "http://13.76.164.246:8080/unauthentic/account/transaction/confirm/" + transaction.getId() + "/1";
            //String callBack = "http://localhost:8080/unauthentic/account/transaction/confirm/" + transaction.getId() + "/1";
            String messageBody = "<table>\n" +
                    "    <tbody>\n" +
                    "        <tr>\n" +
                    "            <td>Họ Tên : </td>\n" +
                    "            <td>" + transactionDto.getGuestName()+ "</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Điện Thoại : </td>\n" +
                    "            <td>"+transactionDto.getGuestPhone()+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Nhà Hàng : </td>\n" +
                    "            <td>"+transactionDto.getStoreName()+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Địa chỉ : </td>\n" +
                    "            <td>"+transactionDto.getStoreAddress()+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Thời gian : </td>\n" +
                    "            <td>"+transactionDto.getTime() + " " + transactionDto.getDay()+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Số người : </td>\n" +
                    "            <td>" + transactionDto.getAdults() + " vả " + transactionDto.getChildren()+ "</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Mã giảm giá : </td>\n" +
                    "            <td>" + transactionDto.getCode()+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>Note : </td>\n" +
                    "            <td>"+ transactionDto.getDescription()+"</td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>";

            emailServices.sendMessageWithAttachment(accountOptional.get().getUsername(), "Xác nhận đặt bàn", accountOptional.get().getUserInformation().getFullName(), callBack, messageBody);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(transactionDto)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
    }

    //OK
    @Override
    public ResponseEntity<Object> getOneById(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (!accountOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
        } else if (!transactionOptional.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("No transaction found with this id !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.get().getId() != transactionOptional.get().getAccount().getId()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
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
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getTypeAccount() == 1){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
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
                return new ResponseEntity<>(new RESTResponse.Error()
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
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
        } else if (!accountOptional1.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
        } else if (!accountOptional.get().equals(accountOptional1.get())) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("[ACCESS DENIED] - You do not have access!").build(), HttpStatus.UNAUTHORIZED);
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
                return new ResponseEntity<>(new RESTResponse.Error()
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
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!storeOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("No store found with this id = " + name + " !").build(), HttpStatus.FORBIDDEN);
        } else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getStore().getId() != storeOptional.get().getId()) {
            return new ResponseEntity<>(new RESTResponse.Error()
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
                return new ResponseEntity<>(new RESTResponse.Error()
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
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!storeOptional.isPresent()) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("No store found with this id = " + name + " !").build(), HttpStatus.FORBIDDEN);
        }else if (!storeAddressOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("No store address found with this id = " + id + " !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.get().getStore().getId() != storeOptional.get().getId()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (storeOptional.get().getId() != storeAddressOptional.get().getStore().getId()){
            return new ResponseEntity<>(new RESTResponse.Error()
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
                return new ResponseEntity<>(new RESTResponse.Error()
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
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getTypeAccount() == 1){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!transactionOptional.isPresent()){
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
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!storeOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getStore().getId() != storeOptional.get().getId()) {
            return new ResponseEntity<>(new RESTResponse.Error()
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

    @Override
    public ResponseEntity<Object> countByStoreFromTo(long id, long from, long to, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(id);
        if (!accountOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!storeOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getStore().getId() != storeOptional.get().getId()) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            List<Integer> data = transactionRepository.countByStoreFromTo(id, from, to);
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(data)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> countByStoreFromToDayString(long id, String from, String to, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(id);
        if (!accountOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!storeOptional.isPresent()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (accountOptional.get().getTypeAccount() == 0 || accountOptional.get().getStore().getId() != storeOptional.get().getId()) {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else {
            List<Integer> data = transactionRepository.countByStoreFromTo(id, new StringUtil().stringToLong(from), new StringUtil().stringToLong(to));
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(data)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
    }

    @Override
    public HttpStatus confirmTransaction(long id, int status) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isPresent()){
            Transaction transaction = transactionOptional.get();
            transaction.setStatus(status);
            transactionRepository.save(transaction);
            return HttpStatus.OK;
        }else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
