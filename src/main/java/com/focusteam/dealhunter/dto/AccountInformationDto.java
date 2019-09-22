package com.focusteam.dealhunter.dto;

import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.util.StringUtil;

public class AccountInformationDto {
    private String username;
    private String password;
    private int typeAccount;
    private int status;
    private String email;
    private String fullName;
    private int gender;
    private String birthday;
    private String phone;
    private String avatar;
    private String address;
    private String created;
    private String updated;

    public AccountInformationDto() {
    }

    public AccountInformationDto(Account account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.typeAccount = account.getTypeAccount();
        this.status = account.getStatus();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(int typeAccount) {
        this.typeAccount = typeAccount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
