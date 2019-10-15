package com.focusteam.dealhunter.dto.AdminDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SetStatus {
    private long id;
    @NotNull
    @Max(2)
    @Min(0)
    private int status;

    public SetStatus() {
    }

    public SetStatus(long id, int status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
