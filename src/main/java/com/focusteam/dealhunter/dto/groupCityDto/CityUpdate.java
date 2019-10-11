package com.focusteam.dealhunter.dto.groupCityDto;

import javax.validation.constraints.*;

public class CityUpdate {
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 10, max = 2000)
    private String description;

    @NotNull
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
