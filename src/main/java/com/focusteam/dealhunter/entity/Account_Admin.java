package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Account_Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private int status;

    @OneToMany(mappedBy = "account_admin")
    private Set<Admin_Credential> admin_credentials;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Admin_Credential> getAdmin_credentials() {
        return admin_credentials;
    }

    public void setAdmin_credentials(Set<Admin_Credential> admin_credentials) {
        this.admin_credentials = admin_credentials;
    }
}
