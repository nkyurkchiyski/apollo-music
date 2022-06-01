package com.apollo.music.views.details;

import com.apollo.music.data.entity.Album;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.service.SongService;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.card.SongCardListItem;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

@PageTitle(ViewConstants.Title.SONG_DETAILS)
@Route(value = ViewConstants.Route.SONG, layout = MainLayout.class)
@AnonymousAllowed
public class SongDetailsView extends EntityDetailsView<Song, SongService> {

    @Autowired
    public SongDetailsView(final SongService songService) {
        super(songService);
    }

    @Override
    protected String getSubMainComponentTitle(final Song entity) {
        return "Similar Songs to " + entity.getName();
    }

    @Override
    protected Component createSubMainComponent(final Song entity) {
        final OrderedList imageContainer = new OrderedList();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");

        //TODO: use suggestion service when ready
        entityService.list(PageRequest.of(0, 10)).stream().forEach(song -> imageContainer.add(new SongCardListItem(song)));
        return imageContainer;
    }

    @Override
    protected Component[] createMoreInfoComponents(final Song entity) {
        final Album album = entity.getAlbum();
        final Label albumLabel = new Label("Album: " + album.getName());
        albumLabel.addClassNames("mb-0", "mt-0", "text-m");

        final Label genreLabel = new Label("Genre: " + entity.getGenre().getName());
        genreLabel.addClassNames("mb-0", "mt-0", "text-m");

        final Date releaseDate = entity.getReleasedOn();
        final String releaseDateText = releaseDate != null ? ViewConstants.DATE_FORMAT.format(releaseDate) : "N/A";
        final Label releaseDateLabel = new Label("Released On: " + releaseDateText);
        releaseDateLabel.addClassNames("mb-0", "mt-0", "text-m");
        return new Component[]{albumLabel, genreLabel, releaseDateLabel};
    }

    @Override
    protected String getSubTitleText(final Song entity) {
        return entity.getAlbum().getName();
    }

    @Override
    protected String getMainTitleText(final Song entity) {
        return entity.getName();
    }

    @Override
    protected String getImageUrl(final Song entity) {
        return entity.getAlbum().getImageUrl();
    }

    @Override
    protected boolean isPlayButtonVisible() {
        return true;
    }
}