package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Entity
public class Type_Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    @Null
    private String description;
    @NotNull
    private long created;
    @Null
    private long updated;
    @NotNull
    private int status;

    @OneToMany(mappedBy = "type_voucher")
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

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }


    public static final class Type_VoucherBuilder {
        private long id;
        private String name;
        private String description;
        private long created;
        private long updated;
        private int status;
        private Set<Voucher> vouchers;

        private Type_VoucherBuilder() {
        }

        public static Type_VoucherBuilder aType_Voucher() {
            return new Type_VoucherBuilder();
        }

        public Type_VoucherBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public Type_VoucherBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Type_VoucherBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Type_VoucherBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public Type_VoucherBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public Type_VoucherBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public Type_VoucherBuilder withVouchers(Set<Voucher> vouchers) {
            this.vouchers = vouchers;
            return this;
        }

        public Type_Voucher build() {
            Type_Voucher type_Voucher = new Type_Voucher();
            type_Voucher.setId(id);
            type_Voucher.setName(name);
            type_Voucher.setDescription(description);
            type_Voucher.setCreated(created);
            type_Voucher.setUpdated(updated);
            type_Voucher.setStatus(status);
            type_Voucher.setVouchers(vouchers);
            return type_Voucher;
        }
    }
}
