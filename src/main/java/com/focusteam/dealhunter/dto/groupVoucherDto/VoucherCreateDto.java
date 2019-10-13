package com.focusteam.dealhunter.dto.groupVoucherDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VoucherCreateDto {
    @NotEmpty
    @Size(min = 10, max = 50)
    private String name;

    @Size(min = 15, max = 200000)
    private String description;

    @NotBlank
    @NotEmpty
    @Size(min = 10, max = 20000)
    private String image;

    @NotNull
    private int percent;
    @NotNull
    private int maxSlot;
    @NotNull
    private long startDay;
    @NotNull
    private long expiredDay;

    @NotNull
    @Size(min = 2, max = 10)
    private String startTime;

    @NotNull
    @Size(min = 2, max = 10)
    private String endTime;
    @NotNull
    @Size(min = 2, max = 50)
    private String dayWeek;
    @NotNull
    private long storeId;
    @NotNull
    private long typeVoucherId;

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

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getTypeVoucherId() {
        return typeVoucherId;
    }

    public void setTypeVoucherId(long typeVoucherId) {
        this.typeVoucherId = typeVoucherId;
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
}
