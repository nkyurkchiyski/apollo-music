package com.apollo.music.jade.agent.editor;

import com.apollo.music.data.entity.Song;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.jade.entityexecutor.IEntityExecutor;
import com.apollo.music.jade.entityexecutor.SongExecutor;

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
