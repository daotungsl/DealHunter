package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    @Null
    private String description;
    @NotNull
    private String image;
    @NotNull
    private String code_sale;
    @NotNull
    private int percent;
    @NotNull
    private int max_slot;
    @NotNull
    private long created;
    @NotNull
    private long expired;
    @Null
    private long updated;
    @NotNull
    private int status;

    @OneToOne(mappedBy = "voucher")
    private Promotion_Time promotion_time;

    @ManyToOne
    @JoinColumn(name = "type_voucher_id", nullable = false)
    private Type_Voucher type_voucher;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode_sale() {
        return code_sale;
    }

    public void setCode_sale(String code_sale) {
        this.code_sale = code_sale;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getMax_slot() {
        return max_slot;
    }

    public void setMax_slot(int max_slot) {
        this.max_slot = max_slot;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
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

    public Type_Voucher getType_voucher() {
        return type_voucher;
    }

    public void setType_voucher(Type_Voucher type_voucher) {
        this.type_voucher = type_voucher;
    }

    public Promotion_Time getPromotion_time() {
        return promotion_time;
    }

    public void setPromotion_time(Promotion_Time promotion_time) {
        this.promotion_time = promotion_time;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    public static final class VoucherBuilder {
        private long id;
        private String name;
        private String description;
        private String image;
        private String code_sale;
        private int percent;
        private int max_slot;
        private long created;
        private long expired;
        private long updated;
        private int status;
        private Promotion_Time promotion_time;
        private Type_Voucher type_voucher;
        private Store store;

        private VoucherBuilder() {
        }

        public static VoucherBuilder aVoucher() {
            return new VoucherBuilder();
        }

        public VoucherBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public VoucherBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public VoucherBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public VoucherBuilder withImage(String image) {
            this.image = image;
            return this;
        }

        public VoucherBuilder withCode_sale(String code_sale) {
            this.code_sale = code_sale;
            return this;
        }

        public VoucherBuilder withPercent(int percent) {
            this.percent = percent;
            return this;
        }

        public VoucherBuilder withMax_slot(int max_slot) {
            this.max_slot = max_slot;
            return this;
        }

        public VoucherBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public VoucherBuilder withExpired(long expired) {
            this.expired = expired;
            return this;
        }

        public VoucherBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public VoucherBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public VoucherBuilder withPromotion_time(Promotion_Time promotion_time) {
            this.promotion_time = promotion_time;
            return this;
        }

        public VoucherBuilder withType_voucher(Type_Voucher type_voucher) {
            this.type_voucher = type_voucher;
            return this;
        }

        public VoucherBuilder withStore(Store store) {
            this.store = store;
            return this;
        }

        public Voucher build() {
            Voucher voucher = new Voucher();
            voucher.setId(id);
            voucher.setName(name);
            voucher.setDescription(description);
            voucher.setImage(image);
            voucher.setCode_sale(code_sale);
            voucher.setPercent(percent);
            voucher.setMax_slot(max_slot);
            voucher.setCreated(created);
            voucher.setExpired(expired);
            voucher.setUpdated(updated);
            voucher.setStatus(status);
            voucher.setPromotion_time(promotion_time);
            voucher.setType_voucher(type_voucher);
            voucher.setStore(store);
            return voucher;
        }
    }
}
