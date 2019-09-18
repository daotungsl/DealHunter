package com.focusteam.dealhunter.dto;

public class StoreDto {

    private String name;
    private String email;
    private String phone;
    private String image;
    private long created;
    private long updated;
    private int status;
    private long accountId;

    public StoreDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }


    public static final class StoreDtoBuilder {
        private String name;
        private String email;
        private String phone;
        private String image;
        private long created;
        private long updated;
        private int status;
        private long accountId;

        private StoreDtoBuilder() {
        }

        public static StoreDtoBuilder aStoreDto() {
            return new StoreDtoBuilder();
        }

        public StoreDtoBuilder addName(String name) {
            this.name = name;
            return this;
        }

        public StoreDtoBuilder addEmail(String email) {
            this.email = email;
            return this;
        }

        public StoreDtoBuilder addPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public StoreDtoBuilder addImage(String image) {
            this.image = image;
            return this;
        }

        public StoreDtoBuilder addCreated(long created) {
            this.created = created;
            return this;
        }

        public StoreDtoBuilder addUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public StoreDtoBuilder addStatus(int status) {
            this.status = status;
            return this;
        }

        public StoreDtoBuilder addAccountId(long accountId) {
            this.accountId = accountId;
            return this;
        }

        public StoreDto build() {
            StoreDto storeDto = new StoreDto();
            storeDto.setName(name);
            storeDto.setEmail(email);
            storeDto.setPhone(phone);
            storeDto.setImage(image);
            storeDto.setCreated(created);
            storeDto.setUpdated(updated);
            storeDto.setStatus(status);
            storeDto.setAccountId(accountId);
            return storeDto;
        }
    }
}
