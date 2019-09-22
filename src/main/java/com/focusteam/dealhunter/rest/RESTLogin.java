package com.focusteam.dealhunter.rest;

import com.focusteam.dealhunter.dto.AccountInformationDto;
import com.focusteam.dealhunter.dto.CredentialDto;
import com.focusteam.dealhunter.entity.Account;

public class RESTLogin {
    private AccountInformationDto account;
    private CredentialDto credential;

    public RESTLogin() {
    }

    public RESTLogin(AccountInformationDto account, CredentialDto credentialDto) {
        this.account = account;
        this.credential = credentialDto;
    }

    public AccountInformationDto getAccount() {
        return account;
    }

    public void setAccount(AccountInformationDto account) {
        this.account = account;
    }

    public CredentialDto getCredential() {
        return credential;
    }

    public void setCredential(CredentialDto credential) {
        this.credential = credential;
    }
}
