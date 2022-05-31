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

import javax.transaction.Transactional;

@Service
public class AlbumService extends AbstractEntityService<Album> {

    private final AlbumRepository repo;

    @Autowired
    public AlbumService(final AlbumRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Page<Album> fetch(final Pageable pageable, final ContentManagerFilter filter) {
        final Example<Album> example = createExample(filter);
        final Page<Album> result = repo.findAll(example, pageable);
        result.stream().forEach(Album::getArtist);
        return result;
    }

    public long count(final ContentManagerFilter filter) {
        final Example<Album> example = createExample(filter);
        return repo.count(example);
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
