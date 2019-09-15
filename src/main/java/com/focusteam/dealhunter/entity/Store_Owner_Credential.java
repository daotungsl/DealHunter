package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class Store_Owner_Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String access_token;
    @Null
    private String refresh_token;
    @Null
    private String client_type;
    @NotNull
    private long created;
    @Null
    private long expired;
    @Null
    private long updated;
    @Null
    private int status;

    @ManyToOne
    @JoinColumn(name = "store_owner_id", nullable = false)
    private Account_Store_Owner account_store_owner;

    public Store_Owner_Credential() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
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

    public Account_Store_Owner getAccount_store_owner() {
        return account_store_owner;
    }

    public void setAccount_store_owner(Account_Store_Owner account_store_owner) {
        this.account_store_owner = account_store_owner;
    }
}
