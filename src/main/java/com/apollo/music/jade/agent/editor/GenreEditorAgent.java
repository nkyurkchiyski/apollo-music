package com.apollo.music.jade.agent.editor;

import com.apollo.music.data.entity.Genre;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.jade.entityexecutor.GenreEntityExecutor;
import com.apollo.music.jade.entityexecutor.IEntityExecutor;

public class GenreEditorAgent extends EntityEditorAgent<Genre> {
    @Override
    protected Genre createEntity() {
        final String args = (String) getArguments()[1];
        final String name = args.split("=")[1];
        final Genre genre = new Genre();
        genre.setName(name);
        return genre;
    }

    @Override
    protected IEntityExecutor<Genre> createNewExecutor(final OntologyConfigurator configurator) {
        return new GenreEntityExecutor(configurator);
    }
}
