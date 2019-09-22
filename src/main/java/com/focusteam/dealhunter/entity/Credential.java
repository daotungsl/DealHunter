package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Calendar;

@Entity
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String accessToken;
    private String token;
    private String refreshToken;
    private String clientType;
    @NotNull
    private long created;
    private long expired;
    private long updated;
    private int status;

    public Credential() {
    }

    public Credential(@NotNull String accessToken, String refreshToken, String clientType, Account account) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.clientType = clientType;
        this.account = account;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static final class CredentialBuilder {
        private long id;
        private String accessToken;
        private String token;
        private String refreshToken;
        private String clientType;
        private long created;
        private long expired;
        private long updated;
        private int status;
        private Account account;

        private CredentialBuilder() {
        }

        public static CredentialBuilder aCredential() {
            return new CredentialBuilder();
        }

        public CredentialBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public CredentialBuilder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public CredentialBuilder withToken(String token) {
            this.token = token;
            return this;
        }

        public CredentialBuilder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public CredentialBuilder withClientType(String clientType) {
            this.clientType = clientType;
            return this;
        }

        public CredentialBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public CredentialBuilder withExpired(long expired) {
            this.expired = expired;
            return this;
        }

        public CredentialBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public CredentialBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public CredentialBuilder withAccount(Account account) {
            this.account = account;
            return this;
        }

        public Credential build() {
            Credential credential = new Credential();
            credential.setId(id);
            credential.setAccessToken(accessToken);
            credential.setToken(token);
            credential.setRefreshToken(refreshToken);
            credential.setClientType(clientType);
            credential.setCreated(created);
            credential.setExpired(expired);
            credential.setUpdated(updated);
            credential.setStatus(status);
            credential.setAccount(account);
            return credential;
        }
    }
}
