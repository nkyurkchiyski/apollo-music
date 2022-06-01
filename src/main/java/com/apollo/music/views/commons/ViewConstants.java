package com.apollo.music.views.commons;

public class ViewConstants {
    public static final class Route {
        public static final String MANAGE_GENRE = "manage/genre";
        public static final String MANAGE_ARTIST = "manage/artist";
        public static final String MANAGE_ALBUM = "manage/album";
        public static final String MANAGE_SONG = "manage/song";
        public static final String ACCOUNT = "account";
    }

    public static final class Title {
        public static final String MANAGE_GENRE = "Manage Genres";
        public static final String MANAGE_ARTIST = "Manage Artists";
        public static final String MANAGE_ALBUM = "Manage Albums";
        public static final String MANAGE_SONG = "Manage Songs";
        public static final String MY_ACCOUNT = "My Account";
    }

    public static final class Validation {
        public static final String EMPTY_FIELD_FORMAT = "Field cannot be empty, please select %s.";
        public static final String USER_ALREADY_EXISTS = "User with the specified %s already exist!";
    }

    public static final String DEFAULT_COVER = "http://developer-assets.ws.sonos.com/doc-assets/portalDocs-sonosApp-defaultArtAlone.png";

}
