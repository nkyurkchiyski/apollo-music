package com.apollo.music.data.repository;

import com.apollo.music.data.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<Genre, String> {

    @Query("SELECT count(s) > 0 FROM Song s JOIN s.genre g WHERE g.id=:genreId")
    boolean hasAnySongs(final String genreId);
}
