package com.apollo.music.views.managecontent.artist;

import com.apollo.music.agent.impl.editor.ArtistEditorAgent;
import com.apollo.music.agent.impl.editor.EntityEditorAgent;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.service.ArtistService;
import com.apollo.music.views.commons.components.EntityManagerGrid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.vaadin.artur.spring.dataprovider.SpringDataProviderBuilder;

import java.util.function.Consumer;

public class ArtistManagerGrid extends EntityManagerGrid<Artist, ArtistService, ContentManagerFilter> {
    private static final long serialVersionUID = 1L;

    public ArtistManagerGrid(final ArtistService entityService, final Consumer<Artist> editConsumer) {
        super(Artist.class, entityService, editConsumer);
    }

    @Override
    protected ConfigurableFilterDataProvider<Artist, Void, ContentManagerFilter> createdDataProvider() {
        return SpringDataProviderBuilder.forFunctions(entityService::fetchByFilter, entityService::countByFilter).buildFilterable();
    }

    @Override
    protected void configureEntityColumns() {
        addColumn(new ComponentRenderer<>(e -> createImage(e.getImageUrl()))).setWidth("10em").setFlexGrow(0).setKey("img").setHeader("Image");
        addColumn(new ComponentRenderer<>(entity -> new Label(entity.getName()))).setKey("info").setHeader("Info");
    }

    @Override
    protected Class<? extends EntityEditorAgent<Artist>> getEditorAgentClassType() {
        return ArtistEditorAgent.class;
    }

}
