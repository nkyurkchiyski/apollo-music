package com.apollo.music.data.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public abstract class AbstractEntity implements EntityWithId, Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false; // null or other class
        }
        AbstractEntity other = (AbstractEntity) obj;

        if (id != null) {
            return id.equals(other.id);
        }
        return super.equals(other);
    }

    @PrePersist
    protected void beforePersist() {
        // do nothing
    }

    @PreRemove
    protected void beforeRemove() {
        // do nothing
    }

    @PreUpdate
    protected void beforeUpdate() {
        // do nothing
    }

    @Override
    public Map<String, Object> createFieldValueMap() {
        return new HashMap<>();
    }


}
