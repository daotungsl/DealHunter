package com.focusteam.dealhunter.service.iml;

import org.springframework.http.ResponseEntity;

public interface AdminServices {
    public ResponseEntity<Object> statusAccount();

    public ResponseEntity<Object> statusStore();

    public ResponseEntity<Object> statusStoreAddress();

    public ResponseEntity<Object> statusVoucher();

    public ResponseEntity<Object> statusTransaction();

}
