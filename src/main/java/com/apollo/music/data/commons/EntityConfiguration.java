package com.apollo.music.data.commons;

public class EntityConfiguration {

    //DB Tables - START
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_ROLE_TABLE_NAME = "user_role";
    public static final String SONG_GENRE_TABLE_NAME = "song_genre";
    public static final String ARTIST_TABLE_NAME = "artist";
    public static final String SONG_TABLE_NAME = "song";
    public static final String ALBUM_TABLE_NAME = "album";
    public static final String GENRE_TABLE_NAME = "genre";
    public static final String PLAYLIST_TABLE_NAME = "playlist";

    //DB Tables - END

    //DB Columns - START
    public static final String NAME_COLUMN_NAME = "name";
    public static final String USERNAME_COLUMN_NAME = "username";
    public static final String EMAIL_COLUMN_NAME = "email";
    public static final String PASSWORD_COLUMN_NAME = "password";
    public static final String IMAGE_URL_COLUMN_NAME = "image_url";
    public static final String ROLE_COLUMN_NAME = "role";
    public static final String CREATED_AT_COLUMN_NAME = "created_at";
    public static final String CREATED_BY_COLUMN_NAME = "created_by";
    public static final String RELEASED_ON_COLUMN_NAME = "released_on";
    public static final String SOURCE_URL_COLUMN_NAME = "source_url";
    public static final String PLAYED_COUNT_COLUMN_NAME = "played_count";
    public static final String LIKES_COUNT_COLUMN_NAME = "likes_count";
    public static final String TRACK_NUMBER_COLUMN_NAME = "track_number";
    public static final String ALBUM_ID_COLUMN_NAME = "album_id";
    public static final String GENRE_ID_COLUMN_NAME = "genre_id";
    public static final String ARTIST_ID_COLUMN_NAME = "artist_id";
    public static final String SONG_ID_COLUMN_NAME = "song_id";
    public static final String USER_ID_COLUMN_NAME = "user_id";
    public static final String PLAYLIST_ID_COLUMN_NAME = "playlist_id";
    public static final String ONTO_HASH_COLUMN_NAME = "onto_hash";

    //DB Columns - END

    //DB Constraints - START
    public static final String PLAYLIST_USER_FK_NAME = "FK_Playlist_User";
    public static final String USER_ROLE_FK_NAME = "FK_User_Role";
    public static final String SONG_PLAYLIST_PLAYLIST_FK_NAME = "FK_SongPlaylist_Playlist";
    public static final String SONG_PLAYLIST_SONG_FK_NAME = "FK_SongPlaylist_Song";
    public static final String SONG_GENRE_FK_NAME = "FK_Song_Genre";
    public static final String SONG_ALBUM_FK_NAME = "FK_Song_Album";
    public static final String ALBUM_ARTIST_FK_NAME = "FK_Album_Artist";

    public static final String USER_USERNAME_UQ_NAME = "UQ_User_Username";
    public static final String USER_EMAIL_UQ_NAME = "UQ_User_Email";
    public static final String SONG_ONTO_HASH_UQ_NAME = "UQ_Song_OntoHash";
    //DB Constraints - END

    //Fields - START
    public static final String ALBUM_FIELD_NAME = "album";
    public static final String SONGS_FIELD_NAME = "songs";
    public static final String ARTIST_FIELD_NAME = "artist";
    public static final String SONG_ID_FIELD_NAME = "songId";
    public static final String PLAYLIST_ID_FIELD_NAME = "playlistId";
    public static final String PLAYLIST_FIELD_NAME = "playlist";
    public static final String USERNAME_FIELD_NAME = "username";
    public static final String EMAIL_FIELD_NAME = "email";
    public static final String ONTO_HASH_FIELD_NAME = "ontoHash";
    public static final String USER_FIELD_NAME = "user";

    //Fields - END

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    public static final String SYSTEM = "system";
    public static final String ANON = "anon";

    public static final String ONTO_HASH_FORMAT = "Song:%s;Genre:%s;Artist:%s;Album:%s";

    public static final String LIKED_SONGS = "Liked Songs";
}
