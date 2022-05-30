package com.apollo.music.data.service;

import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ArtistService extends AbstractEntityService<Artist> {

    private final ArtistRepository repo;

    @Autowired
    public ArtistService(final ArtistRepository repo) {
        this.repo = repo;
    }

    @Override
    protected JpaRepository<Artist, String> getRepository() {
        return repo;
    }

}
