package com.apollo.music.agent.impl.editor;

import com.apollo.music.data.entity.Song;
import com.apollo.music.ontology.OntologyConfigurator;
import com.apollo.music.ontology.executor.IEntityExecutor;
import com.apollo.music.ontology.executor.SongExecutor;

import java.util.Date;

public class SongEditorAgent extends EntityEditorAgent<Song> {
    @Override
    protected Song createEntity() {
        final Song song = new Song();
        song.setName((String) getFieldValue("name"));
        song.setReleasedOn((Date) getFieldValue("releasedOn"));
        song.setTrackNumber((Integer) getFieldValue("trackNumber"));
        song.setOntoDescriptor((String) getFieldValue("ontoDescriptor"));
        return song;
    }

    @Override
    protected IEntityExecutor<Song> createNewExecutor(final OntologyConfigurator configurator) {
        return new SongExecutor(configurator);
    }
}
