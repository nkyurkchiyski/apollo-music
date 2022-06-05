package com.apollo.music.data.repository;

import com.apollo.music.data.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, String> {

    @Query("SELECT DISTINCT s FROM Song s WHERE s.ontoHash IN :songsOntoHash")
    Page<Song> findAllByOntoHash(final Pageable pageable, final List<String> songsOntoHash);

    @Query("SELECT DISTINCT s FROM Song s JOIN s.album a JOIN s.genre g JOIN a.artist at " +
            "WHERE (:songName IS NULL OR s.name LIKE %:songName%) AND (:artistName IS NULL OR at.name LIKE %:artistName%) " +//AND (:artistName IS NULL OR at.artist.name LIKE %:artistName%)
            "AND (:albumName IS NULL OR a.name LIKE %:albumName%) AND (:genreName IS NULL OR g.name LIKE %:genreName%)")
    Page<Song> findAllBySearchFilter(final Pageable paging,
                                     final String songName,
                                     final String artistName,
                                     final String albumName,
                                     final String genreName);
}
