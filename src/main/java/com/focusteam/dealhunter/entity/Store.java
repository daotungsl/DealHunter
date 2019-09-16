package com.focusteam.dealhunter.entity;

import javax.persistence.*;
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
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "typeStore_id", nullable = false)
    private TypeStore typeStore;

    @OneToMany(mappedBy = "store")
    private Set<StoreAddress> storeAddresses;

    @OneToMany(mappedBy = "store")
    private Set<Transaction> transactions;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TypeStore getTypeStore() {
        return typeStore;
    }

    public void setTypeStore(TypeStore typeStore) {
        this.typeStore = typeStore;
    }

    public Set<StoreAddress> getStoreAddresses() {
        return storeAddresses;
    }

    public void setStoreAddresses(Set<StoreAddress> storeAddresses) {
        this.storeAddresses = storeAddresses;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
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
        private Account account;
        private TypeStore typeStore;
        private Set<StoreAddress> storeAddresses;
        private Set<Transaction> transactions;
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

        public StoreBuilder withAccount(Account account) {
            this.account = account;
            return this;
        }

        public StoreBuilder withTypeStore(TypeStore typeStore) {
            this.typeStore = typeStore;
            return this;
        }

        public StoreBuilder withStoreAddresses(Set<StoreAddress> storeAddresses) {
            this.storeAddresses = storeAddresses;
            return this;
        }

        public StoreBuilder withTransactions(Set<Transaction> transactions) {
            this.transactions = transactions;
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
            store.setAccount(account);
            store.setTypeStore(typeStore);
            store.setStoreAddresses(storeAddresses);
            store.setTransactions(transactions);
            store.setVouchers(vouchers);
            return store;
        }
    }
}
