package com.apollo.music.jade.agent.editor;

import com.apollo.music.data.entity.Album;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.jade.entityexecutor.AlbumExecutor;
import com.apollo.music.jade.entityexecutor.IEntityExecutor;

import java.util.Date;

public class AlbumEditorAgent extends EntityEditorAgent<Album> {
    @Override
    protected Album createEntity() {
        final Album album = new Album();
        album.setName((String) getFieldValue("name"));
        album.setReleasedOn((Date) getFieldValue("releasedOn"));

        final Artist artist = new Artist();
        artist.setName((String) getFieldValue("artist"));
        album.setArtist(artist);
        return album;
    }

    @Override
    protected IEntityExecutor<Album> createNewExecutor(final OntologyConfigurator configurator) {
        return new AlbumExecutor(configurator);
    }
}
