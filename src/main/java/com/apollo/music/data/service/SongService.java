package com.apollo.music.data.service;

import com.apollo.music.data.commons.ExampleUtils;
import com.apollo.music.data.entity.AbstractEntity;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.repository.SongRepository;
import com.apollo.music.views.search.SearchFilter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService extends AbstractEntityService<Song> {
    private final SongRepository repo;
    private final SongPlaylistService songPlaylistService;


    @Autowired
    public SongService(final SongRepository repo,
                       final SongPlaylistService songPlaylistService) {
        this.repo = repo;
        this.songPlaylistService = songPlaylistService;
    }


    @Override
    protected JpaRepository<Song, String> getRepository() {
        return repo;
    }

    public Page<Song> getAllByOntoDesc(final Pageable pageable, final List<String> songsOntoDesc) {
        final Page<Song> result = repo.findAllByOntoDesc(pageable, songsOntoDesc);
        if (result.getTotalElements() == pageable.getPageSize()) {
            return result;
        }

        if (result.isEmpty()) {
            return repo.findAllByPlays(pageable);
        }

        final List<Song> recommendedSongs = result.stream().collect(Collectors.toList());
        final int additionalSongsCount = pageable.getPageSize() - recommendedSongs.size();
        final List<String> recommendedSongIds = recommendedSongs.stream().map(AbstractEntity::getId).collect(Collectors.toList());
        repo.findAllNotWithId(PageRequest.of(0, additionalSongsCount), recommendedSongIds).forEach(recommendedSongs::add);
        return new PageImpl<>(recommendedSongs);
    }

    public Page<Song> fetch(final Pageable pageable, final ContentManagerFilter filter) {
        final Example<Song> example = createExample(filter);
        return repo.findAll(example, pageable);
    }

    public long count(final ContentManagerFilter filter) {
        final Example<Song> example = createExample(filter);
        return repo.count(example);
    }

    private Example<Song> createExample(final ContentManagerFilter filter) {
        final ContentManagerFilter filterToUse = ObjectUtils.firstNonNull(filter, new ContentManagerFilter());
        final Song probe = new Song();
        probe.setId(filterToUse.getId());
        probe.setName(filterToUse.getName());
        return Example.of(probe, ExampleUtils.CONTENT_MANAGER_EXAMPLE_MATCHER);
    }

    public void play(final Song song) {
        song.incrementPlayedCount();
        update(song);
    }

    public void like(final Song song) {
        song.incrementLikesCount();
        update(song);
    }

    public void dislike(final Song song) {
        song.decrementLikesCount();
        update(song);
    }

    public Page<Song> getAllBySearchFilter(final PageRequest paging, final SearchFilter filter) {
        return repo.findAllBySearchFilter(paging, filter.getSong(), filter.getArtist(), filter.getAlbum(), filter.getGenre());
    }

    public Page<Song> getAllByReleaseDate(final Pageable pageable) {
        return repo.findAllByReleaseDate(pageable);
    }

    public Page<Song> getAllByLikes(final Pageable pageable) {
        return repo.findAllByLikes(pageable);
    }

    public boolean existsWithOntoDesc(final Song song) {
        return repo.existsWithOntoDesc(song.getId(), Song.createOntoDescriptor(song));
    }

    @Transactional
    @Override
    public void delete(final String s) {
        songPlaylistService.deleteAllWithSongs(Collections.singleton(s));
        super.delete(s);
    }

    @Override
    public Song update(final Song entity) {
        entity.setOntoDescriptor(Song.createOntoDescriptor(entity));
        return super.update(entity);
    }
}
