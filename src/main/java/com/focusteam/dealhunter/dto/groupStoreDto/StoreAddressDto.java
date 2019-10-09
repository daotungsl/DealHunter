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
    @NotNull
    private String address;
    @Size(min = 15, max = 200000)
    private String description;
    @NotNull
    private String created;
    private String updated;
    @NotNull
    private String status;

    public StoreAddressDto() {
    }

    public StoreAddressDto(StoreAddress storeAddress) {
        this.id = storeAddress.getId();
        this.address = storeAddress.getAddress();
        this.description = storeAddress.getDescription();
        this.created = new StringUtil().dateFormatFromLong(storeAddress.getCreated());
        this.updated = new StringUtil().dateFormatFromLong(storeAddress.getUpdated());
        if (storeAddress.getStatus() == 0){
            this.status = "not activated";
        }else {
            this.status = "activated";
        }
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
