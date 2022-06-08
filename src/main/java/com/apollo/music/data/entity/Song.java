package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.commons.GeneralUtils;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@DynamicInsert
@Table(name = EntityConfiguration.SONG_TABLE_NAME,
        uniqueConstraints = {@UniqueConstraint(name = EntityConfiguration.SONG_ONTO_DESC_UQ_NAME, columnNames = {EntityConfiguration.ONTO_DESC_COLUMN_NAME})})
public class Song extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @NotEmpty
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
    @Column(name = EntityConfiguration.SOURCE_URL_COLUMN_NAME, nullable = false)
    private String sourceUrl;

    @Column(name = EntityConfiguration.TRACK_NUMBER_COLUMN_NAME)
    private Integer trackNumber;

    @Column(name = EntityConfiguration.ONTO_DESC_COLUMN_NAME, nullable = false)
    private String ontoDescriptor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = EntityConfiguration.ALBUM_ID_COLUMN_NAME, foreignKey = @ForeignKey(name = EntityConfiguration.SONG_ALBUM_FK_NAME))
    private Album album;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = EntityConfiguration.GENRE_ID_COLUMN_NAME, foreignKey = @ForeignKey(name = EntityConfiguration.SONG_GENRE_FK_NAME))
    private Genre genre;


    @Override
    protected void beforePersist() {
        createdAt = new Date();
        playedCount = 0;
        likesCount = 0;
    }


    public static String createOntoDescriptor(final Song song) {
        return GeneralUtils.stripWhitespaces(
                String.format(EntityConfiguration.ONTO_DESC_FORMAT,
                        song.getName(),
                        song.getGenre().getName(),
                        song.getAlbum().getArtist().getName(),
                        song.getAlbum().getName())
        );
    }


    public static String createOntoDescriptor(final String songName, final String albumName, final String artistName, final String genreName) {
        return GeneralUtils.stripWhitespaces(
                String.format(EntityConfiguration.ONTO_DESC_FORMAT, songName, genreName, artistName, albumName)
        );
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

    public void incrementLikesCount() {
        this.likesCount++;
    }

    public void decrementLikesCount() {
        this.likesCount--;
    }

    public Integer getPlayedCount() {
        return playedCount;
    }

    public void setPlayedCount(final Integer playedCount) {
        this.playedCount = playedCount;
    }

    public void incrementPlayedCount() {
        this.playedCount++;
    }


    public Date getCreatedAt() {
        return createdAt;
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

    public String getOntoDescriptor() {
        return ontoDescriptor;
    }

    public void setOntoDescriptor(final String ontoDescriptor) {
        this.ontoDescriptor = ontoDescriptor;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(final Album album) {
        this.album = album;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }

    @Override
    public Map<String, Object> createFieldValueMap() {
        final Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("releasedOn", releasedOn);
        map.put("trackNumber", trackNumber);
        map.put("ontoDescriptor", ontoDescriptor);
        return map;
    }
}
