package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SongPlaylistKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = EntityConfiguration.SONG_ID_COLUMN_NAME)
    private String songId;

    @Column(name = EntityConfiguration.PLAYLIST_ID_COLUMN_NAME)
    private String playlistId;
}
