package com.apollo.music.agent.impl.editor;

import com.apollo.music.data.entity.Genre;
import com.apollo.music.ontology.OntologyConfigurator;
import com.apollo.music.ontology.executor.GenreExecutor;
import com.apollo.music.ontology.executor.IEntityExecutor;

public class GenreEditorAgent extends EntityEditorAgent<Genre> {
    @Override
    protected Genre createEntity() {
        final Genre genre = new Genre();
        genre.setName((String) getFieldValue("name"));
        return genre;
    }

    @Override
    protected IEntityExecutor<Genre> createNewExecutor(final OntologyConfigurator configurator) {
        return new GenreExecutor(configurator);
    }
}
