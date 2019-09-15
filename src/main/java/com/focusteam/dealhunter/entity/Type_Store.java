package com.focusteam.dealhunter.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Entity
public class Type_Store {
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

    @OneToMany(mappedBy = "type_store")
    private Set<Store> stores;

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

    public Set<Store> getStores() {
        return stores;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }


    public static final class Type_StoreBuilder {
        private long id;
        private String name;
        private String description;
        private long created;
        private long updated;
        private int status;
        private Set<Store> stores;

        private Type_StoreBuilder() {
        }

        public static Type_StoreBuilder aType_Store() {
            return new Type_StoreBuilder();
        }

        public Type_StoreBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public Type_StoreBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Type_StoreBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Type_StoreBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public Type_StoreBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public Type_StoreBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public Type_StoreBuilder withStores(Set<Store> stores) {
            this.stores = stores;
            return this;
        }

        public Type_Store build() {
            Type_Store type_Store = new Type_Store();
            type_Store.setId(id);
            type_Store.setName(name);
            type_Store.setDescription(description);
            type_Store.setCreated(created);
            type_Store.setUpdated(updated);
            type_Store.setStatus(status);
            type_Store.setStores(stores);
            return type_Store;
        }
    }
}
