package com.apollo.music.views.details;

import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.service.ArtistService;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.card.AlbumCardListItem;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;

@PageTitle(ViewConstants.Title.ARTIST_DETAILS)
@Route(value = ViewConstants.Route.ARTIST, layout = MainLayout.class)
@AnonymousAllowed
public class ArtistDetailsView extends EntityDetailsView<Artist, ArtistService> {
    protected ArtistDetailsView(final ArtistService entityService) {
        super(entityService);
    }

    @Override
    protected String getSubMainComponentTitle(final Artist entity) {
        return "Albums by " + entity.getName();
    }

    @Override
    protected Component createSubMainComponent(final Artist entity) {
        final OrderedList imageContainer = new OrderedList();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");

        entity.getAlbums().forEach(album -> imageContainer.add(new AlbumCardListItem(album)));
        return imageContainer;
    }

    @Override
    protected Component[] createMoreInfoComponents(final Artist entity) {
        final String genres = entity.getAlbums()
                .stream()
                .flatMap(album -> album.getSongs().stream())
                .map(song -> song.getGenre().getName())
                .distinct()
                .collect(Collectors.joining(", "));
        final Label genreLabel = new Label("Genres: " + (StringUtils.isNotBlank(genres) ? genres : "N/A"));
        genreLabel.addClassNames("mb-0", "mt-0", "text-m");

        return new Component[]{genreLabel};
    }

    @Override
    protected String getSubTitleText(final Artist entity) {
        return "";
    }

    @Override
    protected String getMainTitleText(final Artist entity) {
        return entity.getName();
    }

    @Override
    protected String getImageUrl(final Artist entity) {
        return entity.getImageUrl();
    }
}
