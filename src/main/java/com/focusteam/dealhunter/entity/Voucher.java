package com.focusteam.dealhunter.entity;

import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherCreateDto;
import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherUpdate;
import com.focusteam.dealhunter.util.StringUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Set;

@Entity
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    @Size(min = 15, max = 200000)
    private String description;

    private String nameUnAccent;

    @NotNull
    @Size(min = 50, max = 200000)
    private String image;

    private String codeSale;
    @NotNull
    private int percent;
    @NotNull
    private int maxSlot;

    private int slotLeft;

    @NotNull
    private long created;
    @NotNull
    private long startDay;
    @NotNull
    private long expiredDay;

    private long updated;
    @NotNull
    private int status;

    @OneToOne(mappedBy = "voucher", cascade = CascadeType.ALL)
    private PromotionTime promotionTime;

    @ManyToOne
    @JoinColumn(name = "typeVoucher_id", nullable = false)
    private TypeVoucher typeVoucher;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "voucher")
    private Set<Transaction> transactions;

    public Voucher() {
    }

    public Voucher(VoucherCreateDto voucherCreateDto) {
        this.name = voucherCreateDto.getName();
        this.description = voucherCreateDto.getDescription();
        this.image = voucherCreateDto.getImage();
        this.codeSale = new StringUtil().codeSale(voucherCreateDto.getPercent());
        this.nameUnAccent = new StringUtil().unAccent(voucherCreateDto.getName() + " " + this.codeSale);
        this.percent = voucherCreateDto.getPercent();
        this.maxSlot = voucherCreateDto.getMaxSlot();
        this.slotLeft = voucherCreateDto.getMaxSlot();
        this.startDay = voucherCreateDto.getStartDay();
        this.created = Calendar.getInstance().getTimeInMillis();
        this.expiredDay = voucherCreateDto.getExpiredDay();
        this.updated = this.created;
        this.status = 0;

        PromotionTime promotionTime = new PromotionTime();
        promotionTime.setStartTime(voucherCreateDto.getStartTime());
        promotionTime.setEndTime(voucherCreateDto.getEndTime());
        promotionTime.setDayWeek(voucherCreateDto.getDayWeek());

        promotionTime.setVoucher(this);
        this.promotionTime = promotionTime;
    }

    public Voucher(VoucherUpdate voucherUpdate) {
        this.name = voucherUpdate.getName();
        this.description = voucherUpdate.getDescription();
        this.image = voucherUpdate.getImage();
        this.codeSale = new StringUtil().codeSale(voucherUpdate.getPercent());
        this.nameUnAccent = new StringUtil().unAccent(voucherUpdate.getName() + " " + this.codeSale);
        this.percent = voucherUpdate.getPercent();
        this.maxSlot = voucherUpdate.getMaxSlot();
        this.slotLeft = voucherUpdate.getMaxSlot();
        this.startDay = voucherUpdate.getStartDay();
        this.created = Calendar.getInstance().getTimeInMillis();
        this.expiredDay = voucherUpdate.getExpiredDay();
        this.updated = this.created;
        this.status = 0;

        PromotionTime promotionTime = new PromotionTime();
        promotionTime.setStartTime(voucherUpdate.getStartTime());
        promotionTime.setEndTime(voucherUpdate.getEndTime());
        promotionTime.setDayWeek(voucherUpdate.getDayWeek());

        promotionTime.setVoucher(this);
        this.promotionTime = promotionTime;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

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

    public int getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(int maxSlot) {
        this.maxSlot = maxSlot;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getStartDay() {
        return startDay;
    }

    public void setStartDay(long startDay) {
        this.startDay = startDay;
    }

    public long getExpiredDay() {
        return expiredDay;
    }

    public void setExpiredDay(long expiredDay) {
        this.expiredDay = expiredDay;
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

    public int getSlotLeft() {
        return slotLeft;
    }

    public void setSlotLeft(int slotLeft) {
        this.slotLeft = slotLeft;
    }


    public static final class VoucherBuilder {
        private long id;
        private String name;
        private String description;
        private String image;
        private String codeSale;
        private int percent;
        private int maxSlot;
        private int slotLeft;
        private long created;
        private long startDay;
        private long expiredDay;
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

        public VoucherBuilder withMaxSlot(int maxSlot) {
            this.maxSlot = maxSlot;
            return this;
        }

        public VoucherBuilder withSlotLeft(int slotLeft) {
            this.slotLeft = slotLeft;
            return this;
        }

        public VoucherBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public VoucherBuilder withStartDay(long startDay) {
            this.startDay = startDay;
            return this;
        }

        public VoucherBuilder withExpiredDay(long expiredDay) {
            this.expiredDay = expiredDay;
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
            voucher.setMaxSlot(maxSlot);
            voucher.setSlotLeft(slotLeft);
            voucher.setCreated(created);
            voucher.setStartDay(startDay);
            voucher.setExpiredDay(expiredDay);
            voucher.setUpdated(updated);
            voucher.setStatus(status);
            voucher.setPromotionTime(promotionTime);
            voucher.setTypeVoucher(typeVoucher);
            voucher.setStore(store);
            return voucher;
        }
    }
}
