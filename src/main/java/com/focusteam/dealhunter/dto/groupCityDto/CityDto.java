package com.focusteam.dealhunter.dto.groupCityDto;

import com.focusteam.dealhunter.entity.City;
import com.focusteam.dealhunter.entity.TypeStore;
import com.focusteam.dealhunter.util.StringUtil;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CityDto {
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

    public CityDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CityDto() {
    }

    public CityDto(long id, String name, String description, int status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public CityDto(City city) {
        this.id = city.getId();
        this.name = city.getName();
        this.nameUnAccent = city.getNameUnAccent();
        this.description = city.getDescription();
        this.created = new StringUtil().dateFormatFromLong(city.getUpdated());
        this.updated = new StringUtil().dateFormatFromLong(city.getUpdated());
        this.status = city.getStatus();
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
