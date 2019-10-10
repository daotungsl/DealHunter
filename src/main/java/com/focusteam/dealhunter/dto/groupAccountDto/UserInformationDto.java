package com.focusteam.dealhunter.dto.groupAccountDto;

import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.util.StringUtil;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserInformationDto {
    private String email;

    @Size(min = 5, max = 50)
    private String fullName;

    private int gender;

    @NotBlank
    @Size(min = 10, max = 50)
    private String birthday;

    private String phone;

    @NotBlank
    @Size(min = 20)
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
