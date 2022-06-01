package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = EntityConfiguration.ALBUM_TABLE_NAME)
public class Album extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(name = EntityConfiguration.NAME_COLUMN_NAME, nullable = false)
    private String name;

    @Column(name = EntityConfiguration.RELEASED_ON_COLUMN_NAME)
    private Date releasedOn;

    @Lob
    @Column(name = EntityConfiguration.IMAGE_URL_COLUMN_NAME)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = EntityConfiguration.ARTIST_ID_COLUMN_NAME, foreignKey = @ForeignKey(name = EntityConfiguration.ALBUM_ARTIST_FK_NAME))
    private Artist artist;

    @OneToMany(mappedBy = EntityConfiguration.ALBUM_FIELD_NAME, fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(final Date releasedOn) {
        this.releasedOn = releasedOn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(final List<Song> songs) {
        this.songs = songs;
    }


    public void removeSong(final Song song) {
        getSongs().remove(song);
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(final Artist artist) {
        this.artist = artist;
    }
}
