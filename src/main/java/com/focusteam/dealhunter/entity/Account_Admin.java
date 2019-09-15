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

    public static final class Account_AdminBuilder {
        private long id;
        private String username;
        private String password;
        private int status;
        private Set<Admin_Credential> admin_credentials;

        private Account_AdminBuilder() {
        }

        public static Account_AdminBuilder anAccount_Admin() {
            return new Account_AdminBuilder();
        }

        public Account_AdminBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public Account_AdminBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Account_AdminBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Account_AdminBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public Account_AdminBuilder withAdmin_credentials(Set<Admin_Credential> admin_credentials) {
            this.admin_credentials = admin_credentials;
            return this;
        }

        public Account_Admin build() {
            Account_Admin account_Admin = new Account_Admin();
            account_Admin.setId(id);
            account_Admin.setUsername(username);
            account_Admin.setPassword(password);
            account_Admin.setStatus(status);
            account_Admin.setAdmin_credentials(admin_credentials);
            return account_Admin;
        }
    }
}
