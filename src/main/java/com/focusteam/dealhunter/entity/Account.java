package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private int typeAccount;
    @NotNull
    private int status;

    @OneToOne(mappedBy = "account")
    private UserInformation userInformation;

    @OneToMany(mappedBy = "account")
    private Set<Credential> credentials;

    @OneToOne(mappedBy = "account")
    private Store store;

    public Account() {
    }

    public Account(@NotNull String username, @NotNull String password, @NotNull int typeAccount, @NotNull int status, UserInformation userInformation) {
        this.username = username;
        this.password = password;
        this.typeAccount = typeAccount;
        this.status = status;
        this.userInformation = userInformation;
    }

    public Account(@NotNull String username, @NotNull String password, @NotNull int typeAccount) {
        this.username = username;
        this.password = password;
        this.typeAccount = typeAccount;
    }

    public Account(@NotNull String username, @NotNull String password, @NotNull int typeAccount, @NotNull int status,  UserInformation userInformation, Set<Credential> credentials, Store store) {
        this.username = username;
        this.password = password;
        this.typeAccount = typeAccount;
        this.status = status;
        this.userInformation = userInformation;
        this.credentials = credentials;
        this.store = store;
    }

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

    public int getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(int typeAccount) {
        this.typeAccount = typeAccount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public Set<Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(Set<Credential> credentials) {
        this.credentials = credentials;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


    public static final class AccountBuilder {
        private long id;
        private String username;
        private String password;
        private int typeAccount;
        private int status;
        private UserInformation userInformation;
        private Set<Credential> credentials;
        private Store store;

        private AccountBuilder() {
        }

        public static AccountBuilder anAccount() {
            return new AccountBuilder();
        }

        public AccountBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public AccountBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public AccountBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public AccountBuilder withTypeAccount(int typeAccount) {
            this.typeAccount = typeAccount;
            return this;
        }

        public AccountBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public AccountBuilder withUserInformation(UserInformation userInformation) {
            this.userInformation = userInformation;
            return this;
        }

        public AccountBuilder withCredentials(Set<Credential> credentials) {
            this.credentials = credentials;
            return this;
        }

        public AccountBuilder withStore(Store store) {
            this.store = store;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.setId(id);
            account.setUsername(username);
            account.setPassword(password);
            account.setTypeAccount(typeAccount);
            account.setStatus(status);
            account.setUserInformation(userInformation);
            account.setCredentials(credentials);
            account.setStore(store);
            return account;
        }
    }
}