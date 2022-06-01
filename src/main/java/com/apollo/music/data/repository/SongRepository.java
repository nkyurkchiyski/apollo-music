package com.apollo.music.data.repository;

import com.apollo.music.data.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, String> {
}
