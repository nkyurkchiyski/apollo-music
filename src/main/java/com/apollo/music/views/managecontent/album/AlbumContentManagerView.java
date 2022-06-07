package com.apollo.music.views.managecontent.album;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.entity.Album;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.service.AlbumService;
import com.apollo.music.data.service.ArtistService;
import com.apollo.music.jade.agent.editor.AlbumEditorAgent;
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

@PageTitle(ViewConstants.Title.MANAGE_ALBUM)
@Route(value = ViewConstants.Route.MANAGE_ALBUM, layout = MainLayout.class)
@RolesAllowed(EntityConfiguration.ADMIN)
@Uses(Icon.class)
public class AlbumContentManagerView extends AbstractContentManagerView<Album, AlbumService, ContentManagerFilter> {
    private static final long serialVersionUID = 1L;

    private TextField idField;
    private TextField nameField;
    private final ArtistService artistService;

    @Autowired
    public AlbumContentManagerView(final AlbumService entityService, final ArtistService artistService) {
        super(entityService);
        this.artistService = artistService;
    }

    @Override
    protected Class<? extends EntityEditorAgent<Album>> getEditorAgentClassType() {
        return AlbumEditorAgent.class;
    }

    @Override
    protected Class<Album> getEntityClass() {
        return Album.class;
    }

    @Override
    protected EntityForm<Album> createEntityForm(final Album entity) {
        return new AlbumForm(entity, artistService);
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
    protected EntityManagerGrid<Album, AlbumService, ContentManagerFilter> createGrid() {
        return new AlbumManagerGrid(entityService, this::openEntityForm);
    }
}
