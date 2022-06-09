package com.apollo.music.views.song;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.entity.User;
import com.apollo.music.data.service.PlaylistService;
import com.apollo.music.security.AuthenticatedUser;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.card.SongCardListItem;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle(ViewConstants.Title.LIKED_SONGS)
@Route(value = ViewConstants.Route.LIKES, layout = MainLayout.class)
@RolesAllowed(EntityConfiguration.USER)
public class SongLikesView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;
    private final AuthenticatedUser authenticatedUser;
    private final PlaylistService playlistService;

    @Autowired
    public SongLikesView(final AuthenticatedUser authenticatedUser, final PlaylistService playlistService) {
        this.authenticatedUser = authenticatedUser;
        this.playlistService = playlistService;
        constructUI();
    }

    private void constructUI() {
        addClassNames("explore-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        final H2 header = new H2("All your likes");
        header.addClassNames("mt-xl", "text-3xl");
        add(header);

        imageContainer = new OrderedList();

        if (authenticatedUser.get().isPresent()) {
            final User currentUser = authenticatedUser.get().get();
            final List<Song> likedSongs = playlistService.getLikedSongsByUser(currentUser);
            if (likedSongs.isEmpty()) {
                add(new H4("There are currently no liked songs :("));
            } else {
                likedSongs.forEach(song -> imageContainer.add(new SongCardListItem(song)));
            }
        }
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");
        add(imageContainer);
    }
}