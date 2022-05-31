package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = EntityConfiguration.PLAYLIST_TABLE_NAME)
public class Playlist extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = EntityConfiguration.NAME_COLUMN_NAME, nullable = false)
    private String name;

    @Column(name = EntityConfiguration.CREATED_AT_COLUMN_NAME, nullable = false)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = EntityConfiguration.USER_ID_COLUMN_NAME, foreignKey = @ForeignKey(name = EntityConfiguration.PLAYLIST_USER_FK_NAME))
    private User user;

    @OneToMany(mappedBy = EntityConfiguration.PLAYLIST_FIELD_NAME)
    private List<SongPlaylist> songs;

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

    public List<SongPlaylist> getSongs() {
        return songs;
    }

    public void setSongs(final List<SongPlaylist> songs) {
        this.songs = songs;
    }
}
