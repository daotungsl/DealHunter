package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class History_Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String time;
    @NotNull
    private String day;
    @Null
    private String description;
    @NotNull
    private long created;
    @Null
    private long updated;
    @NotNull
    private int status;

    @ManyToOne
    @JoinColumn(name = "account_user_id", nullable = false)
    private Account_User account_user;

    @ManyToOne
    @JoinColumn(name = "store_address_id", nullable = false)
    private Store_Address store_address;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public Account_User getAccount_user() {
        return account_user;
    }

    public void setAccount_user(Account_User account_user) {
        this.account_user = account_user;
    }

    public Store_Address getStore_address() {
        return store_address;
    }

    public void setStore_address(Store_Address store_address) {
        this.store_address = store_address;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    public static final class History_OrderBuilder {
        private long id;
        private String time;
        private String day;
        private String description;
        private long created;
        private long updated;
        private int status;
        private Account_User account_user;
        private Store_Address store_address;
        private Store store;

        private History_OrderBuilder() {
        }

        public static History_OrderBuilder aHistory_Order() {
            return new History_OrderBuilder();
        }

        public History_OrderBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public History_OrderBuilder withTime(String time) {
            this.time = time;
            return this;
        }

        public History_OrderBuilder withDay(String day) {
            this.day = day;
            return this;
        }

        public History_OrderBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public History_OrderBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public History_OrderBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public History_OrderBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public History_OrderBuilder withAccount_user(Account_User account_user) {
            this.account_user = account_user;
            return this;
        }

        public History_OrderBuilder withStore_address(Store_Address store_address) {
            this.store_address = store_address;
            return this;
        }

        public History_OrderBuilder withStore(Store store) {
            this.store = store;
            return this;
        }

        public History_Order build() {
            History_Order history_Order = new History_Order();
            history_Order.setId(id);
            history_Order.setTime(time);
            history_Order.setDay(day);
            history_Order.setDescription(description);
            history_Order.setCreated(created);
            history_Order.setUpdated(updated);
            history_Order.setStatus(status);
            history_Order.setAccount_user(account_user);
            history_Order.setStore_address(store_address);
            history_Order.setStore(store);
            return history_Order;
        }
    }
}
