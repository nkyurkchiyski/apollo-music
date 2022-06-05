package com.apollo.music.data.service;

import com.apollo.music.data.commons.ExampleUtils;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.repository.SongRepository;
import com.apollo.music.views.search.SearchFilter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class SongService extends AbstractEntityService<Song> {
    private final SongRepository repo;

    @Autowired
    public SongService(final SongRepository repo) {
        this.repo = repo;
    }


    @Override
    protected JpaRepository<Song, String> getRepository() {
        return repo;
    }

    public Stream<Song> getAllByOntoHash(final Pageable pageable, final List<String> songsOntoHash) {
        return repo.findAllByOntoHash(pageable, songsOntoHash).stream();
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
}
