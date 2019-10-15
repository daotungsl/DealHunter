package com.focusteam.dealhunter.dto.AdminDto;

import java.util.List;

public class SetAllStatus {
    private String name;

    private List<SetStatus> setStatuses;

    public SetAllStatus(List<SetStatus> list) {
        this.setStatuses = list;
    }

    public SetAllStatus(String name, List<SetStatus> setStatuses) {
        this.name = name;
        this.setStatuses = setStatuses;
    }

    public SetAllStatus() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SetStatus> getSetStatuses() {
        return setStatuses;
    }

    public void setSetStatuses(List<SetStatus> setStatuses) {
        this.setStatuses = setStatuses;
    }
}
