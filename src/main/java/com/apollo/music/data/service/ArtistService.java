package com.apollo.music.data.service;

import com.apollo.music.data.entity.Artist;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private ArtistRepository repository;

    public ArtistService(@Autowired ArtistRepository repository) {
        this.repository = repository;
    }

    public Optional<Artist> get(UUID id) {
        return repository.findById(id);
    }

    public Artist update(Artist entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Artist> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
