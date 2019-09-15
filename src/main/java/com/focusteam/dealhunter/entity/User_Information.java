package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class User_Information {
    @Id
    private String email;
    @Null
    private String fullName;
    @Null
    private int gender;
    @Null
    private String birthday;
    @NotNull
    private String phone;
    @Null
    private String avatar;
    @Null
    private String address;
    @NotNull
    private long created;
    @Null
    private long updated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_user_id", referencedColumnName = "id", nullable = false)
    private Account_User account_user;

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

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public Account_User getAccount_user() {
        return account_user;
    }

    public void setAccount_user(Account_User account_user) {
        this.account_user = account_user;
    }
}
