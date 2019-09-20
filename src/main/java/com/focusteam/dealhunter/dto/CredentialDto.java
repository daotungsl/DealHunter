package com.focusteam.dealhunter.dto;

import com.focusteam.dealhunter.entity.Credential;

import javax.validation.constraints.NotNull;

public class CredentialDto {
    private String accessToken;
    private String clientType;
    private long created;
    private long expired;

    public CredentialDto() {
    }

    public CredentialDto(Credential credential) {
        this.accessToken = credential.getAccessToken();
        this.clientType = credential.getClientType();
        this.created = credential.getCreated();
        this.expired = credential.getExpired();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }
}
