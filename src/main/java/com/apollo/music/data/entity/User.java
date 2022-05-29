package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = EntityConfiguration.USER_TABLE_NAME)
public class User extends AbstractEntity {

    @Column(name = EntityConfiguration.USERNAME_COLUMN_NAME, nullable = false, unique = true)
    private String username;

    @Column(name = EntityConfiguration.NAME_COLUMN_NAME)
    private String name;

    @JsonIgnore
    @Column(name = EntityConfiguration.PASSWORD_COLUMN_NAME, nullable = false)
    private String password;

    @Column(name = EntityConfiguration.ROLE_COLUMN_NAME)
    @CollectionTable(name = EntityConfiguration.USER_ROLE_TABLE_NAME)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Lob
    @Column(name = EntityConfiguration.IMAGE_URL_COLUMN_NAME)
    private String imageUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
