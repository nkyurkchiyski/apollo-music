package com.apollo.music.views.song;

import com.apollo.music.views.MainLayout;
import com.apollo.music.views.explore.SongCard;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Song Details")
@Route(value = "song/details", layout = MainLayout.class)
@AnonymousAllowed
public class SongDetailsView extends Main implements HasComponents, HasStyle {


    private OrderedList imageContainer;

    public SongDetailsView() {
        constructUI();

        imageContainer.add(new SongCard("Lay Wit Ya", "Isaiah Rashad",
                "https://i.ytimg.com/vi/dzrQCsJzr70/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG&rs=AOn4CLCS6I9mpHFOEzXlElDqlhGYdG-AHA"));
        imageContainer.add(new SongCard("way back", "Travis Scott",
                "https://i.ytimg.com/vi/1kgw0vubj9U/hq720.jpg?sqp=-oaymwEcCOgCEMoBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLAncGKlPWVnt8qUCgQMi37yR-Puiw"));
        imageContainer.add(new SongCard("Ocean Eyes", "Billie Eilish",
                "https://i.ytimg.com/an_webp/viimfQi_pUw/mqdefault_6s.webp?du=3000&sqp=CNT1qJEG&rs=AOn4CLCP16iZ3Mw6OJzhJL9Og315CO24qQ"));
        imageContainer.add(new SongCard("Thriller", "Michael Jackson",
                "https://i.ytimg.com/an_webp/sOnqjkJTMaA/mqdefault_6s.webp?du=3000&sqp=CIC9qJEG&rs=AOn4CLDIzS55tQbQdRY3y9PQyWzBjtF-KQ"));
        imageContainer.add(new SongCard("Nightcrawler", "Travis Scott",
                "https://i.ytimg.com/vi/rNr6X0_vmWM/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG&rs=AOn4CLDJAyP1a10hiHIjHqXqzE4qeLj3oQ"));
        imageContainer.add(new SongCard("Fade to Black", "Metallica",
                "https://i.ytimg.com/vi/HdWw9SksiwQ/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG&rs=AOn4CLCmKyD-XkM7RuyISAQtLi_HXE_8Cg"));
        imageContainer.add(new SongCard("Low Life", "Future",
                "https://i.ytimg.com/vi/K_9tX4eHztY/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG&rs=AOn4CLAwodJymLErzAfXsmRRpVFKQWr89g"));

    }

    private void constructUI() {
        addClassNames("explore-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        HorizontalLayout container = new HorizontalLayout();
        VerticalLayout songInfo = new VerticalLayout();
        H2 songName = new H2("Low Life");
        songName.addClassNames("mb-0", "mt-l", "text-2xl");

        H2 performer = new H2("Future");
        performer.addClassNames("mb-0", "mt-0", "text-l");

        H2 moreInfo = new H2("More Information");
        moreInfo.addClassNames("mb-0", "mt-0", "text-m");

        Label composer = new Label("Composer: DaHeala, The Weeknd");
        composer.addClassNames("mb-0", "mt-0", "text-m");

        Label album = new Label("Album: EVOL");
        album.addClassNames("mb-0", "mt-0", "text-m");

        Label genre = new Label("Genre: Hip Hop, Rap, Trap");
        genre.addClassNames("mb-0", "mt-0", "text-m");

        Div div = new Div();
        Image image = new Image();
        image.setSrc("https://townsquare.media/site/625/files/2016/03/The-Weekend-Future-Low-Life.jpg");
        image.addClassNames("bg-contrast", "flex items-center", "justify-center", "mt-m", "mb-m", "overflow-hidden",
                "rounded-m w-full");
        image.setMaxHeight("350px");
        div.add(image);
        div.addClassName("pos-r");

        songInfo.add(songName, performer, moreInfo, composer, album, genre);
        container.setWidthFull();
        container.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        H2 similarSongs = new H2("Similar Songs");
        similarSongs.addClassNames("mb-m", "mt-m", "text-2xl");

        Button playButton = new Button(new Icon(VaadinIcon.PLAY));
        playButton.addClassNames("play-button-big","pos-abr");
        playButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        div.add(playButton);
        imageContainer = new OrderedList();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");
        container.add(songInfo, div);
        add(container, similarSongs, imageContainer);

    }
}