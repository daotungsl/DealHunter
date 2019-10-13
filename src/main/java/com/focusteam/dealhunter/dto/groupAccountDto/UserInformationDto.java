package com.focusteam.dealhunter.dto.groupAccountDto;

import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.util.StringUtil;

import javax.validation.constraints.*;

public class UserInformationDto {

    @Email
    private String email;

    @Size(min = 5, max = 50)
    private String fullName;

    @Digits(integer = 1, fraction = 0)
    @Min(0)
    @Max(2)
    private int gender;

    @NotBlank
    @Size(min = 10, max = 50)
    private String birthday;

    @Digits(integer = 10, fraction = 0)
    private String phone;

    @NotBlank
    @Size(min = 50, max = 20000)
    private String avatar;

    @Size(min = 10, max = 100)
    private String address;

    private String created;
    private String updated;

    public UserInformationDto(Account account) {
        this.email = account.getUserInformation().getEmail();
        this.fullName = account.getUserInformation().getFullName();
        this.gender = account.getUserInformation().getGender();
        this.birthday = account.getUserInformation().getBirthday();
        this.phone = account.getUserInformation().getPhone();
        this.avatar = account.getUserInformation().getAvatar();
        this.address = account.getUserInformation().getAddress();
        this.created = new StringUtil().dateFormatFromLong(account.getUserInformation().getCreated());
        this.updated = new StringUtil().dateFormatFromLong(account.getUserInformation().getUpdated());
    }

    public UserInformationDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
