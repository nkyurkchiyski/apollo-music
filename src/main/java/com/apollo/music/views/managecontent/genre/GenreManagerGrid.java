package com.apollo.music.views.managecontent.genre;

import com.apollo.music.data.entity.Genre;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.service.GenreService;
import com.apollo.music.views.commons.components.EntityManagerGrid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.vaadin.artur.spring.dataprovider.SpringDataProviderBuilder;

import java.util.function.Consumer;

public class GenreManagerGrid extends EntityManagerGrid<Genre, GenreService, ContentManagerFilter> {
    private static final long serialVersionUID = 1L;

    public GenreManagerGrid(final GenreService genreService,
                            final Consumer<Genre> editConsumer) {
        super(Genre.class, genreService, editConsumer);
    }

    @Override
    protected ConfigurableFilterDataProvider<Genre, Void, ContentManagerFilter> createdDataProvider() {
        return SpringDataProviderBuilder.forFunctions(entityService::fetch, entityService::count).buildFilterable();
    }

    @Override
    protected void configureEntityColumns() {
        addColumn(new ComponentRenderer<>(entity -> new Label(entity.getName()))).setKey("info").setHeader("Info");
    }
}
