package com.apollo.music.views.managecontent;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.data.service.AbstractEntityService;
import com.apollo.music.views.commons.ComponentFactory;
import com.apollo.music.views.commons.components.EntityForm;
import com.apollo.music.views.commons.components.EntityManagerGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class AbstractContentManagerView<T extends EntityWithId, S extends AbstractEntityService<T>, F> extends VerticalLayout {
    private static final long serialVersionUID = 1L;
    private final Button resetButton = new Button(new Icon(VaadinIcon.REFRESH));
    private final Button searchButton = new Button(new Icon(VaadinIcon.SEARCH));
    private final FormLayout filterForm = new FormLayout();
    private final Button addEntityButton = new Button(new Icon(VaadinIcon.PLUS));

    protected final S entityService;
    protected EntityManagerGrid<T, S, F> grid;

    public AbstractContentManagerView(final S entityService) {
        this.entityService = entityService;
        grid = createGrid();
        initFilterForm();
        initContent();
    }

    private void initFilterForm() {
        filterForm.setSizeFull();
        final Component[] formComponents = createFilterFormComponents();
        filterForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 3)
        );

        filterForm.setWidth("100%");
        filterForm.add(formComponents);
        searchButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        searchButton.addClickListener(e -> grid.filterGrid(createFilter()));

        resetButton.addClickListener(e -> {
            clearFilterForm();
            grid.filterGrid(createFilter());
        });

        addEntityButton.addClickListener(x -> openEntityForm(null));
        final HorizontalLayout buttonsLayout = new HorizontalLayout(searchButton, resetButton, addEntityButton);
        filterForm.add(buttonsLayout);
    }

    private void initContent() {
        setId(getEntityClass().getSimpleName() + "-view".toLowerCase());
        setSizeFull();

        final VerticalLayout layout = new VerticalLayout(filterForm);
        grid.addItemDoubleClickListener(e -> openEntityForm(e.getItem()));
        add(layout, grid);
    }

    private void clearFilterForm() {
        filterForm.getChildren().filter(c -> c instanceof HasValue).map(HasValue.class::cast).forEach(f -> f.setValue(""));
    }

    protected void openEntityForm(final T entity) {
        final Dialog dialog = ComponentFactory.createDialog();
        final EntityForm<T> form = createEntityForm(entity);
        form.addCancelClickListener(e -> dialog.close());
        form.addSaveClickListener(e ->
        {
            if (form.isSaved() && validate(form.getBean())) {
                final T bean = form.getBean();
                updateEntity(bean);
                form.clearForm();
                dialog.close();
                refreshGrid();
            }
        });
        dialog.add(form);
        dialog.open();
    }

    protected boolean validate(final T bean) {
        return true;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

    private void updateEntity(final T bean) {
        entityService.update(bean);
    }

    protected abstract Class<T> getEntityClass();

    protected abstract EntityForm<T> createEntityForm(final T entity);

    protected abstract Component[] createFilterFormComponents();

    protected abstract F createFilter();

    protected abstract EntityManagerGrid<T, S, F> createGrid();

}
