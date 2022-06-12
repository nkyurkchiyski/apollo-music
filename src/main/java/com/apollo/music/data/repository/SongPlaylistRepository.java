package com.apollo.music.data.repository;

import com.apollo.music.data.entity.SongPlaylist;
import com.apollo.music.data.entity.SongPlaylistKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, SongPlaylistKey> {

    @Modifying
    @Query("DELETE FROM SongPlaylist sp WHERE sp.song.id IN :songIds")
    int removeAllWithSongIds(final Set<String> songIds);


    @Modifying
    @Query("DELETE FROM SongPlaylist sp WHERE sp.playlist.id IN :playlistId")
    int removeAllWithPlaylistId(final String playlistId);


    @Query("SELECT DISTINCT sp.song.ontoDescriptor FROM SongPlaylist sp LEFT JOIN sp.playlist p " +
            "WHERE p.user.id = :userId AND p.name = 'Liked Songs' AND p.createdBy = 'SYSTEM'")
    Page<String> findLikedSongsOntoDescByUser(final Pageable pageable, final String userId);
}
