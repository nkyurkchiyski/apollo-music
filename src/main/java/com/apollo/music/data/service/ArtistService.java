package com.apollo.music.data.service;

import com.apollo.music.data.commons.ExampleUtils;
import com.apollo.music.data.entity.Album;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.repository.ArtistRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

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

    public Page<Artist> fetchByFilter(final Pageable pageable, final ContentManagerFilter filter) {
        final Example<Artist> example = createExample(filter);
        return repo.findAll(example, pageable);
    }

    public long countByFilter(final ContentManagerFilter filter) {
        final Example<Artist> example = createExample(filter);
        return repo.count(example);
    }

    private Example<Artist> createExample(final ContentManagerFilter filter) {
        final ContentManagerFilter filterToUse = ObjectUtils.firstNonNull(filter, new ContentManagerFilter());
        final Artist probe = new Artist();
        probe.setId(filterToUse.getId());
        probe.setName(filterToUse.getName());
        return Example.of(probe, ExampleUtils.CONTENT_MANAGER_EXAMPLE_MATCHER);
    }

    public Stream<Artist> fetchByName(final Pageable pageable, final Optional<String> filter) {
        final ContentManagerFilter filterToUse = new ContentManagerFilter(null, filter.orElse(null));
        return fetchByFilter(pageable, filterToUse).stream();
    }

    public int countByName(final Optional<String> filter) {
        final ContentManagerFilter filterToUse = new ContentManagerFilter(null, filter.orElse(null));
        return (int) countByFilter(filterToUse);
    }

    @Override
    public Artist update(final Artist entity) {
        if (Strings.isBlank(entity.getId())) {
            final Album album = new Album();
            album.setName("Singles");
            album.setArtist(entity);
            entity.setAlbums(Collections.singletonList(album));
        }
        return super.update(entity);
    }
}
