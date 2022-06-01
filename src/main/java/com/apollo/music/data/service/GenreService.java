package com.apollo.music.data.service;

import com.apollo.music.data.commons.ExampleUtils;
import com.apollo.music.data.entity.Genre;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.repository.GenreRepository;
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
public class GenreService extends AbstractEntityService<Genre> {
    private final GenreRepository repo;

    @Autowired
    public GenreService(final GenreRepository repo) {
        this.repo = repo;
    }

    @Override
    protected JpaRepository<Genre, String> getRepository() {
        return repo;
    }

    public long countByFilter(final ContentManagerFilter filter) {
        final Example<Genre> example = createExample(filter);
        return repo.count(example);
    }

    public Page<Genre> fetchByFilter(final Pageable paging, final ContentManagerFilter filter) {
        final Example<Genre> example = createExample(filter);
        return repo.findAll(example, paging);
    }

    private Example<Genre> createExample(final ContentManagerFilter filter) {
        final ContentManagerFilter filterToUse = ObjectUtils.firstNonNull(filter, new ContentManagerFilter());
        final Genre probe = new Genre();
        probe.setId(filterToUse.getId());
        probe.setName(filterToUse.getName());
        return Example.of(probe, ExampleUtils.CONTENT_MANAGER_EXAMPLE_MATCHER);
    }

    public Stream<Genre> fetchByName(final Pageable paging, final Optional<String> filter) {
        final ContentManagerFilter filterToUse = new ContentManagerFilter(null, filter.orElse(null));
        return fetchByFilter(paging, filterToUse).stream();
    }

    public int countByName(final Optional<String> filter) {
        final ContentManagerFilter filterToUse = new ContentManagerFilter(null, filter.orElse(null));
        return (int) countByFilter(filterToUse);
    }
}
