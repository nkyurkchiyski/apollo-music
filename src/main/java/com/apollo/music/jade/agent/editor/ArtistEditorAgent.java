package com.apollo.music.jade.agent.editor;

import com.apollo.music.data.entity.Artist;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.jade.entityexecutor.IEntityExecutor;

public class ArtistEditorAgent extends EntityEditorAgent<Artist> {
    @Override
    protected Artist createEntity() {
        return null;
    }

    @Override
    protected IEntityExecutor<Artist> createNewExecutor(final OntologyConfigurator configurator) {
        return null;
    }
}
