package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class History_Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String time;
    @NotNull
    private String day;
    @Null
    private String description;
    @NotNull
    private long created;
    @Null
    private long updated;
    @NotNull
    private int status;

    @ManyToOne
    @JoinColumn(name = "account_user_id", nullable = false)
    private Account_User account_user;

    @ManyToOne
    @JoinColumn(name = "store_address_id", nullable = false)
    private Store_Address store_address;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Account_User getAccount_user() {
        return account_user;
    }

    public void setAccount_user(Account_User account_user) {
        this.account_user = account_user;
    }

    public Store_Address getStore_address() {
        return store_address;
    }

    public void setStore_address(Store_Address store_address) {
        this.store_address = store_address;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
