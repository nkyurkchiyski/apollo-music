package com.apollo.music.agent.impl.editor;

import com.apollo.music.data.entity.Artist;
import com.apollo.music.ontology.OntologyConfigurator;
import com.apollo.music.ontology.executor.ArtistExecutor;
import com.apollo.music.ontology.executor.IEntityExecutor;

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
