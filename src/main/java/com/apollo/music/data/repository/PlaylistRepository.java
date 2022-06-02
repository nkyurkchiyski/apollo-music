package com.apollo.music.data.repository;

import com.apollo.music.data.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, String>, ExtendedPlaylistRepository {

    @Query("SELECT DISTINCT p FROM Playlist p LEFT JOIN p.songs s LEFT JOIN p.user u " +
            "WHERE u.id = :userId AND p.name = 'Liked Songs' AND p.createdBy = 'SYSTEM'")
    Optional<Playlist> getLikedSongsPlaylist(final String userId);


    @Query("SELECT DISTINCT p FROM Playlist p LEFT JOIN p.songs s LEFT JOIN p.user u " +
            "WHERE u.id = :userId AND (:name IS NULL OR p.name = :name) AND p.createdBy != 'SYSTEM'")
    Page<Playlist> findAllByUserId(final Pageable paging, final String userId, final String name);


    @Query("SELECT COUNT(DISTINCT p) FROM Playlist p LEFT JOIN p.songs s LEFT JOIN p.user u " +
            "WHERE u.id = :userId AND (:name IS NULL OR p.name = :name) AND p.createdBy != 'SYSTEM'")
    long countByUserId(final String userId, final String name);

    @Query("SELECT DISTINCT p FROM Playlist p LEFT JOIN p.songs s LEFT JOIN p.user u " +
            "WHERE u.id = :userId AND p.createdBy != 'SYSTEM' ORDER BY p.createdAt")
    List<Playlist> findAllByUserId(final String userId);
}
