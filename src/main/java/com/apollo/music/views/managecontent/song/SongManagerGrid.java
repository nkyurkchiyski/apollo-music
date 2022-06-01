package com.apollo.music.views.managecontent.song;

import com.apollo.music.data.entity.Album;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.service.SongService;
import com.apollo.music.views.commons.components.EntityManagerGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.vaadin.artur.spring.dataprovider.SpringDataProviderBuilder;

import java.util.function.Consumer;

public class SongManagerGrid extends EntityManagerGrid<Song, SongService, ContentManagerFilter> {
    public SongManagerGrid(final SongService entityService, final Consumer<Song> editConsumer) {
        super(Song.class, entityService, editConsumer);
    }

    //TODO: switch to song specific filter containing genre, artist and album
    @Override
    protected ConfigurableFilterDataProvider<Song, Void, ContentManagerFilter> createdDataProvider() {
        return SpringDataProviderBuilder.forFunctions(entityService::fetch, entityService::count).buildFilterable();
    }

    @Override
    protected void configureEntityColumns() {
        addColumn(new ComponentRenderer<>(this::createSongInfo)).setKey("info").setHeader("Info");
    }

    private Component createSongInfo(final Song song) {
        final StringBuilder sb = new StringBuilder();
        sb.append(song.getName());
        final Album album = song.getAlbum();
        if (album != null) {
            sb.append(" from " + album.getName());
            if (album.getArtist() != null) {
                sb.append(" by " + album.getArtist().getName());
            }
        }
        return new Label(sb.toString());
    }
}
