package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;

@Entity
public class SongPlaylist implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SongPlaylistKey id;

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

    public Song getSong() {
        return song;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }
}
