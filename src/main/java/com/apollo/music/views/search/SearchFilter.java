package com.apollo.music.views.search;

import com.google.common.base.Strings;

public class SearchFilter {
    private final String song;
    private final String genre;
    private final String album;
    private final String artist;


    public SearchFilter(final String song, final String genre, final String album, final String artist) {
        this.song = song;
        this.genre = genre;
        this.album = album;
        this.artist = artist;
    }


    public String getSong() {
        return Strings.emptyToNull(song);
    }

    public String getGenre() {
        return Strings.emptyToNull(genre);
    }

    public String getAlbum() {
        return Strings.emptyToNull(album);
    }

    public String getArtist() {
        return Strings.emptyToNull(artist);
    }
}
