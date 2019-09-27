package com.focusteam.dealhunter.dto.groupTypeVoucherDto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TypeVoucherUpdate {
    @NotEmpty
    @Size(min = 10, max = 15)
    private String name;

    @Size(min = 10, max = 2000)
    private String description;

    @NotBlank
    @Digits(integer = 1, fraction = 0)
    private int status;

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
}
