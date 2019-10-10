package com.focusteam.dealhunter.dto.groupVoucherDto;

import com.focusteam.dealhunter.entity.Voucher;

public class PromotionTimeDto {
    private long id;
    private String startTime;
    private String endTime;
    private String dayWeek;

    public PromotionTimeDto(Voucher voucher) {
        this.id = voucher.getPromotionTime().getId();
        this.startTime = voucher.getPromotionTime().getStartTime();
        this.endTime = voucher.getPromotionTime().getEndTime();
        this.dayWeek = voucher.getPromotionTime().getDayWeek();
    }

    public PromotionTimeDto() {
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
}
