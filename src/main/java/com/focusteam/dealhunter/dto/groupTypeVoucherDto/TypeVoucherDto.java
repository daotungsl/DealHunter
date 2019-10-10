package com.focusteam.dealhunter.dto.groupTypeVoucherDto;

import com.focusteam.dealhunter.entity.TypeVoucher;
import com.focusteam.dealhunter.util.StringUtil;

import javax.validation.constraints.*;

public class TypeVoucherDto {
    private long id;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    private String nameUnAccent;

    @Size(min = 10, max = 2000)
    private String description;

    private String created;
    private String updated;

    @NotBlank
    @Digits(integer = 1, fraction = 0)
    private int status;

    public TypeVoucherDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TypeVoucherDto() {
    }

    public TypeVoucherDto(long id, String name, String description, int status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public TypeVoucherDto(TypeVoucher typeVoucher) {
        this.id = typeVoucher.getId();
        this.name = typeVoucher.getName();
        this.nameUnAccent = typeVoucher.getNameUnAccent();
        this.description = typeVoucher.getDescription();
        this.created = new StringUtil().dateFormatFromLong(typeVoucher.getUpdated());
        this.updated = new StringUtil().dateFormatFromLong(typeVoucher.getUpdated());
        this.status = typeVoucher.getStatus();
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
