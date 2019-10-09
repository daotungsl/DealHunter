package com.focusteam.dealhunter.dto.groupStoreDto;

import com.focusteam.dealhunter.entity.Store;
import com.focusteam.dealhunter.entity.StoreAddress;
import com.focusteam.dealhunter.util.StringUtil;

import java.util.List;

public class StoreDto {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String image;

    private String created;
    private String updated;
    private String status;
    private long accountId;

    private String typeStore;

    private List<StoreAddressDto> storeAddresses;

    public StoreDto() {
    }

    public StoreDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.email = store.getEmail();
        this.phone = store.getPhone();
        this.image = store.getImages();
        this.created = new StringUtil().dateFormatFromLong(store.getCreated());
        this.updated = new StringUtil().dateFormatFromLong(store.getUpdated());
        if (store.getStatus() == 0){
            this.status = "not activated";
        }else {
            this.status = "activated";
        }
        this.accountId = store.getAccount().getId();
        this.typeStore = store.getTypeStore().getName();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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