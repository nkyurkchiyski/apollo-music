package com.apollo.music.jade.agent.editor;

import com.apollo.music.data.entity.Song;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.jade.entityexecutor.IEntityExecutor;

public class SongEditorAgent extends EntityEditorAgent<Song> {
    @Override
    protected Song createEntity() {
        return null;
    }

    @Override
    protected IEntityExecutor<Song> createNewExecutor(final OntologyConfigurator configurator) {
        return null;
    }
}
