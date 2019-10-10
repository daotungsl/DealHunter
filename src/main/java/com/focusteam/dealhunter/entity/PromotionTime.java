package com.focusteam.dealhunter.entity;

import com.focusteam.dealhunter.dto.groupVoucherDto.VoucherCreateDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class PromotionTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String startTime;
    @NotNull
    private String endTime;
    @NotNull
    private String dayWeek;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_id", referencedColumnName = "id", nullable = false)
    private Voucher voucher;

    public PromotionTime() {
    }

    public PromotionTime(VoucherCreateDto voucherCreateDto) {
        this.startTime = voucherCreateDto.getStartTime();
        this.endTime = voucherCreateDto.getEndTime();
        this.dayWeek = voucherCreateDto.getDayWeek();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }


    public static final class PromotionTimeBuilder {
        private long id;
        private String startTime;
        private String endTime;
        private String dayWeek;
        private Voucher voucher;

        private PromotionTimeBuilder() {
        }

        public static PromotionTimeBuilder aPromotionTime() {
            return new PromotionTimeBuilder();
        }

        public PromotionTimeBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public PromotionTimeBuilder withStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public PromotionTimeBuilder withEndTime(String endTime) {
            this.endTime = endTime;
            return this;
        }

        public PromotionTimeBuilder withDayWeek(String dayWeek) {
            this.dayWeek = dayWeek;
            return this;
        }

        public PromotionTimeBuilder withVoucher(Voucher voucher) {
            this.voucher = voucher;
            return this;
        }

        public PromotionTime build() {
            PromotionTime promotionTime = new PromotionTime();
            promotionTime.setId(id);
            promotionTime.setStartTime(startTime);
            promotionTime.setEndTime(endTime);
            promotionTime.setDayWeek(dayWeek);
            promotionTime.setVoucher(voucher);
            return promotionTime;
        }
    }
}
