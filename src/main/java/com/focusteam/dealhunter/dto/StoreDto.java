package com.focusteam.dealhunter.dto;

import com.focusteam.dealhunter.entity.Store;

public class StoreDto {

    private String name;
    private String email;
    private String phone;
    private String image;
    private long created;
    private long updated;
    private int status;
    private long accountId;
    private long typeStoreId;

    public StoreDto() {
    }

    public StoreDto(Store store) {
        this.name = store.getName();
        this.email = store.getEmail();
        this.phone = store.getPhone();
        this.image = store.getImages();
        this.created = store.getCreated();
        this.updated = store.getUpdated();
        this.accountId = store.getAccount().getId();
        this.typeStoreId = store.getTypeStore().getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getTypeStoreId() {
        return typeStoreId;
    }

    public void setTypeStoreId(long typeStoreId) {
        this.typeStoreId = typeStoreId;
    }

}
