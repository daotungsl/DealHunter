package com.focusteam.dealhunter.entity;

import com.focusteam.dealhunter.dto.groupAccountDto.AccountDto;
import com.focusteam.dealhunter.dto.groupAccountDto.AccountStoreDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    @NotNull
    private int typeAccount;
    private String token;
    @NotNull
    private int confirmEmail;
    @NotNull
    private int status;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private UserInformation userInformation;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Credential credential;

    @OneToOne(mappedBy = "account")
    private Store store;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;

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
        this.typeAccount = 0;
        this.confirmEmail = 0;
        this.status = 0;
        UserInformation userInformation = new UserInformation();
        userInformation.setCreated(System.currentTimeMillis());
        userInformation.setUpdated(System.currentTimeMillis());
        userInformation.setFullName(accountDto.getFullName());
        userInformation.setEmail(accountDto.getEmail());
        userInformation.setPhone(accountDto.getPhone());
        //userInformation.setSalt(new StringUtil().randomString());
        userInformation.setAccount(this);
        this.userInformation = userInformation;
    }

    public Account(AccountStoreDto accountStoreDto) {
        this.username = accountStoreDto.getEmail();
        this.password = accountStoreDto.getPassword();
        this.typeAccount = 1;
        this.confirmEmail = 0;
        this.status = 0;

        UserInformation userInformation = new UserInformation();
        userInformation.setEmail(accountStoreDto.getEmail());
        userInformation.setPhone(accountStoreDto.getPhone());
        userInformation.setFullName(accountStoreDto.getFullName());
        userInformation.setGender(accountStoreDto.getGender());
        userInformation.setBirthday(accountStoreDto.getBirthday());
        userInformation.setAddress(accountStoreDto.getAddress());
        userInformation.setCreated(System.currentTimeMillis());
        userInformation.setUpdated(System.currentTimeMillis());
        userInformation.setAccount(this);

        this.userInformation = userInformation;
    }

    public int getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(int confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
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
