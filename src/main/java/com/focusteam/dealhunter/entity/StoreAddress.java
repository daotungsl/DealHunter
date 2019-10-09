package com.focusteam.dealhunter.entity;

import com.focusteam.dealhunter.dto.groupStoreDto.StoreCreateDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class StoreAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String address;
    @Size(min = 15, max = 200000)
    private String description;
    @NotNull
    private long created;
    private long updated;
    @NotNull
    private int status;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "storeAddress")
    private Set<Transaction> transactions;

    public StoreAddress() {
    }

    public StoreAddress(StoreCreateDto storeCreateDto) {
        this.address = storeCreateDto.getAddress();
        this.description = storeCreateDto.getDescription();
        this.created = System.currentTimeMillis();
        this.updated = System.currentTimeMillis();
        this.status = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }


    public static final class StoreAddressBuilder {
        private long id;
        private String address;
        private String description;
        private long created;
        private long updated;
        private int status;
        private Store store;
        private City city;
        private Set<Transaction> transactions;

        private StoreAddressBuilder() {
        }

        public static StoreAddressBuilder aStoreAddress() {
            return new StoreAddressBuilder();
        }

        public StoreAddressBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public StoreAddressBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public StoreAddressBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public StoreAddressBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public StoreAddressBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public StoreAddressBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public StoreAddressBuilder withStore(Store store) {
            this.store = store;
            return this;
        }

        public StoreAddressBuilder withCity(City city) {
            this.city = city;
            return this;
        }

        public StoreAddressBuilder withTransactions(Set<Transaction> transactions) {
            this.transactions = transactions;
            return this;
        }

        public StoreAddress build() {
            StoreAddress storeAddress = new StoreAddress();
            storeAddress.setId(id);
            storeAddress.setAddress(address);
            storeAddress.setDescription(description);
            storeAddress.setCreated(created);
            storeAddress.setUpdated(updated);
            storeAddress.setStatus(status);
            storeAddress.setStore(store);
            storeAddress.setCity(city);
            storeAddress.setTransactions(transactions);
            return storeAddress;
        }
    }
}
