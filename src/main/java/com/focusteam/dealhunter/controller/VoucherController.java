package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherCreateDto;
import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherUpdate;
import com.focusteam.dealhunter.service.iml.VoucherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class VoucherController {
    @Autowired
    private VoucherServices voucherServices;

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/vouchers/voucher", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody VoucherCreateDto voucherCreateDto, BindingResult bindingResult, HttpServletRequest request){
        return voucherServices.create(voucherCreateDto, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/vouchers/voucher/-/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable long id){
        return voucherServices.getOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/vouchers/voucher/{name}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOne(@PathVariable String name){
        return voucherServices.getOneByNameUA(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        return voucherServices.getAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/{name}/vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllByStore(@PathVariable String name){
        return voucherServices.getAllByStore(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/stores/store/{sname}/vouchers/voucher/{vname}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllByStore(@PathVariable String sname, @PathVariable String vname){
        return voucherServices.getOneByStoreNameUA(sname, vname);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/type-vouchers/type-voucher/{name}/vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllByTypeVoucher(@PathVariable String name){
        return voucherServices.getAllByTypeVoucher(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/unauthentic/cities/city/{name}/vouchers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllByCity(@PathVariable String name){
        return voucherServices.getAllByCity(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/stores/store/vouchers/voucher/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody VoucherUpdate voucherUpdate, BindingResult bindingResult, HttpServletRequest request){
        return voucherServices.update(id, voucherUpdate, bindingResult, request);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/admin/stores/store/vouchers/voucher/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id, HttpServletRequest request){
        return voucherServices.delete(id, request);
    }
}
