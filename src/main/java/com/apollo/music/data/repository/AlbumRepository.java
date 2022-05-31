package com.apollo.music.data.repository;

import com.apollo.music.data.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, String> {
}
