package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = EntityConfiguration.USER_TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = EntityConfiguration.USER_USERNAME_UQ_NAME, columnNames = {EntityConfiguration.USERNAME_COLUMN_NAME}),
                @UniqueConstraint(name = EntityConfiguration.USER_EMAIL_UQ_NAME, columnNames = {EntityConfiguration.EMAIL_COLUMN_NAME})})
public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(name = EntityConfiguration.USERNAME_COLUMN_NAME, nullable = false)
    private String username;

    @NotEmpty
    @Email
    @Column(name = EntityConfiguration.EMAIL_COLUMN_NAME, nullable = false)
    private String email;

    @Column(name = EntityConfiguration.NAME_COLUMN_NAME)
    private String name;

    @JsonIgnore
    @Column(name = EntityConfiguration.PASSWORD_COLUMN_NAME, nullable = false)
    private String password;

    @Column(name = EntityConfiguration.ROLE_COLUMN_NAME)
    @CollectionTable(name = EntityConfiguration.USER_ROLE_TABLE_NAME, foreignKey = @ForeignKey(name = EntityConfiguration.USER_ROLE_FK_NAME))
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Lob
    @Column(name = EntityConfiguration.IMAGE_URL_COLUMN_NAME)
    private String imageUrl;

    @OneToMany(mappedBy = EntityConfiguration.USER_FIELD_NAME, fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private Set<Playlist> playlists = new LinkedHashSet<>();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(final Set<Playlist> playlists) {
        this.playlists = playlists;
    }
}
