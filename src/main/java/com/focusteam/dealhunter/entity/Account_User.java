package com.focusteam.dealhunter.entity;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Account_User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private int status;

    @OneToOne(mappedBy = "account_user")
    private User_Information user_information;

    @OneToMany(mappedBy = "account_user")
    private Set<History_Order> history_orders;

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

    public User_Information getUser_information() {
        return user_information;
    }

    public void setUser_information(User_Information user_information) {
        this.user_information = user_information;
    }

    public Set<History_Order> getHistory_orders() {
        return history_orders;
    }

    public void setHistory_orders(Set<History_Order> history_orders) {
        this.history_orders = history_orders;
    }
}
