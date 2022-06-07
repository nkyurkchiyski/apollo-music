package com.apollo.music.views.managecontent.artist;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.service.ArtistService;
import com.apollo.music.jade.agent.editor.ArtistEditorAgent;
import com.apollo.music.jade.agent.editor.EntityEditorAgent;
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

@PageTitle(ViewConstants.Title.MANAGE_ARTIST)
@Route(value = ViewConstants.Route.MANAGE_ARTIST, layout = MainLayout.class)
@RolesAllowed(EntityConfiguration.ADMIN)
@Uses(Icon.class)
public class ArtistContentManagerView extends AbstractContentManagerView<Artist, ArtistService, ContentManagerFilter> {
    private static final long serialVersionUID = 1L;

    private TextField idField;
    private TextField nameField;

    @Autowired
    public ArtistContentManagerView(final ArtistService artistService) {
        super(artistService);
    }

    @Override
    protected Class<? extends EntityEditorAgent<Artist>> getEditorAgentClassType() {
        return ArtistEditorAgent.class;
    }

    @Override
    protected Class<Artist> getEntityClass() {
        return Artist.class;
    }

    @Override
    protected EntityForm<Artist> createEntityForm(final Artist entity) {
        return new ArtistForm(entity);
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
    protected EntityManagerGrid<Artist, ArtistService, ContentManagerFilter> createGrid() {
        return new ArtistManagerGrid(entityService, this::openEntityForm);
    }
}
