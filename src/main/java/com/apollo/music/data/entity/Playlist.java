package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = EntityConfiguration.PLAYLIST_TABLE_NAME)
public class Playlist extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = EntityConfiguration.NAME_COLUMN_NAME, nullable = false)
    private String name;

    @Column(name = EntityConfiguration.CREATED_AT_COLUMN_NAME, nullable = false)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = EntityConfiguration.CREATED_BY_COLUMN_NAME, nullable = false)
    private Role createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = EntityConfiguration.USER_ID_COLUMN_NAME, foreignKey = @ForeignKey(name = EntityConfiguration.PLAYLIST_USER_FK_NAME))
    private User user;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(mappedBy = EntityConfiguration.PLAYLIST_FIELD_NAME, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    private Set<SongPlaylist> songs = new LinkedHashSet<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    protected void beforePersist() {
        createdAt = new Date();
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Set<SongPlaylist> getSongs() {
        return songs;
    }

    public void setSongs(final Set<SongPlaylist> songs) {
        this.songs = songs;
    }

    public Role getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Role createdBy) {
        this.createdBy = createdBy;
    }

    public void addSong(final SongPlaylist song) {
        songs.add(song);
    }

    public void removeSong(final SongPlaylist song) {
        songs.remove(song);
    }
}
