package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Entity
public class Store_Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String address;
    @Null
    private String description;
    @NotNull
    private long created;
    @Null
    private long updated;
    @NotNull
    private int status;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "store_address")
    private Set<History_Order> history_orders;

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

    public Set<History_Order> getHistory_orders() {
        return history_orders;
    }

    public void setHistory_orders(Set<History_Order> history_orders) {
        this.history_orders = history_orders;
    }


    public static final class Store_AddressBuilder {
        private long id;
        private String address;
        private String description;
        private long created;
        private long updated;
        private int status;
        private Store store;
        private City city;
        private Set<History_Order> history_orders;

        private Store_AddressBuilder() {
        }

        public static Store_AddressBuilder aStore_Address() {
            return new Store_AddressBuilder();
        }

        public Store_AddressBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public Store_AddressBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Store_AddressBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Store_AddressBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public Store_AddressBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public Store_AddressBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public Store_AddressBuilder withStore(Store store) {
            this.store = store;
            return this;
        }

        public Store_AddressBuilder withCity(City city) {
            this.city = city;
            return this;
        }

        public Store_AddressBuilder withHistory_orders(Set<History_Order> history_orders) {
            this.history_orders = history_orders;
            return this;
        }

        public Store_Address build() {
            Store_Address store_Address = new Store_Address();
            store_Address.setId(id);
            store_Address.setAddress(address);
            store_Address.setDescription(description);
            store_Address.setCreated(created);
            store_Address.setUpdated(updated);
            store_Address.setStatus(status);
            store_Address.setStore(store);
            store_Address.setCity(city);
            store_Address.setHistory_orders(history_orders);
            return store_Address;
        }
    }
}
