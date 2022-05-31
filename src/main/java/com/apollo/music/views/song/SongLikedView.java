package com.apollo.music.views.song;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.explore.SongCard;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.RolesAllowed;

@PageTitle("Liked Songs")
@Route(value = "song/liked", layout = MainLayout.class)
@RolesAllowed(EntityConfiguration.USER)
public class SongLikedView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;

    public SongLikedView() {
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
        container.addClassNames("items-center", "justify-between");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("All your likes");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Most recent", "Oldest first");
        sortBy.setValue("Most recent");

        imageContainer = new OrderedList();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");

        container.add(header, sortBy);
        add(container, imageContainer);

    }
}