package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Account_Store_Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private int status;

    @OneToOne(mappedBy = "account_store_owner")
    private Store_Owner_Information shop_owner_information;

    @OneToMany(mappedBy = "account_store_owner")
    private Set<Store_Owner_Credential> credentials;

    @OneToOne(mappedBy = "account_store_owner")
    private Store store;

    public Account_Store_Owner() {
    }

    public Account_Store_Owner(String username, String password, int status, Store_Owner_Information shop_owner_information) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.shop_owner_information = shop_owner_information;
    }

    public Account_Store_Owner(String username, String password) {
        this.username = username;
        this.password = password;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Store_Owner_Information getShop_owner_information() {
        return shop_owner_information;
    }

    public void setShop_owner_information(Store_Owner_Information shop_owner_information) {
        this.shop_owner_information = shop_owner_information;
    }

    public Set<Store_Owner_Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(Set<Store_Owner_Credential> credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "Account_Shop_Owner{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", shop_owner_information=" + shop_owner_information +
                '}';
    }


    public static final class Account_Store_OwnerBuilder {
        private long id;
        private String username;
        private String password;
        private int status;
        private Store_Owner_Information shop_owner_information;
        private Set<Store_Owner_Credential> credentials;
        private Store store;

        private Account_Store_OwnerBuilder() {
        }

        public static Account_Store_OwnerBuilder anAccount_Store_Owner() {
            return new Account_Store_OwnerBuilder();
        }

        public Account_Store_OwnerBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public Account_Store_OwnerBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Account_Store_OwnerBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Account_Store_OwnerBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public Account_Store_OwnerBuilder withShop_owner_information(Store_Owner_Information shop_owner_information) {
            this.shop_owner_information = shop_owner_information;
            return this;
        }

        public Account_Store_OwnerBuilder withCredentials(Set<Store_Owner_Credential> credentials) {
            this.credentials = credentials;
            return this;
        }

        public Account_Store_OwnerBuilder withStore(Store store) {
            this.store = store;
            return this;
        }

        public Account_Store_Owner build() {
            Account_Store_Owner account_Store_Owner = new Account_Store_Owner();
            account_Store_Owner.setId(id);
            account_Store_Owner.setUsername(username);
            account_Store_Owner.setPassword(password);
            account_Store_Owner.setStatus(status);
            account_Store_Owner.setShop_owner_information(shop_owner_information);
            account_Store_Owner.setCredentials(credentials);
            account_Store_Owner.store = this.store;
            return account_Store_Owner;
        }
    }
}
