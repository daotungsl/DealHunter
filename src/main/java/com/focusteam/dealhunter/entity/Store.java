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


    public static final class StoreBuilder {
        private long id;
        private String name;
        private String email;
        private String phone;
        private String image;
        private long created;
        private long updated;
        private int status;
        private Account_Store_Owner account_store_owner;
        private Type_Store type_store;
        private Set<Store_Address> store_addresses;
        private Set<History_Order> history_orders;
        private Set<Voucher> vouchers;

        private StoreBuilder() {
        }

        public static StoreBuilder aStore() {
            return new StoreBuilder();
        }

        public StoreBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public StoreBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public StoreBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public StoreBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public StoreBuilder withImage(String image) {
            this.image = image;
            return this;
        }

        public StoreBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public StoreBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public StoreBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public StoreBuilder withAccount_store_owner(Account_Store_Owner account_store_owner) {
            this.account_store_owner = account_store_owner;
            return this;
        }

        public StoreBuilder withType_store(Type_Store type_store) {
            this.type_store = type_store;
            return this;
        }

        public StoreBuilder withStore_addresses(Set<Store_Address> store_addresses) {
            this.store_addresses = store_addresses;
            return this;
        }

        public StoreBuilder withHistory_orders(Set<History_Order> history_orders) {
            this.history_orders = history_orders;
            return this;
        }

        public StoreBuilder withVouchers(Set<Voucher> vouchers) {
            this.vouchers = vouchers;
            return this;
        }

        public Store build() {
            Store store = new Store();
            store.setId(id);
            store.setName(name);
            store.setEmail(email);
            store.setPhone(phone);
            store.setImage(image);
            store.setCreated(created);
            store.setUpdated(updated);
            store.setStatus(status);
            store.setAccount_store_owner(account_store_owner);
            store.setType_store(type_store);
            store.setStore_addresses(store_addresses);
            store.setHistory_orders(history_orders);
            store.setVouchers(vouchers);
            return store;
        }
    }
}
