package com.apollo.music.data.repository;

import com.apollo.music.data.entity.SongPlaylist;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class ExtendedPlaylistRepositoryImpl implements ExtendedPlaylistRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void deleteSongPlaylist(final SongPlaylist songPlaylist) {
        em.remove(em.contains(songPlaylist) ? songPlaylist : em.merge(songPlaylist));
    }
}
