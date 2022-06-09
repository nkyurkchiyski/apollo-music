package com.apollo.music.views.managecontent.song;

import com.apollo.music.agent.impl.editor.EntityEditorAgent;
import com.apollo.music.agent.impl.editor.SongEditorAgent;
import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.filter.ContentManagerFilter;
import com.apollo.music.data.service.AlbumService;
import com.apollo.music.data.service.ArtistService;
import com.apollo.music.data.service.GenreService;
import com.apollo.music.data.service.SongService;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.EntityForm;
import com.apollo.music.views.commons.components.EntityManagerGrid;
import com.apollo.music.views.managecontent.AbstractContentManagerView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@PageTitle(ViewConstants.Title.MANAGE_SONG)
@Route(value = ViewConstants.Route.MANAGE_SONG, layout = MainLayout.class)
@RolesAllowed(EntityConfiguration.ADMIN)
@Uses(Icon.class)
public class SongContentManagerView extends AbstractContentManagerView<Song, SongService, ContentManagerFilter> {
    private static final long serialVersionUID = 1L;

    private TextField idField;
    private TextField nameField;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final GenreService genreService;

    @Autowired
    public SongContentManagerView(final SongService entityService,
                                  final ArtistService artistService,
                                  final AlbumService albumService,
                                  final GenreService genreService) {
        super(entityService);
        this.artistService = artistService;
        this.albumService = albumService;
        this.genreService = genreService;
    }

    @Override
    protected Class<Song> getEntityClass() {
        return Song.class;
    }

    @Override
    protected EntityForm<Song> createEntityForm(final Song entity) {
        return new SongForm(entity, artistService, albumService, genreService);
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
    protected EntityManagerGrid<Song, SongService, ContentManagerFilter> createGrid() {
        return new SongManagerGrid(entityService, this::openEntityForm);
    }

    @Override
    protected Class<? extends EntityEditorAgent<Song>> getEditorAgentClassType() {
        return SongEditorAgent.class;
    }

    @Override
    protected boolean validate(final Song bean) {
        final boolean isValid = !entityService.existsWithOntoDesc(bean);
        if (!isValid) {
            Notification.show(ViewConstants.Validation.SONG_WITH_ONTO_DESC_EXISTS);
        }
        return isValid;
    }
}
