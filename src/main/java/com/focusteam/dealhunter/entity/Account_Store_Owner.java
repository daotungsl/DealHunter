package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Account_Store_Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private int status;

    @OneToOne(mappedBy = "account_store_owner")
    private Store_Owner_Information shop_owner_information;

    @OneToMany(mappedBy = "account_store_owner")
    private Set<Store_Owner_Credential> credentials;

    @OneToOne(mappedBy = "account_store_owner")
    private Store store;

    public Account_Store_Owner() {
    }

    public Account_Store_Owner(String username, String password, int status, Store_Owner_Information shop_owner_information) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.shop_owner_information = shop_owner_information;
    }

    public Account_Store_Owner(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Store_Owner_Information getShop_owner_information() {
        return shop_owner_information;
    }

    public void setShop_owner_information(Store_Owner_Information shop_owner_information) {
        this.shop_owner_information = shop_owner_information;
    }

    public Set<Store_Owner_Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(Set<Store_Owner_Credential> credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "Account_Shop_Owner{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", shop_owner_information=" + shop_owner_information +
                '}';
    }
}
