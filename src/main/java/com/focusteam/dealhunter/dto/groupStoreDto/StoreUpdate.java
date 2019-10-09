package com.focusteam.dealhunter.dto.groupStoreDto;

import javax.validation.constraints.*;

public class StoreUpdate {
    @NotNull
    private long id;

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

    @NotNull
    private long typeStoreId;

    @NotNull
    @Digits(integer = 1, fraction = 0)
    private int status;

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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public long getTypeStoreId() {
        return typeStoreId;
    }

    public void setTypeStoreId(long typeStoreId) {
        this.typeStoreId = typeStoreId;
    }
}
