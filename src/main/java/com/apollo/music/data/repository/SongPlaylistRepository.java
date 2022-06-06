package com.apollo.music.data.repository;

import com.apollo.music.data.entity.SongPlaylist;
import com.apollo.music.data.entity.SongPlaylistKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, SongPlaylistKey> {

    @Modifying
    @Query("DELETE FROM SongPlaylist sp WHERE sp.song.id IN :songIds")
    int removeAllWithSongIds(final Set<String> songIds);
}
