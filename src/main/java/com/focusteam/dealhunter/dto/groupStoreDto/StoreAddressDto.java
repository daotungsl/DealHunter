package com.focusteam.dealhunter.dto.groupStoreDto;

import com.focusteam.dealhunter.entity.StoreAddress;
import com.focusteam.dealhunter.util.StringUtil;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StoreAddressDto {
    private long id;
    private String store;
    @NotNull
    private String address;
    private String city;
    @Size(min = 15, max = 200000)
    private String description;
    @NotNull
    private String created;
    private String updated;
    @NotNull
    private int status;

    public StoreAddressDto() {
    }

    public StoreAddressDto(StoreAddress storeAddress) {
        this.id = storeAddress.getId();
        this.store = storeAddress.getStore().getName();
        this.address = storeAddress.getAddress();
        this.city = storeAddress.getCity().getName();
        this.description = storeAddress.getDescription();
        this.created = new StringUtil().dateFormatFromLong(storeAddress.getCreated());
        this.updated = new StringUtil().dateFormatFromLong(storeAddress.getUpdated());
        this.status = storeAddress.getStatus();
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
