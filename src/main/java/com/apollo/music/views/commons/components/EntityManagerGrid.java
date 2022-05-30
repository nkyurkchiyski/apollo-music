package com.apollo.music.views.commons.components;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.data.service.AbstractEntityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;

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
        addColumn(new ComponentRenderer<>(e -> new Label(e.getId()))).setKey("id").setHeader("Id");
        addColumn(new ComponentRenderer<>(this::createEntityInfo)).setKey("info").setHeader("Info");
        addColumn(new ComponentRenderer<>(this::createActions)).setKey("actions").setHeader("Actions").setFlexGrow(0).setWidth("140px");
        this.addItemClickListener(listener -> {
            if (!listener.getColumn().getKey().equals("actions")) {
                edit(listener.getItem());
            }
        });
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        setHeightFull();
    }

    protected abstract Component createEntityInfo(final T entity);

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
}
