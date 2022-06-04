package com.apollo.music.views.commons;

import java.text.SimpleDateFormat;

public class ViewConstants {
    public static final class Route {
        public static final String ROUTE_FORMAT = "%s/%s";
        public static final String MANAGE = "manage";
        public static final String SONG = "song";
        public static final String ARTIST = "artist";
        public static final String ALBUM = "album";
        public static final String PLAYLIST = "playlist";
        public static final String GENRE = "genre";
        public static final String ACCOUNT = "account";
        public static final String EXPLORE = "explore";

        public static final String MANAGE_GENRE = "manage/genre";
        public static final String MANAGE_ARTIST = "manage/artist";
        public static final String MANAGE_ALBUM = "manage/album";
        public static final String MANAGE_SONG = "manage/song";

        public static final String LIKES = "likes";
        public static final String LOGIN = "login";
        public static final String REGISTER = "register";
    }

    public static final class Title {
        public static final String MANAGE_GENRE = "Manage Genres";
        public static final String MANAGE_ARTIST = "Manage Artists";
        public static final String MANAGE_ALBUM = "Manage Albums";
        public static final String MANAGE_SONG = "Manage Songs";
        public static final String MY_ACCOUNT = "My Account";

        public static final String SONG_DETAILS = "Song Details";
        public static final String ALBUM_DETAILS = "Album Details";
        public static final String ARTIST_DETAILS = "Artist Details";
        public static final String LIKED_SONGS = "Liked Songs";
        public static final String PLAYLIST_DETAILS = "Playlist Details";
        public static final String LOGIN = "Login";
        public static final String REGISTER = "Register";
    }

    public static final class Validation {
        public static final String EMPTY_FIELD_FORMAT = "Field cannot be empty, please select %s.";
        public static final String USER_ALREADY_EXISTS = "User with the specified %s already exist!";
        public static final String PASSWORDS_NOT_MATCH = "The confirm password does not match!";
    }

    public static final class Notification {
        public static final String ADDED_TO_PLAYLIST = "Song was successfully added to playlist!";
        public static final String REMOVED_FROM_PLAYLIST = "Song was successfully removed from playlist!";
        public static final String USER_ALREADY_EXISTS = "User with the specified %s already exist!";
        public static final String PLAYLIST_DELETED = "Playlist was successfully deleted!";
        public static final String PLAYLIST_SAVED = "Playlist was saved!";
        public static final String SONG_LIKED_FORMAT = "Song %s Liked Songs";
        public static final String REGISTER_SUCCESS = "User successfully registered!";
    }

    public static final String DEFAULT_COVER = "http://developer-assets.ws.sonos.com/doc-assets/portalDocs-sonosApp-defaultArtAlone.png";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static final String ADDED_TO = "added to";
    public static final String REMOVED_FROM = "removed from";
}
