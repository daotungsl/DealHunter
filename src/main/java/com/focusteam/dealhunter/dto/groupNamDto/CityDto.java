package com.focusteam.dealhunter.dto.groupNamDto;

import com.focusteam.dealhunter.entity.City;

public class CityDto {
    private long id;
    private String name;
    private String description;

    public CityDto(City city) {
        this.id = city.getId();
        this.name = city.getName();
        this.description = city.getDescription();
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
}
