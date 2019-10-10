package com.focusteam.dealhunter.entity;

import com.focusteam.dealhunter.dto.groupTypeVoucherDto.TypeVoucherCreateDto;
import com.focusteam.dealhunter.util.StringUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class TypeVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;

    private String nameUnAccent;

    @Size(min = 10, max = 2000)
    private String description;
    @NotNull
    private long created;
    private long updated;
    @NotNull
    private int status;

    public TypeVoucher() {
    }

    public TypeVoucher(TypeVoucherCreateDto typeVoucherCreateDto) {
        this.name = typeVoucherCreateDto.getName();
        this.nameUnAccent = new StringUtil().unAccent(typeVoucherCreateDto.getName());
        this.description = typeVoucherCreateDto.getDescription();
        this.created = System.currentTimeMillis();
        this.updated = System.currentTimeMillis();
        this.status = 1;
    }

    @OneToMany(mappedBy = "typeVoucher", cascade = CascadeType.ALL)
    private Set<Voucher> vouchers;

    public String getNameUnAccent() {
        return nameUnAccent;
    }

    public void setNameUnAccent(String nameUnAccent) {
        this.nameUnAccent = nameUnAccent;
    }

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


    public static final class TypeVoucherBuilder {
        private long id;
        private String name;
        private String description;
        private long created;
        private long updated;
        private int status;
        private Set<Voucher> vouchers;

        private TypeVoucherBuilder() {
        }

        public static TypeVoucherBuilder aTypeVoucher() {
            return new TypeVoucherBuilder();
        }

        public TypeVoucherBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public TypeVoucherBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TypeVoucherBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TypeVoucherBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public TypeVoucherBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public TypeVoucherBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public TypeVoucherBuilder withVouchers(Set<Voucher> vouchers) {
            this.vouchers = vouchers;
            return this;
        }

        public TypeVoucher build() {
            TypeVoucher typeVoucher = new TypeVoucher();
            typeVoucher.setId(id);
            typeVoucher.setName(name);
            typeVoucher.setDescription(description);
            typeVoucher.setCreated(created);
            typeVoucher.setUpdated(updated);
            typeVoucher.setStatus(status);
            typeVoucher.setVouchers(vouchers);
            return typeVoucher;
        }
    }
}
