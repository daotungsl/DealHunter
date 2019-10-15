package com.focusteam.dealhunter.dto.groupAccountDto;

import javax.validation.constraints.*;

public class AccountStoreDto {
    @Size(min = 10, max = 15)
    @Digits(integer = 10, fraction = 15)
    private String phone;

    @Email
    @NotEmpty
    @Size(min = 15, max = 50)
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 200)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 200)
    private String repassword;


    @Size(min = 5, max = 50)
    private String fullName;

    @NotNull
    @Digits(integer = 1, fraction = 0)
    @Min(0)
    @Max(2)
    private int gender;

    @NotBlank
    @NotEmpty
    @NotNull
    @Size(min = 10, max = 50)
    private String birthday;

    @NotBlank
    @NotNull

    @Size(min = 50, max = 20000)
    private String avatar;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 100)
    private String address;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
