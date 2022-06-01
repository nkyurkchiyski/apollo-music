package com.apollo.music.data.service;

import com.apollo.music.data.commons.ExampleUtils;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.repository.SongRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
}
