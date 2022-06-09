package com.apollo.music.data.service;

import com.apollo.music.data.entity.SongPlaylist;
import com.apollo.music.data.entity.SongPlaylistKey;
import com.apollo.music.data.entity.User;
import com.apollo.music.data.repository.SongPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.Set;

@Service
public class SongPlaylistService extends CrudService<SongPlaylist, SongPlaylistKey> {
    private final SongPlaylistRepository repo;

    @Autowired
    public SongPlaylistService(final SongPlaylistRepository repo) {
        this.repo = repo;
    }

    @Override
    protected JpaRepository<SongPlaylist, SongPlaylistKey> getRepository() {
        return repo;
    }


    public void deleteAllWithSongs(final Set<String> songIds) {
        repo.removeAllWithSongIds(songIds);
    }


    public Page<String> getLikedSongsOntoDescByUser(final Pageable pageable, final User user) {
        return repo.findLikedSongsOntoDescByUser(pageable, user.getId());
    }
}
