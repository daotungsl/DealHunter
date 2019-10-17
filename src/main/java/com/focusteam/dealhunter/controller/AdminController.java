package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.AdminDto.SetAllStatus;
import com.focusteam.dealhunter.dto.groupCityDto.CityCreateDto;
import com.focusteam.dealhunter.dto.groupCityDto.CityUpdate;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreCreateDto;
import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreUpdate;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherCreateDto;
import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherUpdate;
import com.focusteam.dealhunter.service.impl.AdminServices;
import com.focusteam.dealhunter.service.impl.CityServices;
import com.focusteam.dealhunter.service.impl.TypeStoreServices;
import com.focusteam.dealhunter.service.impl.TypeVoucherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AdminController {
    @Autowired
    private AdminServices adminServices;

    @Autowired
    private CityServices cityServices;

    @Autowired
    TypeStoreServices typeStoreServices;

    @Autowired
    private TypeVoucherServices typeVoucherServices;

    // STATUS
    @CrossOrigin
    @RequestMapping(value = "/api/admin/account/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusAccount(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusAccount(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusStore(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusStore(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/store-address/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusStoreAddress(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusStoreAddress(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/voucher/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusVoucher(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusVoucher(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/transaction/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusTransaction(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusTransaction(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-store/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusTypeStore(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusTypeStore(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-voucher/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusTypeVoucher(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusTypeVoucher(id, status, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/city/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<Object> statusCity(@PathVariable long id, @PathVariable int status, HttpServletRequest request){
        return adminServices.statusCity(id, status, request);
    }

    // UPDATE - DELETE

    @CrossOrigin
    @RequestMapping(value = "/api/admin/cities/city", method = RequestMethod.POST)
    public ResponseEntity<Object> createCity(@Valid @RequestBody CityCreateDto cityCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return cityServices.create(cityCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/cities/city/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCity(@PathVariable long id , @Valid @RequestBody CityUpdate cityUpdate, BindingResult bindingResult, HttpServletRequest request){
        return cityServices.update(id, cityUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/cities/city/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCity(@PathVariable long id, HttpServletRequest request){
        return cityServices.delete(id, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-stores/type-store", method = RequestMethod.POST)
    public ResponseEntity<Object> createTypeStore(@Valid @RequestBody TypeStoreCreateDto typeStoreCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return typeStoreServices.create(typeStoreCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-store/type-store/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateTypeStore(@PathVariable long id , @Valid @RequestBody TypeStoreUpdate typeStoreUpdate, BindingResult bindingResult, HttpServletRequest request){
        return typeStoreServices.update(id, typeStoreUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-stores/type-store/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteTypeStore(@PathVariable long id, HttpServletRequest request){
        return typeStoreServices.delete(id, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-vouchers/type-voucher", method = RequestMethod.POST)
    public ResponseEntity<Object> createTypeVoucher(@Valid @RequestBody TypeVoucherCreateDto typeVoucherCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return typeVoucherServices.create(typeVoucherCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-vouchers/type-voucher/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateTypeVoucher(@PathVariable long id , @Valid @RequestBody TypeVoucherUpdate typeVoucherUpdate, BindingResult bindingResult, HttpServletRequest request){
        return typeVoucherServices.update(id, typeVoucherUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-vouchers/type-voucher/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteTypeVoucher(@PathVariable long id, HttpServletRequest request){
        return typeVoucherServices.delete(id, request);
    }

    // GET ALL

    @CrossOrigin
    @RequestMapping(value = "/api/admin/accounts", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllAccount(HttpServletRequest request){
        return adminServices.getAllAccount( request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/accounts/{type}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllAccountByType(@PathVariable int type, HttpServletRequest request){
        return adminServices.getAllAccountByType( type,request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/stores", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllStores(HttpServletRequest request){
        return adminServices.getAllStore( request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-stores", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllTypeStore(HttpServletRequest request){
        return adminServices.getAllTypeStore( request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllTypeVoucher(HttpServletRequest request){
        return adminServices.getAllTypeVoucher( request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/stores/store-addresses", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllStoreAddress(HttpServletRequest request){
        return adminServices.getAllStoreAddress( request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/stores/transactions", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllTransaction(HttpServletRequest request){
        return adminServices.getAllTransaction( request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/stores/vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllVouchers(HttpServletRequest request){
        return adminServices.getAllVoucher( request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/cities", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllCities(HttpServletRequest request){
        return adminServices.getAllCity(request);
    }

    //GET ONE

    @CrossOrigin
    @RequestMapping(value = "/api/admin/account/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneAccount(@PathVariable long id, HttpServletRequest request){
        return adminServices.getOneAccount(id, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneStore(@PathVariable long id, HttpServletRequest request){
        return adminServices.getOneStore(id, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-store/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneTypeStore(@PathVariable long id, HttpServletRequest request){
        return adminServices.getOneTypeStore(id, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/type-voucher/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneTypeVoucher(@PathVariable long id, HttpServletRequest request){
        return adminServices.getOneTypeVoucher(id, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/store-address/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneStoreAddress(@PathVariable long id, HttpServletRequest request){
        return adminServices.getOneStoreAddress(id, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/store/transaction/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOneTransaction(@PathVariable long id, HttpServletRequest request){
        return adminServices.getOneTransaction(id, request);
    }

    // SET LIST STATUS

    @CrossOrigin
    @RequestMapping(value = "/api/admin/change-status", method = RequestMethod.PUT)
    public ResponseEntity<Object> setListAccount(@RequestBody SetAllStatus setAllStatus, HttpServletRequest request){
        return adminServices.statusListObject(setAllStatus, request);
    }

    // COUNT ALL

    @CrossOrigin
    @RequestMapping(value = "/api/admin/count-all", method = RequestMethod.GET)
    public ResponseEntity<Object> countAll( HttpServletRequest request){
        return adminServices.countAll( request);
    }
}
