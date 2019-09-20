package com.focusteam.dealhunter.rest;

import com.focusteam.dealhunter.dto.AccountInformationDto;
import com.focusteam.dealhunter.dto.CredentialDto;
import com.focusteam.dealhunter.entity.Account;

public class RESTLogin {
    private AccountInformationDto account;
    private CredentialDto credentialDto;

    public RESTLogin() {
    }

    public RESTLogin(AccountInformationDto account, CredentialDto credentialDto) {
        this.account = account;
        this.credentialDto = credentialDto;
    }

    public AccountInformationDto getAccount() {
        return account;
    }

    public void setAccount(AccountInformationDto account) {
        this.account = account;
    }

    public CredentialDto getCredentialDto() {
        return credentialDto;
    }

    public void setCredentialDto(CredentialDto credentialDto) {
        this.credentialDto = credentialDto;
    }
}
