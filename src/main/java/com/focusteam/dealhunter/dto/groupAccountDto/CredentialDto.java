package com.focusteam.dealhunter.dto.groupAccountDto;

import com.focusteam.dealhunter.entity.Credential;
import com.focusteam.dealhunter.util.StringUtil;

public class CredentialDto {
    private String accessToken;
    private String clientType;
    private String created;
    private String expired;

    public CredentialDto() {
    }

    public CredentialDto(Credential credential) {
        this.accessToken = credential.getAccessToken();
        this.clientType = credential.getClientType();
        this.created = new StringUtil().dateFormatFromLong(credential.getCreated());
        this.expired = new StringUtil().dateFormatFromLong(credential.getExpired());
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}
