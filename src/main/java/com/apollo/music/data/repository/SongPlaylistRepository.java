package com.apollo.music.data.repository;

import com.apollo.music.data.entity.SongPlaylist;
import com.apollo.music.data.entity.SongPlaylistKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, SongPlaylistKey> {
}
