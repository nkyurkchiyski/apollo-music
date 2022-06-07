package com.apollo.music.jade.agent.editor;

import com.apollo.music.data.entity.Album;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.jade.entityexecutor.IEntityExecutor;

public class AlbumEditorAgent extends EntityEditorAgent<Album> {
    @Override
    protected Album createEntity() {
        return null;
    }

    @Override
    protected IEntityExecutor<Album> createNewExecutor(final OntologyConfigurator configurator) {
        return null;
    }
}
