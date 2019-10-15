package com.focusteam.dealhunter.controller;

import com.focusteam.dealhunter.service.impl.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class StoreManagerController {
    @Autowired
    TransactionServices transactionServices;

    // Count all transaction of one store

    @CrossOrigin
    @RequestMapping(value = "/api/stores/transactions/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> countByStore(@PathVariable long id, HttpServletRequest request){
        return transactionServices.countByStore(id , request);
    }

    // Count all transaction of one store from day ( long ) to day (long)
    @CrossOrigin
    @RequestMapping(value = "/api/stores/transactions/{id}/from/{from}/to/{to}", method = RequestMethod.GET)
    public ResponseEntity<Object> countByStoreFromTo(@PathVariable long id, @PathVariable long from, @PathVariable long to, HttpServletRequest request){
        return transactionServices.countByStoreFromTo(id , from, to,request);
    }


    // Count all transaction of one store from day to day
    // day format : 12-09-2019 || 12.09.2019 || 12 09 2019
    // or : 12.09-2019, i can convert to 12-09-2019 for select data and return
    @CrossOrigin
    @RequestMapping(value = "/api/stores/transactions/{id}/from-day/{from}/to/{to}", method = RequestMethod.GET)
    public ResponseEntity<Object> countByStoreFromToDay(@PathVariable long id, @PathVariable String from, @PathVariable String to, HttpServletRequest request){
        return transactionServices.countByStoreFromToDayString(id , from, to,request);
    }


}
