package com.apollo.music.views.commons.components;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.data.service.AbstractEntityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.apache.logging.log4j.util.Strings;

import java.util.function.Consumer;

public abstract class EntityManagerGrid<T extends EntityWithId, S extends AbstractEntityService<T>, F> extends Grid<T> {
    private static final long serialVersionUID = 1L;
    private final ConfigurableFilterDataProvider<T, Void, F> dataProvider;
    protected final S entityService;
    private final Consumer<T> editConsumer;

    public EntityManagerGrid(final Class<T> entityClass,
                             final S entityService,
                             final Consumer<T> editConsumer) {
        super(entityClass, false);
        this.entityService = entityService;
        this.editConsumer = editConsumer;

        this.dataProvider = createdDataProvider();
        setItems(dataProvider);
        setSelectionMode(SelectionMode.SINGLE);
        initContent();
    }

    protected abstract ConfigurableFilterDataProvider<T, Void, F> createdDataProvider();

    private void initContent() {
        removeAllColumns();
        addColumn(new ComponentRenderer<>(e -> new Label(e.getId()))).setWidth("22em").setFlexGrow(0).setKey("id").setHeader("Id");
        configureEntityColumns();
        addColumn(new ComponentRenderer<>(this::createActions)).setKey("actions").setHeader("Actions").setFlexGrow(0).setWidth("140px");
        this.addItemClickListener(listener -> {
            if (!listener.getColumn().getKey().equals("actions")) {
                edit(listener.getItem());
            }
        });
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        setHeightFull();
    }

    protected abstract void configureEntityColumns();

    private Component createActions(final T entity) {
        final Button remove = new Button(new Icon(VaadinIcon.TRASH), x ->
        {
            entityService.delete(entity.getId());
            getDataProvider().refreshAll();
        });
        final Button edit = new Button(new Icon(VaadinIcon.EDIT), l -> edit(entity));
        return new HorizontalLayout(edit, remove);
    }

    private void edit(final T entity) {
        editConsumer.accept(entity);
    }

    public void filterGrid(final F filter) {
        dataProvider.setFilter(filter);
        dataProvider.refreshAll();
    }

    protected Component createImage(final String imageUrl) {
        if (Strings.isBlank(imageUrl)) {
            return new Label("N/A");
        }

        final Image img = new Image(imageUrl, "img");
        img.setClassName("img-grid");
        return img;
    }
}
