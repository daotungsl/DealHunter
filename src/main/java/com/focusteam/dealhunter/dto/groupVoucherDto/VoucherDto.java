package com.focusteam.dealhunter.dto.groupVoucherDto;

import com.focusteam.dealhunter.entity.Voucher;
import com.focusteam.dealhunter.util.StringUtil;

public class VoucherDto {
    private long id;

    private String name;

    private String store;

    private String typeVoucher;

    private String nameUnAccent;
    private String description;
    private String image;
    private String codeSale;
    private int percent;
    private int maxSlot;
    private int slotLeft;
    private String startDay;
    private String expiredDay;

    private PromotionTimeDto promotionTimeDto;

    //private String city;

    private String created;
    private String updated;
    private int status;


    public VoucherDto() {
    }

    public VoucherDto(Voucher voucher) {
        this.id = voucher.getId();
        this.name = voucher.getName();

        this.store = voucher.getStore().getName();

        this.typeVoucher = voucher.getTypeVoucher().getName();

        this.nameUnAccent = voucher.getNameUnAccent();
        this.description = voucher.getDescription();
        this.image = voucher.getImage();
        this.codeSale = voucher.getCodeSale();
        this.percent = voucher.getPercent();
        this.maxSlot = voucher.getMaxSlot();
        this.slotLeft = voucher.getSlotLeft();
        this.startDay = new StringUtil().dateFormatFromLong(voucher.getStartDay());
        this.expiredDay = new StringUtil().dateFormatFromLong(voucher.getExpiredDay());

        promotionTimeDto = new PromotionTimeDto(voucher);

        this.created = new StringUtil().dateFormatFromLong(voucher.getCreated());
        this.updated = new StringUtil().dateFormatFromLong(voucher.getUpdated());
        this.status = voucher.getStatus();
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getTypeVoucher() {
        return typeVoucher;
    }

    public void setTypeVoucher(String typeVoucher) {
        this.typeVoucher = typeVoucher;
    }

//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }

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

    public int getSlotLeft() {
        return slotLeft;
    }

    public void setSlotLeft(int slotLeft) {
        this.slotLeft = slotLeft;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getExpiredDay() {
        return expiredDay;
    }

    public void setExpiredDay(String expiredDay) {
        this.expiredDay = expiredDay;
    }

    public PromotionTimeDto getPromotionTimeDto() {
        return promotionTimeDto;
    }

    public void setPromotionTimeDto(PromotionTimeDto promotionTimeDto) {
        this.promotionTimeDto = promotionTimeDto;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
