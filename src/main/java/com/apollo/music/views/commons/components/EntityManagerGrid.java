package com.apollo.music.views.commons.components;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.data.service.AbstractEntityService;
import com.apollo.music.jade.AgentManager;
import com.apollo.music.jade.agent.editor.EntityEditorAgent;
import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
    private final boolean viewActionEnabled;

    public EntityManagerGrid(final Class<T> entityClass,
                             final S entityService,
                             final Consumer<T> editConsumer,
                             final boolean viewActionEnabled) {
        super(entityClass, false);
        this.entityService = entityService;
        this.editConsumer = editConsumer;
        this.viewActionEnabled = viewActionEnabled;

        this.dataProvider = createdDataProvider();
        setItems(dataProvider);
        setSelectionMode(SelectionMode.SINGLE);
        initContent();
    }

    public EntityManagerGrid(final Class<T> entityClass,
                             final S entityService,
                             final Consumer<T> editConsumer) {
        this(entityClass, entityService, editConsumer, true);
    }

    protected abstract ConfigurableFilterDataProvider<T, Void, F> createdDataProvider();

    private void initContent() {
        removeAllColumns();
        addColumn(new ComponentRenderer<>(e -> new Label(e.getId()))).setWidth("22em").setFlexGrow(0).setKey("id").setHeader("Id");
        configureEntityColumns();
        addColumn(new ComponentRenderer<>(this::createActions)).setWidth("12em").setFlexGrow(0).setKey("actions").setHeader("Actions");
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
            AgentManager.createNewAgent(entity.getClass().getSimpleName() + "Remover",
                    getEditorAgentClassType(),
                    new Object[]{"remove", entity.toString()});
            getDataProvider().refreshAll();
        });
        final Button edit = new Button(new Icon(VaadinIcon.EDIT), l -> edit(entity));

        final HorizontalLayout layout = new HorizontalLayout();
        if (viewActionEnabled) {
            final Button details = new Button(
                    new Icon(VaadinIcon.EYE),
                    e -> UI.getCurrent().navigate(String.format(ViewConstants.Route.ROUTE_FORMAT, entity.getClass().getSimpleName().toLowerCase(), entity.getId()))
            );
            layout.add(details);
        }
        layout.add(edit, remove);
        return layout;
    }

    protected abstract Class<? extends EntityEditorAgent<T>> getEditorAgentClassType();

    private void edit(final T entity) {
        editConsumer.accept(entity);
    }

    public void filterGrid(final F filter) {
        dataProvider.setFilter(filter);
        dataProvider.refreshAll();
    }

    protected Component createImage(final String imageUrl) {
        final String imgToUse = Strings.isBlank(imageUrl) ? ViewConstants.DEFAULT_COVER : imageUrl;
        final Image img = new Image(imgToUse, "img");
        img.setClassName("img-grid");
        return img;
    }
}
