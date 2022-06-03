package com.apollo.music.views.details;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.commons.GeneralServiceException;
import com.apollo.music.data.commons.GeneralUtils;
import com.apollo.music.data.entity.Album;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.entity.Playlist;
import com.apollo.music.data.entity.Role;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.entity.User;
import com.apollo.music.data.service.LikeActionResult;
import com.apollo.music.data.service.PlaylistService;
import com.apollo.music.data.service.SongService;
import com.apollo.music.security.AuthenticatedUser;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ComponentFactory;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.DefaultForm;
import com.apollo.music.views.commons.components.card.SongCardListItem;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.Objects;
import java.util.function.Consumer;

@PageTitle(ViewConstants.Title.SONG_DETAILS)
@Route(value = ViewConstants.Route.SONG, layout = MainLayout.class)
@AnonymousAllowed
public class SongDetailsView extends EntityDetailsView<Song, SongService> {
    private final AuthenticatedUser authenticatedUser;
    private final PlaylistService playlistService;

    @Autowired
    public SongDetailsView(final SongService songService,
                           final AuthenticatedUser authenticatedUser,
                           final PlaylistService playlistService) {
        super(songService);
        this.authenticatedUser = authenticatedUser;
        this.playlistService = playlistService;
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
        final Anchor anchor = new Anchor(String.format(ViewConstants.Route.ROUTE_FORMAT, ViewConstants.Route.ALBUM, album.getId()), album.getName());
        final Span albumLabel = new Span(new Label("Album: "), anchor);
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
    protected Component createSubTitle(final Song entity) {
        final Artist artist = entity.getAlbum().getArtist();
        return new Anchor(String.format(ViewConstants.Route.ROUTE_FORMAT, ViewConstants.Route.ARTIST, artist.getId()), artist.getName());
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

    @Override
    protected Component[] createTitleComponents(final Song entity) {
        if (authenticatedUser.get().isPresent()) {
            final User user = authenticatedUser.get().get();
            final Playlist likedSongsPlaylist = getLikedSongsPlaylist(user);

            final VaadinIcon likeIcon = isSongAlreadyLiked(entity, likedSongsPlaylist) ? VaadinIcon.THUMBS_DOWN : VaadinIcon.THUMBS_UP;
            final Button addToPlaylistButton = new Button(new Icon(VaadinIcon.PLUS));
            final Button likeButton = new Button(new Icon(likeIcon));
            likeButton.addThemeVariants(ButtonVariant.LUMO_ICON);

            likeButton.addClickListener(event -> performLikeOperation(entity, user, likeButton));

            addToPlaylistButton.addClickListener(event -> openPlaylistSelectionDialog(entity, user));
            return new Component[]{likeButton, addToPlaylistButton};
        }
        return super.createTitleComponents(entity);
    }

    private Playlist getLikedSongsPlaylist(final User user) {
        return user.getPlaylists()
                .stream()
                .filter(pl -> pl.getName().equals(EntityConfiguration.LIKED_SONGS) && pl.getCreatedBy().equals(Role.SYSTEM))
                .findFirst()
                .orElse(null);
    }

    private void performLikeOperation(final Song entity, final User user, final Button likeButton) {
        final LikeActionResult result = playlistService.likeSong(user, entity);
        likeButton.setIcon(new Icon(result.getOppositeIcon()));
        Notification.show(String.format(ViewConstants.Notification.SONG_LIKED_FORMAT, result.getActionText()));
    }

    private void openPlaylistSelectionDialog(final Song entity, final User user) {
        final Dialog dialog = ComponentFactory.createDialog();
        final ComboBox<Playlist> playlistCombo = new ComboBox<>("Playlist");
        playlistCombo.setItemLabelGenerator(Playlist::getName);
        playlistCombo.setItems((query) -> playlistService.fetchByUserAndName(PageRequest.of(query.getPage(), query.getPageSize()), user,
                query.getFilter()),
                (query) -> playlistService.countByUserAndName(user, query.getFilter()));
        final Component[] formComponents = new Component[]{playlistCombo};
        final DefaultForm form = new DefaultForm(formComponents,
                e -> GeneralUtils.performConditionalConsumer(
                        playlistCombo.getValue(),
                        Objects::nonNull,
                        playlist -> performAddToPlaylistOperation(playlist, entity, song -> dialog.close())
                ),
                e -> dialog.close());
        dialog.add(form);
        dialog.open();
    }

    private boolean isSongAlreadyLiked(final Song entity, final Playlist likedSongsPlaylist) {
        return likedSongsPlaylist != null && likedSongsPlaylist.getSongs().stream().anyMatch(sp -> sp.getSong().equals(entity));
    }

    private void performAddToPlaylistOperation(final Playlist playlist,
                                               final Song song,
                                               final Consumer<Song> afterActionConsumer) {
        try {
            playlistService.addSongToPlaylist(playlist, song);
            Notification.show(ViewConstants.Notification.ADDED_TO_PLAYLIST);
            afterActionConsumer.accept(song);
        } catch (final GeneralServiceException ex) {
            Notification.show(ex.getMessage());
        }
    }

    @Override
    protected void onPlayButtonClicked(final Song entity) {
        entityService.play(entity);
    }
}