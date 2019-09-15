package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class Store_Owner_Information {
    @Id
    private String email;
    @NotNull
    private String fullName;
    @NotNull
    private int gender;
    @NotNull
    private String birthday;
    @NotNull
    private String phone;
    @NotNull
    private String avatar;
    @NotNull
    private String address;
    @NotNull
    private long created;
    @Null
    private long updated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_owner_id", referencedColumnName = "id", nullable = false)
    private Account_Store_Owner account_store_owner;

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

    public Account_Store_Owner getAccount_store_owner() {
        return account_store_owner;
    }

    public void setAccount_store_owner(Account_Store_Owner account_store_owner) {
        this.account_store_owner = account_store_owner;
    }
}
