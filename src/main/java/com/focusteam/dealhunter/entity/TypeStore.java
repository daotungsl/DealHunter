package com.focusteam.dealhunter.entity;

import com.focusteam.dealhunter.dto.groupTypeStoreDto.TypeStoreCreateDto;
import com.focusteam.dealhunter.util.StringUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class TypeStore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;

    private String nameUnAccent;

    @Size(min = 15, max = 2000)
    private String description;
    @NotNull
    private long created;
    private long updated;
    @NotNull
    private int status;

    @OneToMany(mappedBy = "typeStore")
    private Set<Store> stores;

    public TypeStore() {
    }

    public TypeStore(TypeStoreCreateDto typeStoreCreateDto) {
        this.name = typeStoreCreateDto.getName();
        this.nameUnAccent = new StringUtil().unAccent(typeStoreCreateDto.getName());
        this.description = typeStoreCreateDto.getDescription();
        this.created = System.currentTimeMillis();
        this.updated = System.currentTimeMillis();
        this.status = 1;
    }

    public String getNameUnAccent() {
        return nameUnAccent;
    }

    public void setNameUnAccent(String nameUnAccent) {
        this.nameUnAccent = nameUnAccent;
    }

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


    public static final class TypeStoreBuilder {
        private long id;
        private String name;
        private String description;
        private long created;
        private long updated;
        private int status;
        private Set<Store> stores;

        private TypeStoreBuilder() {
        }

        public static TypeStoreBuilder aTypeStore() {
            return new TypeStoreBuilder();
        }

        public TypeStoreBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public TypeStoreBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TypeStoreBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TypeStoreBuilder withCreated(long created) {
            this.created = created;
            return this;
        }

        public TypeStoreBuilder withUpdated(long updated) {
            this.updated = updated;
            return this;
        }

        public TypeStoreBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public TypeStoreBuilder withStores(Set<Store> stores) {
            this.stores = stores;
            return this;
        }

        public TypeStore build() {
            TypeStore typeStore = new TypeStore();
            typeStore.setId(id);
            typeStore.setName(name);
            typeStore.setDescription(description);
            typeStore.setCreated(created);
            typeStore.setUpdated(updated);
            typeStore.setStatus(status);
            typeStore.setStores(stores);
            return typeStore;
        }
    }
}
