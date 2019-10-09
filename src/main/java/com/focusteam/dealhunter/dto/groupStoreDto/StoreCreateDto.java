package com.focusteam.dealhunter.dto.groupStoreDto;

import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class StoreCreateDto {
    @NotEmpty
    @Size(min = 10, max = 50)
    private String name;

    @Email
    @NotEmpty
    @Size(min = 15, max = 100)
    private String email;

    @NotEmpty
    @NotBlank
    @Size(min = 10, max = 20)
    private String phone;

    @NotBlank
    @NotEmpty
    @Size(min = 50, max = 20000)
    private String images;
    private long accountId;
    private long typeStoreId;

    @NotEmpty
    @Size(min = 50, max = 255)
    private String address;

    @NotEmpty
    @Size(min = 20, max = 2000)
    private String description;
    private long cityId;

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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    @Override
    public String toString() {
        return "StoreCreateDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", image='" + images + '\'' +
                ", accountId=" + accountId +
                ", typeStoreId=" + typeStoreId +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
