package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String accessToken;
    private String refreshToken;
    private String clientType;
    @NotNull
    private long created;
    private long expired;
    private long updated;
    private int status;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public static final class AccountCredentialBuilder {
        private long id;
        private String accessToken;
        private String refreshToken;
        private String clientType;
        private long created;
        private long expired;
        private long updated;
        private int status;
        private Account account;

        private AccountCredentialBuilder() {
        }

        public static AccountCredentialBuilder anAccountCredential() {
            return new AccountCredentialBuilder();
        }

        public AccountCredentialBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public AccountCredentialBuilder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public AccountCredentialBuilder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public AccountCredentialBuilder withClientType(String clientType) {
            this.clientType = clientType;
            return this;
        }

        public AccountCredentialBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public AccountCredentialBuilder withExpired(long expired) {
            this.expired = expired;
            return this;
        }

        public AccountCredentialBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public AccountCredentialBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public AccountCredentialBuilder withAccount(Account account) {
            this.account = account;
            return this;
        }

        public Credential build() {
            Credential accountCredential = new Credential();
            accountCredential.setId(id);
            accountCredential.setAccessToken(accessToken);
            accountCredential.setRefreshToken(refreshToken);
            accountCredential.setClientType(clientType);
            accountCredential.setCreated(created);
            accountCredential.setExpired(expired);
            accountCredential.setUpdated(updated);
            accountCredential.setStatus(status);
            accountCredential.setAccount(account);
            return accountCredential;
        }
    }
}
