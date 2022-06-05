package com.apollo.music.data.service;

import com.apollo.music.data.commons.ExampleUtils;
import com.apollo.music.data.entity.Album;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.repository.AlbumRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AlbumService extends AbstractEntityService<Album> {

    private final AlbumRepository repo;

    @Autowired
    public AlbumService(final AlbumRepository repo) {
        this.repo = repo;
    }

    public Page<Album> fetch(final Pageable pageable, final ContentManagerFilter filter) {
        final Example<Album> example = createExample(filter);
        return repo.findAll(example, pageable);
    }

    public long count(final ContentManagerFilter filter) {
        final Example<Album> example = createExample(filter);
        return repo.count(example);
    }

    public Stream<Album> fetchByName(final Pageable pageable, final Optional<String> filter) {
        final ContentManagerFilter filterToUse = new ContentManagerFilter(null, filter.orElse(null));
        return fetch(pageable, filterToUse).stream();
    }

    public int countByName(final Optional<String> filter) {
        final ContentManagerFilter filterToUse = new ContentManagerFilter(null, filter.orElse(null));
        return (int) count(filterToUse);
    }

    @Override
    protected JpaRepository<Album, String> getRepository() {
        return repo;
    }

    private Example<Album> createExample(final ContentManagerFilter filter) {
        final ContentManagerFilter filterToUse = ObjectUtils.firstNonNull(filter, new ContentManagerFilter());
        final Album probe = new Album();
        probe.setId(filterToUse.getId());
        probe.setName(filterToUse.getName());
        return Example.of(probe, ExampleUtils.CONTENT_MANAGER_EXAMPLE_MATCHER);
    }
}
