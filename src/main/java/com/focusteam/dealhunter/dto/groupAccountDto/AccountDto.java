package com.focusteam.dealhunter.dto.groupAccountDto;

import com.focusteam.dealhunter.entity.Account;

import javax.validation.constraints.*;

public class AccountDto {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 50)
    private String fullName;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 15)
    @Digits(integer = 10, fraction = 15)
    private String phone;

    @Email
    @NotNull
    @NotEmpty
    @Size(min = 15, max = 50)
    private String email;

    @NotEmpty
    @NotNull
    @Size(min = 6, max = 200)
    private String password;

    @NotEmpty
    @NotNull
    @Size(min = 6, max = 200)
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
