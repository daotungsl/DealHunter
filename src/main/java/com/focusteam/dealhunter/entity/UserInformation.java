package com.focusteam.dealhunter.entity;

import com.focusteam.dealhunter.dto.UserInformationDto;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class UserInformation {
    @Id
    private String email;
    private String fullName;
    private int gender;
    private String birthday;
    @NotNull
    private String phone;
    private String avatar;
    private String address;
    @NotNull
    private long created;
    private long updated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    public UserInformation() {
        this.created = System.currentTimeMillis();
        this.updated = System.currentTimeMillis();
    }

    public UserInformation(UserInformationDto userInformationDto) {
        this.fullName = userInformationDto.getFullName();
        this.gender = userInformationDto.getGender();
        this.birthday = userInformationDto.getBirthday();
        this.avatar = userInformationDto.getAvatar();
        this.address = userInformationDto.getAddress();
        //this.updated = userInformationDto.getUpdated();
    }



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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", account=" + account +
                '}';
    }

    public static final class UserInformationBuilder {
        private String email;
        private String fullName;
        private int gender;
        private String birthday;
        private String phone;
        private String avatar;
        private String address;
        private long created;
        private long updated;
        private Account account;

        private UserInformationBuilder() {
        }

        public static UserInformationBuilder anUserInformation() {
            return new UserInformationBuilder();
        }

        public UserInformationBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserInformationBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserInformationBuilder withGender(int gender) {
            this.gender = gender;
            return this;
        }

        public UserInformationBuilder withBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public UserInformationBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserInformationBuilder withAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public UserInformationBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public UserInformationBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public UserInformationBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public UserInformationBuilder withAccount(Account account) {
            this.account = account;
            return this;
        }

        public UserInformation build() {
            UserInformation userInformation = new UserInformation();
            userInformation.setEmail(email);
            userInformation.setFullName(fullName);
            userInformation.setGender(gender);
            userInformation.setBirthday(birthday);
            userInformation.setPhone(phone);
            userInformation.setAvatar(avatar);
            userInformation.setAddress(address);
            userInformation.setCreated(created);
            userInformation.setUpdated(updated);
            userInformation.setAccount(account);
            return userInformation;
        }
    }
}
