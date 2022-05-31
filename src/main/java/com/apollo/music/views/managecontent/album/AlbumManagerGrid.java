package com.apollo.music.views.managecontent.album;

import com.apollo.music.data.entity.Album;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.service.AlbumService;
import com.apollo.music.views.commons.components.EntityManagerGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.vaadin.artur.spring.dataprovider.SpringDataProviderBuilder;

import java.util.function.Consumer;

public class AlbumManagerGrid extends EntityManagerGrid<Album, AlbumService, ContentManagerFilter> {
    private static final long serialVersionUID = 1L;

    public AlbumManagerGrid(final AlbumService entityService, final Consumer<Album> editConsumer) {
        super(Album.class, entityService, editConsumer);
    }

    @Override
    protected ConfigurableFilterDataProvider<Album, Void, ContentManagerFilter> createdDataProvider() {
        return SpringDataProviderBuilder.forFunctions(entityService::fetch, entityService::count).buildFilterable();
    }

    @Override
    protected void configureEntityColumns() {
        addColumn(new ComponentRenderer<>(e -> createImage(e.getImageUrl()))).setWidth("10em").setFlexGrow(0).setKey("img").setHeader("Image");
        addColumn(new ComponentRenderer<>(this::createAlbumInfo)).setKey("info").setHeader("Info");
    }

    private Component createAlbumInfo(final Album album) {
        final StringBuilder sb = new StringBuilder();
        sb.append(album.getName());
        if (album.getArtist() != null) {
            sb.append(" by " + album.getArtist().getName());
        }
        return new Label(sb.toString());
    }
}
