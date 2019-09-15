package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String image;
    @NotNull
    private long created;
    @Null
    private long updated;
    @NotNull
    private int status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_owner_id", referencedColumnName = "id", nullable = false)
    private Account_Store_Owner account_store_owner;

    @ManyToOne
    @JoinColumn(name = "type_store_id", nullable = false)
    private Type_Store type_store;

    @OneToMany(mappedBy = "store")
    private Set<Store_Address> store_addresses;

    @OneToMany(mappedBy = "store")
    private Set<History_Order> history_orders;

    @OneToMany(mappedBy = "store")
    private Set<Voucher> vouchers;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Account_Store_Owner getAccount_store_owner() {
        return account_store_owner;
    }

    public void setAccount_store_owner(Account_Store_Owner account_store_owner) {
        this.account_store_owner = account_store_owner;
    }

    public Type_Store getType_store() {
        return type_store;
    }

    public void setType_store(Type_Store type_store) {
        this.type_store = type_store;
    }

    public Set<Store_Address> getStore_addresses() {
        return store_addresses;
    }

    public void setStore_addresses(Set<Store_Address> store_addresses) {
        this.store_addresses = store_addresses;
    }

    public Set<History_Order> getHistory_orders() {
        return history_orders;
    }

    public void setHistory_orders(Set<History_Order> history_orders) {
        this.history_orders = history_orders;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }
}
