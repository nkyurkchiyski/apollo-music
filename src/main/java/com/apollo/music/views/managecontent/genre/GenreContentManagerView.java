package com.apollo.music.views.managecontent.genre;

import com.apollo.music.agent.impl.editor.EntityEditorAgent;
import com.apollo.music.agent.impl.editor.GenreEditorAgent;
import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.entity.Genre;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.service.GenreService;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.EntityForm;
import com.apollo.music.views.commons.components.EntityManagerGrid;
import com.apollo.music.views.managecontent.AbstractContentManagerView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;


@PageTitle(ViewConstants.Title.MANAGE_GENRE)
@Route(value = ViewConstants.Route.MANAGE_GENRE, layout = MainLayout.class)
@RolesAllowed(EntityConfiguration.ADMIN)
@Uses(Icon.class)
public class GenreContentManagerView extends AbstractContentManagerView<Genre, GenreService, ContentManagerFilter> {
    private static final long serialVersionUID = 1L;

    private TextField idField;
    private TextField nameField;

    @Autowired
    public GenreContentManagerView(final GenreService genreService) {
        super(genreService);
    }

    @Override
    protected Class<? extends EntityEditorAgent<Genre>> getEditorAgentClassType() {
        return GenreEditorAgent.class;
    }

    @Override
    protected Class<Genre> getEntityClass() {
        return Genre.class;
    }

    @Override
    protected EntityForm<Genre> createEntityForm(final Genre entity) {
        return new GenreForm(entity);
    }

    @Override
    protected Component[] createFilterFormComponents() {
        idField = new TextField("Id");
        nameField = new TextField("Name");
        return new Component[]{idField, nameField};
    }

    @Override
    protected ContentManagerFilter createFilter() {
        return new ContentManagerFilter(idField.getValue(), nameField.getValue());
    }

    @Override
    protected EntityManagerGrid<Genre, GenreService, ContentManagerFilter> createGrid() {
        return new GenreManagerGrid(entityService, this::openEntityForm);
    }
}
