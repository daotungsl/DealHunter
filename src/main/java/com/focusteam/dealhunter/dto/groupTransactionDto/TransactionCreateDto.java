package com.focusteam.dealhunter.dto.groupTransactionDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionCreateDto {
    @NotNull
    private long accountId;
    @NotNull
    private long storeId;
    @NotNull
    private long storeAddressId;

    @NotNull
    private long voucherId;

    @NotNull
    private int adults;

    private int children;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 10)
    private String time;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String day;


    private String description;


    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getStoreAddressId() {
        return storeAddressId;
    }

    public void setStoreAddressId(long storeAddressId) {
        this.storeAddressId = storeAddressId;
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
