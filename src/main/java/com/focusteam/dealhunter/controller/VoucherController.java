package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupCityDto.CityCreateDto;
import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherCreateDto;
import com.focusteam.dealhunter.service.iml.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/voucher", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody VoucherCreateDto voucherCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return voucherService.create(voucherCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/vouchers/voucher/-/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        return voucherService.getOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/vouchers/voucher/{name}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable String name){
        return voucherService.getOneByNameUA(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        return voucherService.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/{name}/vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllByStore(@PathVariable String name){
        return voucherService.getAllByStore(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/typeVouchers/typeVoucher/{name}/vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllByTypeVoucher(@PathVariable String name){
        return voucherService.getAllByTypeVoucher(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/cities/city/{name}/vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllByCity(@PathVariable String name){
        return voucherService.getAllByCity(name);
    }
}
