package com.apollo.music.data.service;

import com.apollo.music.data.entity.Artist;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {

}