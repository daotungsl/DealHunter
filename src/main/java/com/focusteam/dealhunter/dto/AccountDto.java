package com.focusteam.dealhunter.dto;

import com.focusteam.dealhunter.entity.Account;

public class AccountDto {
    private String phone;
    private String email;
    private String password;
    private String repassword;

    public AccountDto(String phone, String email, String password, String repassword) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.repassword = repassword;
    }

    public AccountDto() {
    }

    public AccountDto(Account account) {
        this.email = account.getUsername();
        this.phone = account.getUserInformation().getPhone();
        this.password = account.getPassword();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}