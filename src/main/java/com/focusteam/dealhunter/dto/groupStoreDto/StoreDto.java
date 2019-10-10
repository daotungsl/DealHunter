package com.focusteam.dealhunter.dto.groupStoreDto;

import com.focusteam.dealhunter.entity.Store;
import com.focusteam.dealhunter.entity.StoreAddress;
import com.focusteam.dealhunter.util.StringUtil;

import java.util.List;

public class StoreDto {
    private long id;
    private String name;
    private String nameUnAccent;
    private String email;
    private String phone;
    private String image;

    private String created;
    private String updated;
    private int status;

    private long accountId;

    private String typeStore;

    private List<StoreAddressDto> storeAddresses;

    public StoreDto() {
    }

    public StoreDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.nameUnAccent = store.getNameUnAccent();
        this.email = store.getEmail();
        this.phone = store.getPhone();
        this.image = store.getImages();
        this.created = new StringUtil().dateFormatFromLong(store.getCreated());
        this.updated = new StringUtil().dateFormatFromLong(store.getUpdated());
        this.status = store.getStatus();
        this.accountId = store.getAccount().getId();
        this.typeStore = store.getTypeStore().getName();
    }

    public String getNameUnAccent() {
        return nameUnAccent;
    }

    public void setNameUnAccent(String nameUnAccent) {
        this.nameUnAccent = nameUnAccent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
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

    public String getTypeStore() {
        return typeStore;
    }

    public void setTypeStore(String typeStore) {
        this.typeStore = typeStore;
    }

    public List<StoreAddressDto> getStoreAddresses() {
        return storeAddresses;
    }

    public void setStoreAddresses(List<StoreAddressDto> storeAddresses) {
        this.storeAddresses = storeAddresses;
    }
}
