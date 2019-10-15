package com.focusteam.dealhunter.service.defaultService;

import com.focusteam.dealhunter.dto.groupStoreDto.StoreAddressDto;
import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherCreateDto;
import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherDto;
import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherUpdate;
import com.focusteam.dealhunter.entity.*;
import com.focusteam.dealhunter.repository.*;
import com.focusteam.dealhunter.rest.RESTResponse;
import com.focusteam.dealhunter.service.impl.AccountServices;
import com.focusteam.dealhunter.service.impl.VoucherServices;
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

@Service("voucherServices")
public class DefaultVoucherServices implements VoucherServices {
    private HashMap<String, String> hashMap = new HashMap<>();
    private HashSet<Long> hashSet = new HashSet<Long>();

    @Autowired
    AccountServices accountServices;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TypeVoucherRepository typeVoucherRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    PromotionTimeRepository promotionTimeRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public ResponseEntity<Object> create(@Valid VoucherCreateDto voucherCreateDto, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Store> storeOptional = storeRepository.findById(voucherCreateDto.getStoreId());
        Optional<TypeVoucher> typeVoucherOptional = typeVoucherRepository.findById(voucherCreateDto.getTypeVoucherId());

        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (accountOptional.get().getStore().getId() != voucherCreateDto.getStoreId() || accountOptional.get().getTypeAccount() == 0){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
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
        } else if (!typeVoucherOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No type voucher found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else {
            Voucher voucher = new Voucher(voucherCreateDto);
            voucher.setIcon(storeOptional.get().getImages());
            voucher.setStore(storeOptional.get());
            voucher.setTypeVoucher(typeVoucherOptional.get());

            voucherRepository.save(voucher);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.CREATED.value())
                    .setData(new VoucherDto(voucher))
                    .setMessage("Created voucher success !").build(), HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Object> getOne(long id) {
        Optional<Voucher> voucherOptional = voucherRepository.findByIdAndStatus(id);
        if (voucherOptional.isPresent()){
            Voucher voucher = voucherOptional.get();
            if (voucher.getStore().getStoreAddresses() != null){
                List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(voucher.getStore().getStoreAddresses());
                List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
                for (StoreAddress s: storeAddressList
                ) {
                    storeAddressDtoList.add(new StoreAddressDto(s));
                }
                VoucherDto voucherDto = new VoucherDto(voucher);
                voucherDto.setStoreAddress(storeAddressDtoList);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(voucherDto)
                        .setMessage("Voucher with id = " + id + " !").build(), HttpStatus.OK);
            }else {
                VoucherDto voucherDto = new VoucherDto(voucher);
                voucherDto.setStoreAddress(null);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(voucherDto)
                        .setMessage("Voucher with id = " + id + " !").build(), HttpStatus.OK);
            }
        }else {
            hashMap.clear();
            hashMap.put("ID", "No voucher found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<Object> getOneByNameUA(String name) {
        Optional<Voucher> voucherOptional = voucherRepository.findByNameUAAndStatus(name);
        if (voucherOptional.isPresent()){
            Voucher voucher = voucherOptional.get();
            if (voucher.getStore().getStoreAddresses() != null){
                List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(voucher.getStore().getStoreAddresses());
                List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
                for (StoreAddress s: storeAddressList
                ) {
                    storeAddressDtoList.add(new StoreAddressDto(s));
                }
                VoucherDto voucherDto = new VoucherDto(voucher);
                voucherDto.setStoreAddress(storeAddressDtoList);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(voucherDto)
                        .setMessage("Voucher with name ua = " + name + " !").build(), HttpStatus.OK);
            }else {
                VoucherDto voucherDto = new VoucherDto(voucher);
                voucherDto.setStoreAddress(null);

                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(voucherDto)
                        .setMessage("Voucher with name ua = " + name + " !").build(), HttpStatus.OK);
            }
        }else {
            hashMap.clear();
            hashMap.put("ID", "No voucher found with this nameUA = " + name + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<Object> getOneByStoreNameUA(String sNameUA, String vNameUA) {
        Optional<Store> storeOptional = storeRepository.findByNameUnAccentAndStatus(sNameUA);
        if (!storeOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Name", "No store found with this name = " + sNameUA + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else {
            List<Voucher> vouchers = new ArrayList<>(storeOptional.get().getVouchers());
            if (!vouchers.isEmpty()){
                //List<VoucherDto> voucherDtos = new ArrayList<>();
                VoucherDto voucherDto = null;
                for (Voucher voucher: vouchers
                ) {
                    if (voucher.getNameUnAccent().equalsIgnoreCase(vNameUA)){
                        if (voucher.getStore().getStoreAddresses() != null){
                            List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(voucher.getStore().getStoreAddresses());
                            List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
                            for (StoreAddress s: storeAddressList
                            ) {
                                storeAddressDtoList.add(new StoreAddressDto(s));
                            }
                            voucherDto = new VoucherDto(voucher);
                            voucherDto.setStoreAddress(storeAddressDtoList);
                        }else {
                            voucherDto = new VoucherDto(voucher);
                            voucherDto.setStoreAddress(null);
                        }
                        //voucherDto = new VoucherDto(voucher);
                        //voucherDtos.add(voucherDto);
                    } else {
                        vouchers.remove(voucher);
                    }
                }
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(voucherDto)
                        .setMessage("Voucher with name = " + vNameUA + " !").build(), HttpStatus.OK);
            }else {
                hashMap.clear();
                hashMap.put("Voucher", "No voucher found !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<Object> getAll() {
        List<Voucher> vouchers = voucherRepository.getAllByStatus();
        if (!vouchers.isEmpty()){
            List<VoucherDto> voucherDtos = new ArrayList<>();
            VoucherDto voucherDto = null;
            for (Voucher voucher: vouchers
            ) {
                if (voucher.getStore().getStoreAddresses() != null){
                    List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(voucher.getStore().getStoreAddresses());
                    List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
                    for (StoreAddress s: storeAddressList
                    ) {
                        storeAddressDtoList.add(new StoreAddressDto(s));
                    }
                    voucherDto = new VoucherDto(voucher);
                    voucherDto.setStoreAddress(storeAddressDtoList);
                    voucherDtos.add(voucherDto);
                }else {
                    voucherDto = new VoucherDto(voucher);
                    voucherDto.setStoreAddress(null);
                    voucherDtos.add(voucherDto);
                }
            }
            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(voucherDtos)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
        hashMap.clear();
        hashMap.put("Voucher", "No voucher found !");
        return new ResponseEntity<>(new RESTResponse.Error()
                .addErrors(hashMap)
                .setStatus(HttpStatus.NOT_FOUND.value())
                .setData("")
                .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> getAllByStore(String name) {
        Optional<Store> storeOptional = storeRepository.findByNameUnAccentAndStatus(name);
        if (!storeOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Name", "No store found with this name = " + name + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else {
            List<Voucher> vouchers = new ArrayList<>(storeOptional.get().getVouchers());
            if (!vouchers.isEmpty()){
                List<VoucherDto> voucherDtos = new ArrayList<>();
                VoucherDto voucherDto = null;
                for (Voucher voucher: vouchers
                ) {
                    if (voucher.getStore().getStoreAddresses() != null){
                        List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(voucher.getStore().getStoreAddresses());
                        List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
                        for (StoreAddress s: storeAddressList
                        ) {
                            storeAddressDtoList.add(new StoreAddressDto(s));
                        }
                        voucherDto = new VoucherDto(voucher);
                        voucherDto.setStoreAddress(storeAddressDtoList);
                        voucherDtos.add(voucherDto);
                    }else {
                        voucherDto = new VoucherDto(voucher);
                        voucherDto.setStoreAddress(null);
                        voucherDtos.add(voucherDto);
                    }
                }
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(voucherDtos)
                        .setMessage("Success!").build(), HttpStatus.OK);
            }else {
                hashMap.clear();
                hashMap.put("Voucher", "No voucher found !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<Object> getAllByTypeVoucher(String name) {
        Optional<TypeVoucher> typeVoucherOptional = typeVoucherRepository.findByNameUnAccentAndStatus(name);
        if (!typeVoucherOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Name", "No type voucher found with this name = " + name + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else {
            List<Voucher> vouchers = new ArrayList<>(typeVoucherOptional.get().getVouchers());
            if (!vouchers.isEmpty()){
                List<VoucherDto> voucherDtos = new ArrayList<>();
                VoucherDto voucherDto = null;
                for (Voucher voucher: vouchers
                ) {
                    if (voucher.getStore().getStoreAddresses() != null){
                        List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(voucher.getStore().getStoreAddresses());
                        List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
                        for (StoreAddress s: storeAddressList
                        ) {
                            storeAddressDtoList.add(new StoreAddressDto(s));
                        }
                        voucherDto = new VoucherDto(voucher);
                        voucherDto.setStoreAddress(storeAddressDtoList);
                        voucherDtos.add(voucherDto);
                    }else {
                        voucherDto = new VoucherDto(voucher);
                        voucherDto.setStoreAddress(null);
                        voucherDtos.add(voucherDto);
                    }
                }
                return new ResponseEntity<>(new RESTResponse.Success()
                        .setStatus(HttpStatus.OK.value())
                        .setData(voucherDtos)
                        .setMessage("Success!").build(), HttpStatus.OK);
            }else {
                hashMap.clear();
                hashMap.put("Voucher", "No voucher found !");
                return new ResponseEntity<>(new RESTResponse.Error()
                        .addErrors(hashMap)
                        .setStatus(HttpStatus.NOT_FOUND.value())
                        .setData("")
                        .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<Object> getAllByCity(String name) {
        Optional<City> cityOptional = cityRepository.findByNameUnAccentAndStatus(name);
        List<VoucherDto> voucherDtos = new ArrayList<>();
        if (!cityOptional.isPresent()){
            hashMap.clear();
            hashMap.put("ID", "No city found with this nameUA = " + name + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else {
            List<StoreAddress> storeAddressList = new ArrayList<>(cityOptional.get().getStoreAddresses());
            for (StoreAddress storeAddress: storeAddressList
                 ) {
                long id = storeAddress.getStore().getId();
                hashSet.add(id);
            }

            for (Long id : hashSet){
                Optional<Store> storeOptional = storeRepository.findById(id);
                if (storeOptional.isPresent()){
                    voucherDtos = getVoucherByStore(storeOptional.get());
                }else {
                    hashMap.clear();
                    hashMap.put("Voucher", "No voucher found !");
                    return new ResponseEntity<>(new RESTResponse.Error()
                            .addErrors(hashMap)
                            .setStatus(HttpStatus.NOT_FOUND.value())
                            .setData("")
                            .setMessage("Not found !").build(), HttpStatus.NOT_FOUND);
                }
            }

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(voucherDtos)
                    .setMessage("Success!").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> update(long id, @Valid VoucherUpdate voucherUpdate, BindingResult bindingResult, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Voucher> voucherOptional = voucherRepository.findById(id);
        Optional<Store> storeOptional = storeRepository.findById(voucherUpdate.getStoreId());
        Optional<TypeVoucher> typeVoucherOptional = typeVoucherRepository.findById(voucherUpdate.getTypeVoucherId());
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (accountOptional.get().getStore().getId() != voucherUpdate.getStoreId() || accountOptional.get().getTypeAccount() == 0) {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        }else if (!voucherOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No voucher found with this id = " + id + " !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (id != voucherUpdate.getId()){
            hashMap.clear();
            hashMap.put("ID", "The id in path variable does not match the id in the data update !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Data error !").build(), HttpStatus.UNAUTHORIZED);
        } else if (bindingResult.hasErrors()) {
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
                    .setMessage("Voucher data has errors !").build(), HttpStatus.FORBIDDEN);
        } else if (!storeOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No store found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        } else if (!typeVoucherOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No type voucher found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (voucherOptional.get().getStatus() == 1){
            hashMap.clear();
            hashMap.put("Update", "You cannot change the information once the voucher has been confirmed !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else {
            Voucher voucher = voucherOptional.get();
            voucher.setName(voucherUpdate.getName());
            voucher.setDescription(voucherUpdate.getDescription());
            voucher.setImage(voucherUpdate.getImage());
            voucher.setNameUnAccent(new StringUtil().unAccent(voucherUpdate.getName() + " " + voucher.getCodeSale()));
            voucher.setPercent(voucherUpdate.getPercent());
            voucher.setMaxSlot(voucherUpdate.getMaxSlot());
            voucher.setSlotLeft(voucherUpdate.getMaxSlot());
            voucher.setStartDay(voucherUpdate.getStartDay());
            voucher.setExpiredDay(voucherUpdate.getExpiredDay());
            voucher.setUpdated(Calendar.getInstance().getTimeInMillis());

            voucher.setStore(storeOptional.get());
            voucher.setTypeVoucher(typeVoucherOptional.get());

            voucher.getPromotionTime().setStartTime(voucherUpdate.getStartTime());
            voucher.getPromotionTime().setEndTime(voucherUpdate.getEndTime());
            voucher.getPromotionTime().setDayWeek(voucherUpdate.getDayWeek());

            voucherRepository.save(voucher);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new VoucherDto(voucher))
                    .setMessage("Voucher update success !").build(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> delete(long id, HttpServletRequest request) {
        Optional<Account> accountOptional = accountRepository.findByTokenAccount(request.getHeader("Authorization"));
        Optional<Voucher> voucherOptional = voucherRepository.findById(id);
        if (!accountOptional.isPresent()){
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else if (!voucherOptional.isPresent()) {
            hashMap.clear();
            hashMap.put("ID", "No voucher found with this id !");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.FORBIDDEN.value())
                    .setData("")
                    .setMessage("Not found !").build(), HttpStatus.FORBIDDEN);
        }else if (accountOptional.get().getTypeAccount() == 1 || accountOptional.get().getTypeAccount() == 0) {
            hashMap.clear();
            hashMap.put("Authorization", "[ACCESS DENIED] - You do not have access!");
            return new ResponseEntity<>(new RESTResponse.Error()
                    .addErrors(hashMap)
                    .setStatus(HttpStatus.UNAUTHORIZED.value())
                    .setData("")
                    .setMessage("Authorization has errors !").build(), HttpStatus.UNAUTHORIZED);
        } else {
            Voucher voucher = voucherOptional.get();
            voucherRepository.delete(voucher);

            return new ResponseEntity<>(new RESTResponse.Success()
                    .setStatus(HttpStatus.OK.value())
                    .setData(new VoucherDto(voucher))
                    .setMessage("Delete voucher success !").build(), HttpStatus.OK);
        }
    }

    private List<VoucherDto> getVoucherByStore(Store store){
            List<VoucherDto> voucherDtos = new ArrayList<>();
            List<Voucher> vouchers = new ArrayList<>(store.getVouchers());
            if (!vouchers.isEmpty()){
                VoucherDto voucherDto = null;
                for (Voucher voucher: vouchers
                ) {
                    if (voucher.getStore().getStoreAddresses() != null){
                        List<StoreAddress> storeAddressList = new ArrayList<StoreAddress>(voucher.getStore().getStoreAddresses());
                        List<StoreAddressDto> storeAddressDtoList = new ArrayList<>();
                        for (StoreAddress s: storeAddressList
                        ) {
                            storeAddressDtoList.add(new StoreAddressDto(s));
                        }
                        voucherDto = new VoucherDto(voucher);
                        voucherDto.setStoreAddress(storeAddressDtoList);
                        voucherDtos.add(voucherDto);
                    }else {
                        voucherDto = new VoucherDto(voucher);
                        voucherDto.setStoreAddress(null);
                        voucherDtos.add(voucherDto);
                    }
                }
                return voucherDtos;
            }
            return null;
    }

}
