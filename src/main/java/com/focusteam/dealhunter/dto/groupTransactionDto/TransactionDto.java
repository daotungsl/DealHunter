package com.focusteam.dealhunter.dto.groupTransactionDto;

import com.focusteam.dealhunter.entity.StoreAddress;
import com.focusteam.dealhunter.entity.Transaction;

public class TransactionDto {
    private long id;
    private String guestName;
    private String guestPhone;
    private String guestEmail;
    private String storeName;
    private String storeAddress;
    private String time;
    private String day;
    private String adults;
    private String children;
    private String sale;
    private String code;
    private String description;

    public TransactionDto() {
    }

    public TransactionDto(Transaction transaction, StoreAddress storeAddress) {
        this.id = transaction.getId();
        this.guestName = transaction.getAccount().getUserInformation().getFullName();
        this.guestPhone = transaction.getAccount().getUserInformation().getPhone();
        this.guestEmail = transaction.getAccount().getUserInformation().getEmail();
        this.storeName = transaction.getStore().getName();
        this.storeAddress = storeAddress.getAddress();
        this.time = transaction.getTime();
        this.day = transaction.getDay();
        this.adults = transaction.getAdults() + " người lớn";
        this.children = transaction.getChildren() + " trẻ em";
        this.sale = transaction.getVoucher().getPercent() + " %";
        this.code = transaction.getVoucher().getCodeSale();
        this.description = transaction.getDescription();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public long getId() {
        return id;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
