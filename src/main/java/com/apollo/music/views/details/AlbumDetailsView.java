package com.apollo.music.views.details;

import com.apollo.music.data.entity.Album;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.service.AlbumService;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Comparator;
import java.util.Date;

@PageTitle(ViewConstants.Title.ALBUM_DETAILS)
@Route(value = ViewConstants.Route.ALBUM, layout = MainLayout.class)
@AnonymousAllowed
public class AlbumDetailsView extends EntityDetailsView<Album, AlbumService> {
    protected AlbumDetailsView(final AlbumService entityService) {
        super(entityService);
    }

    @Override
    protected String getSubMainComponentTitle(final Album entity) {
        return "Songs in " + entity.getName();
    }

    @Override
    protected Component createSubMainComponent(final Album entity) {
        final Grid<Song> songGrid = new Grid<>();
        songGrid.setItems(entity.getSongs().stream().sorted(Comparator.comparing(Song::getTrackNumber)).toArray(Song[]::new));
        songGrid.removeAllColumns();
        songGrid.addColumn(new ComponentRenderer<>(e -> new Label(e.getTrackNumber().toString())))
                .setWidth("5em")
                .setFlexGrow(0)
                .setHeader("#");
        songGrid.addColumn(new ComponentRenderer<>(e -> new Label(e.getName())))
                .setHeader("Title");
        songGrid.addColumn(new ComponentRenderer<>(e -> new Label(e.getLikesCount().toString())))
                .setWidth("5em")
                .setFlexGrow(0)
                .setHeader("Likes");
        songGrid.addColumn(new ComponentRenderer<>(e -> new Label(e.getPlayedCount().toString())))
                .setWidth("5em")
                .setFlexGrow(0)
                .setHeader("Plays");
        songGrid.addColumn(new ComponentRenderer<>(this::createViewButton))
                .setWidth("5em")
                .setFlexGrow(0);
        return songGrid;
    }

    private Button createViewButton(final Song song) {
        return new Button(
                new Icon(VaadinIcon.EYE),
                e -> UI.getCurrent().navigate(String.format(ViewConstants.Route.ROUTE_FORMAT, song.getClass().getSimpleName().toLowerCase(), song.getId()))
        );
    }

    @Override
    protected Component[] createMoreInfoComponents(final Album entity) {
        final Date releaseDate = entity.getReleasedOn();
        final String releaseDateText = releaseDate != null ? ViewConstants.DATE_FORMAT.format(releaseDate) : "N/A";
        final Label releaseDateLabel = new Label("Released On: " + releaseDateText);
        releaseDateLabel.addClassNames("mb-0", "mt-0", "text-m");
        return new Component[]{releaseDateLabel};
    }

    @Override
    protected Component createSubTitle(final Album entity) {
        final Artist artist = entity.getArtist();
        return new Anchor(String.format(ViewConstants.Route.ROUTE_FORMAT, ViewConstants.Route.ARTIST, artist.getId()), artist.getName());
    }

    @Override
    protected String getMainTitleText(final Album entity) {
        return entity.getName();
    }

    @Override
    protected String getImageUrl(final Album entity) {
        return entity.getImageUrl();
    }
}
