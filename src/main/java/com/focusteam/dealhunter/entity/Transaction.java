package com.focusteam.dealhunter.entity;

import com.focusteam.dealhunter.dto.groupTransactionDto.TransactionCreateDto;
import com.focusteam.dealhunter.util.StringUtil;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String time;
    @NotNull
    private String day;

    private long dayLong;

    @NotNull
    private int adults;

    private int children;

    @Size(min = 0, max = 20000)
    private String description;
    @NotNull
    private long created;
    private long updated;
    @NotNull
    private int status;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "storeAddress_id", nullable = false)
    private StoreAddress storeAddress;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;

    public Transaction() {
    }

    public Transaction(TransactionCreateDto transactionCreateDto) {
        this.adults = transactionCreateDto.getAdults();
        this.children = transactionCreateDto.getChildren();
        this.time = transactionCreateDto.getTime();
        this.day = transactionCreateDto.getDay();
        this.dayLong = new StringUtil().stringToLong(this.day);
        this.description = transactionCreateDto.getDescription();
        this.created = Calendar.getInstance().getTimeInMillis();
        this.updated = this.created;
        this.status = 0;
    }

    public long getDayLong() {
        return dayLong;
    }

    public void setDayLong(long dayLong) {
        this.dayLong = dayLong;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public StoreAddress getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    public static final class TransactionBuilder {
        private long id;
        private String time;
        private String day;
        private String description;
        private long created;
        private long updated;
        private int status;
        private Account account;
        private StoreAddress storeAddress;
        private Store store;

        private TransactionBuilder() {
        }

        public static TransactionBuilder aTransaction() {
            return new TransactionBuilder();
        }

        public TransactionBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder withTime(String time) {
            this.time = time;
            return this;
        }

        public TransactionBuilder withDay(String day) {
            this.day = day;
            return this;
        }

        public TransactionBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TransactionBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public TransactionBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public TransactionBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public TransactionBuilder withAccount(Account account) {
            this.account = account;
            return this;
        }

        public TransactionBuilder withStoreAddress(StoreAddress storeAddress) {
            this.storeAddress = storeAddress;
            return this;
        }

        public TransactionBuilder withStore(Store store) {
            this.store = store;
            return this;
        }

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.setId(id);
            transaction.setTime(time);
            transaction.setDay(day);
            transaction.setDescription(description);
            transaction.setCreated(created);
            transaction.setUpdated(updated);
            transaction.setStatus(status);
            transaction.setAccount(account);
            transaction.setStoreAddress(storeAddress);
            transaction.setStore(store);
            return transaction;
        }
    }
}
