package com.apollo.music.views.explore;

import com.apollo.music.data.entity.Song;
import com.apollo.music.data.service.SongService;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.card.SongCardListItem;
import com.apollo.music.views.commons.components.carousel.CarouselList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.function.Supplier;


@PageTitle(ViewConstants.Title.EXPLORE)
@Route(value = ViewConstants.Route.EXPLORE, layout = MainLayout.class)
@AnonymousAllowed
public class ExploreView extends Main implements HasComponents, HasStyle {
    private final SongService songService;

    public ExploreView(final SongService songService) {
        this.songService = songService;
        constructUI();
    }

    private void constructUI() {
        addClassNames("explore-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        createHeader();
        final PageRequest pageRequest = PageRequest.of(0, 15);
        final SongCardListItem[] likedSongsListItem = getSongListItems(() -> songService.getAllByLikes(pageRequest));
        final SongCardListItem[] releasedSongsListItem = getSongListItems(() -> songService.getAllByReleaseDate(pageRequest));

        //TODO add recommendation from agent
//        final List<SongCardListItem> releasedSongsListItem = getSongs(() -> songService.getAllByOntoHash(pageRequest));
//        createCarouselList("Recommended for you", );

        createCarouselList("Most liked", likedSongsListItem);
        createCarouselList("New releases", releasedSongsListItem);

    }

    private SongCardListItem[] getSongListItems(final Supplier<Page<Song>> songs) {
        return songs.get().stream().map(SongCardListItem::new).toArray(SongCardListItem[]::new);
    }

    private void createCarouselList(final String title, final Component... components) {
        final VerticalLayout layout = new VerticalLayout();
        final H3 header = new H3(title);
        header.addClassNames("mb-2");
        final CarouselList carouselList = new CarouselList(5, components);
        layout.add(header, carouselList);
        add(layout);
    }

    private void createHeader() {
        final HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("items-center", "justify-between");

        final H2 header = new H2("Browse Our Library");
        header.addClassNames("mb-2", "mt-xl", "text-3xl");

        container.add(header);
        add(container);
    }
}