package com.focusteam.dealhunter.dto.groupAccountDto;

import com.focusteam.dealhunter.entity.Account;
import com.focusteam.dealhunter.util.StringUtil;

public class AccountInformationDto {
    private long id;
    private String username;
    private String password;
    private int typeAccount;
    private long storeId;

    private String email;
    private String fullName;
    private int gender;
    private String birthday;
    //private String salt;
    private String phone;
    private String avatar;
    private String address;

    private String created;
    private String updated;
    private int status;

    public AccountInformationDto() {
    }

    public AccountInformationDto(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.typeAccount = account.getTypeAccount();
        if (account.getStore() == null) {
            this.storeId = -1;
        }else {
            this.storeId = account.getStore().getId();
        }

        this.email = account.getUserInformation().getEmail();
        this.fullName = account.getUserInformation().getFullName();
        this.gender = account.getUserInformation().getGender();
        this.birthday = account.getUserInformation().getBirthday();
        this.phone = account.getUserInformation().getPhone();
        this.avatar = account.getUserInformation().getAvatar();
        this.address = account.getUserInformation().getAddress();

        this.created = new StringUtil().dateFormatFromLong(account.getUserInformation().getCreated());
        this.updated = new StringUtil().dateFormatFromLong(account.getUserInformation().getUpdated());
        this.status = account.getStatus();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
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

//    public String getSalt() {
//        return salt;
//    }
//
//    public void setSalt(String salt) {
//        this.salt = salt;
//    }
}
