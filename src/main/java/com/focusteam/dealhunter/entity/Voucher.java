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
    private String description;
    @NotNull
    private String image;
    private String codeSale;
    @NotNull
    private int percent;
    @NotNull
    private int max_slot;
    @NotNull
    private long created;
    @NotNull
    private long expired;
    private long updated;
    @NotNull
    private int status;

    @OneToOne(mappedBy = "voucher")
    private PromotionTime promotionTime;

    @ManyToOne
    @JoinColumn(name = "typeVoucher_id", nullable = false)
    private TypeVoucher typeVoucher;

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

    public String getCodeSale() {
        return codeSale;
    }

    public void setCodeSale(String codeSale) {
        this.codeSale = codeSale;
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

    public PromotionTime getPromotionTime() {
        return promotionTime;
    }

    public void setPromotionTime(PromotionTime promotionTime) {
        this.promotionTime = promotionTime;
    }

    public TypeVoucher getTypeVoucher() {
        return typeVoucher;
    }

    public void setTypeVoucher(TypeVoucher typeVoucher) {
        this.typeVoucher = typeVoucher;
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
        private String codeSale;
        private int percent;
        private int max_slot;
        private long created;
        private long expired;
        private long updated;
        private int status;
        private PromotionTime promotionTime;
        private TypeVoucher typeVoucher;
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

        public VoucherBuilder withCodeSale(String codeSale) {
            this.codeSale = codeSale;
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

        public VoucherBuilder withPromotionTime(PromotionTime promotionTime) {
            this.promotionTime = promotionTime;
            return this;
        }

        public VoucherBuilder withTypeVoucher(TypeVoucher typeVoucher) {
            this.typeVoucher = typeVoucher;
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
            voucher.setCodeSale(codeSale);
            voucher.setPercent(percent);
            voucher.setMax_slot(max_slot);
            voucher.setCreated(created);
            voucher.setExpired(expired);
            voucher.setUpdated(updated);
            voucher.setStatus(status);
            voucher.setPromotionTime(promotionTime);
            voucher.setTypeVoucher(typeVoucher);
            voucher.setStore(store);
            return voucher;
        }
    }
}
