package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    @Null
    private String description;
    @NotNull
    private long created;
    @Null
    private long updated;
    @NotNull
    private int status;

    @OneToMany(mappedBy = "city")
    private Set<StoreAddress> storeAddresses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<StoreAddress> getStoreAddresses() {
        return storeAddresses;
    }

    public void setStoreAddresses(Set<StoreAddress> storeAddresses) {
        this.storeAddresses = storeAddresses;
    }


    public static final class CityBuilder {
        private long id;
        private String name;
        private String description;
        private long created;
        private long updated;
        private int status;
        private Set<StoreAddress> storeAddresses;

        private CityBuilder() {
        }

        public static CityBuilder aCity() {
            return new CityBuilder();
        }

        public CityBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public CityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public CityBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public CityBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public CityBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public CityBuilder withStoreAddresses(Set<StoreAddress> storeAddresses) {
            this.storeAddresses = storeAddresses;
            return this;
        }

        public City build() {
            City city = new City();
            city.setId(id);
            city.setName(name);
            city.setDescription(description);
            city.setCreated(created);
            city.setUpdated(updated);
            city.setStatus(status);
            city.setStoreAddresses(storeAddresses);
            return city;
        }
    }
}
