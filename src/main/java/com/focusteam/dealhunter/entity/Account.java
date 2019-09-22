package com.focusteam.dealhunter.entity;

import com.focusteam.dealhunter.dto.AccountDto;

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
    private String token;
    @NotNull
    private int status;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private UserInformation userInformation;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Credential credential;

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

    public Account(@NotNull String username, @NotNull String password, @NotNull int typeAccount, @NotNull int status,  UserInformation userInformation, Credential credential, Store store) {
        this.username = username;
        this.password = password;
        this.typeAccount = typeAccount;
        this.status = status;
        this.userInformation = userInformation;
        this.credential = credential;
        this.store = store;
    }

    public Account(AccountDto accountDto) {
        this.username = accountDto.getEmail();
        this.password = accountDto.getPassword();
        this.typeAccount = 1;
        this.status = 1;
        UserInformation userInformation = new UserInformation();
        userInformation.setCreated(System.currentTimeMillis());
        userInformation.setUpdated(System.currentTimeMillis());
        userInformation.setEmail(accountDto.getEmail());
        userInformation.setPhone(accountDto.getPhone());
        userInformation.setAccount(this);
        this.userInformation = userInformation;
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

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", typeAccount=" + typeAccount +
                ", token='" + token + '\'' +
                ", status=" + status +
                ", userInformation=" + userInformation +
                ", credential=" + credential +
                ", store=" + store +
                '}';
    }

    public static final class AccountBuilder {
        private long id;
        private String username;
        private String password;
        private int typeAccount;
        private String token;
        private int status;
        private UserInformation userInformation;
        private Credential credential;
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

        public AccountBuilder withToken(String token) {
            this.token = token;
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

        public AccountBuilder withCredential(Credential credential) {
            this.credential = credential;
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
            account.setToken(token);
            account.setStatus(status);
            account.setUserInformation(userInformation);
            account.setCredential(credential);
            account.setStore(store);
            return account;
        }
    }
}
