package com.focusteam.dealhunter.dto.groupCityDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CityCreateDto {
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;
    @Size(min = 10, max = 2000)
    private String description;

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
}
