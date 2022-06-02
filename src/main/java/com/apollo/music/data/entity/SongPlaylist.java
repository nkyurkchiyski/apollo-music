package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;

@Entity
@DynamicInsert
@DynamicUpdate
public class SongPlaylist implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SongPlaylistKey id = new SongPlaylistKey();

    @ManyToOne
    @MapsId(EntityConfiguration.SONG_ID_FIELD_NAME)
    @JoinColumn(name = EntityConfiguration.SONG_ID_COLUMN_NAME, foreignKey = @ForeignKey(name = EntityConfiguration.SONG_PLAYLIST_SONG_FK_NAME))
    private Song song;

    @ManyToOne
    @MapsId(EntityConfiguration.PLAYLIST_ID_FIELD_NAME)
    @JoinColumn(name = EntityConfiguration.PLAYLIST_ID_COLUMN_NAME, foreignKey = @ForeignKey(name = EntityConfiguration.SONG_PLAYLIST_PLAYLIST_FK_NAME))
    private Playlist playlist;

    @Column(name = EntityConfiguration.TRACK_NUMBER_COLUMN_NAME)
    private Integer trackNumber;

    public SongPlaylist() {
    }

    public SongPlaylist(final Song song, final Playlist playlist, final Integer trackNumber) {
        this.song = song;
        this.playlist = playlist;
        this.trackNumber = trackNumber;
    }

    public Song getSong() {
        return song;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(final Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public SongPlaylistKey getId() {
        return id;
    }

    public void setId(final SongPlaylistKey id) {
        this.id = id;
    }

    public void setSong(final Song song) {
        this.song = song;
    }

    public void setPlaylist(final Playlist playlist) {
        this.playlist = playlist;
    }
}
