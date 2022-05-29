package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = EntityConfiguration.SONG_TABLE_NAME)
public class Song extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Column(name = EntityConfiguration.NAME_COLUMN_NAME, nullable = false)
    private String name;

    @Column(name = EntityConfiguration.RELEASED_ON_COLUMN_NAME)
    private Date releasedOn;

    @Column(name = EntityConfiguration.LIKES_COUNT_COLUMN_NAME)
    private Integer likesCount;

    @Column(name = EntityConfiguration.PLAYED_COUNT_COLUMN_NAME)
    private Integer playedCount;

    @Column(name = EntityConfiguration.CREATED_AT_COLUMN_NAME, nullable = false)
    private Date createdAt;

    @Lob
    @Column(name = EntityConfiguration.IMAGE_URL_COLUMN_NAME)
    private String imageUrl;

    @Lob
    @Column(name = EntityConfiguration.SOURCE_URL_COLUMN_NAME, nullable = false)
    private String sourceUrl;

    @Column(name = EntityConfiguration.TRACK_NUMBER_COLUMN_NAME)
    private Integer trackNumber;

    //TODO: add featuredArtists fields/relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EntityConfiguration.ALBUM_ID_COLUMN_NAME)
    private Album album;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = EntityConfiguration.SONG_GENRE_TABLE_NAME, joinColumns = {@JoinColumn(name = EntityConfiguration.SONG_ID_COLUMN_NAME)}, inverseJoinColumns = {@JoinColumn(name = EntityConfiguration.GENRE_ID_COLUMN_NAME)})
    private Set<Genre> genres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EntityConfiguration.ARTIST_ID_COLUMN_NAME)
    private Artist artist;


    @Override
    protected void beforePersist() {
        createdAt = new Date();
        playedCount = 0;
        likesCount = 0;
    }

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

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(final Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getPlayedCount() {
        return playedCount;
    }

    public void setPlayedCount(final Integer playedCount) {
        this.playedCount = playedCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(final String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(final Integer trackNumber) {
        this.trackNumber = trackNumber;
    }
}
