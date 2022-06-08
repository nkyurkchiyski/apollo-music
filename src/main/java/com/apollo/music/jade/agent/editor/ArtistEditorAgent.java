package com.apollo.music.jade.agent.editor;

import com.apollo.music.data.entity.Artist;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.jade.entityexecutor.ArtistExecutor;
import com.apollo.music.jade.entityexecutor.IEntityExecutor;

public class ArtistEditorAgent extends EntityEditorAgent<Artist> {
    @Override
    protected Artist createEntity() {
        final Artist artist = new Artist();
        artist.setName((String) getFieldValue("name"));
        return artist;
    }

    @Override
    protected IEntityExecutor<Artist> createNewExecutor(final OntologyConfigurator configurator) {
        return new ArtistExecutor(configurator);
    }
}
