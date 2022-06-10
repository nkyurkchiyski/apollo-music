package com.apollo.music.data.repository;

import com.apollo.music.data.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, String> {

    @Query("SELECT DISTINCT s FROM Song s WHERE s.ontoDescriptor IN :songsOntoDesc ORDER BY s.likesCount DESC")
    Page<Song> findAllByOntoDesc(final Pageable pageable, final List<String> songsOntoDesc);

    @Query("SELECT DISTINCT s FROM Song s JOIN s.album a JOIN s.genre g JOIN a.artist at " +
            "WHERE (:songName IS NULL OR s.name LIKE %:songName%) AND (:artistName IS NULL OR at.name LIKE %:artistName%) " +//AND (:artistName IS NULL OR at.artist.name LIKE %:artistName%)
            "AND (:albumName IS NULL OR a.name LIKE %:albumName%) AND (:genreName IS NULL OR g.name LIKE %:genreName%)")
    Page<Song> findAllBySearchFilter(final Pageable paging,
                                     final String songName,
                                     final String artistName,
                                     final String albumName,
                                     final String genreName);


    @Query("SELECT DISTINCT s FROM Song s ORDER BY s.releasedOn DESC")
    Page<Song> findAllByReleaseDate(final Pageable pageable);

    @Query("SELECT DISTINCT s FROM Song s ORDER BY s.likesCount DESC")
    Page<Song> findAllByLikes(final Pageable pageable);

    @Query("SELECT count(s) > 0 FROM Song s WHERE s.ontoDescriptor=:ontoDesc AND (:id IS NULL OR s.id!=:id)")
    boolean existsWithOntoDesc(final String id, final String ontoDesc);

    @Query("SELECT DISTINCT s FROM Song s WHERE s.id NOT IN :songIds ORDER BY s.playedCount DESC")
    Page<Song> findAllNotWithId(final Pageable pageable, final List<String> songIds);

    @Query("SELECT DISTINCT s FROM Song s ORDER BY s.playedCount DESC")
    Page<Song> findAllByPlays(final Pageable pageable);
}
