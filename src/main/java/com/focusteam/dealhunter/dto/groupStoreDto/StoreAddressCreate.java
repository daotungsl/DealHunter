package com.focusteam.dealhunter.dto.groupStoreDto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StoreAddressCreate {
    @NotEmpty
    @Size(min = 50, max = 255)
    private String address;


    @Size(min = 50, max = 2000)
    private String description;

    @NotNull
    private long cityId;

    @NotNull
    private long storeId;



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

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }
}
