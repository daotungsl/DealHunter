package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class User_Information {
    @Id
    private String email;
    @Null
    private String fullName;
    @Null
    private int gender;
    @Null
    private String birthday;
    @NotNull
    private String phone;
    @Null
    private String avatar;
    @Null
    private String address;
    @NotNull
    private long created;
    @Null
    private long updated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_user_id", referencedColumnName = "id", nullable = false)
    private Account_User account_user;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public Account_User getAccount_user() {
        return account_user;
    }

    public void setAccount_user(Account_User account_user) {
        this.account_user = account_user;
    }


    public static final class User_InformationBuilder {
        private String email;
        private String fullName;
        private int gender;
        private String birthday;
        private String phone;
        private String avatar;
        private String address;
        private long created;
        private long updated;
        private Account_User account_user;

        private User_InformationBuilder() {
        }

        public static User_InformationBuilder anUser_Information() {
            return new User_InformationBuilder();
        }

        public User_InformationBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public User_InformationBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public User_InformationBuilder withGender(int gender) {
            this.gender = gender;
            return this;
        }

        public User_InformationBuilder withBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public User_InformationBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public User_InformationBuilder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public User_InformationBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public User_InformationBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public User_InformationBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public User_InformationBuilder withAccount_user(Account_User account_user) {
            this.account_user = account_user;
            return this;
        }

        public User_Information build() {
            User_Information user_Information = new User_Information();
            user_Information.setEmail(email);
            user_Information.setFullName(fullName);
            user_Information.setGender(gender);
            user_Information.setBirthday(birthday);
            user_Information.setPhone(phone);
            user_Information.setAvatar(avatar);
            user_Information.setAddress(address);
            user_Information.setCreated(created);
            user_Information.setUpdated(updated);
            user_Information.setAccount_user(account_user);
            return user_Information;
        }
    }
}
