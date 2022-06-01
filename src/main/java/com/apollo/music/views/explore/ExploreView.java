package com.apollo.music.views.explore;

import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.components.card.CardListItem;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Explore")
@Route(value = "explore", layout = MainLayout.class)
@AnonymousAllowed
public class ExploreView extends Main implements HasComponents, HasStyle {

    private OrderedList imageContainer;

    public ExploreView() {
        constructUI();

        imageContainer.add(new CardListItem("Lay Wit Ya", "Isaiah Rashad",
                "https://i.ytimg.com/vi/dzrQCsJzr70/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG&rs=AOn4CLCS6I9mpHFOEzXlElDqlhGYdG-AHA"));
        imageContainer.add(new CardListItem("way back", "Travis Scott",
                "https://i.ytimg.com/vi/1kgw0vubj9U/hq720.jpg?sqp=-oaymwEcCOgCEMoBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLAncGKlPWVnt8qUCgQMi37yR-Puiw"));
        imageContainer.add(new CardListItem("Ocean Eyes", "Billie Eilish",
                "https://i.ytimg.com/an_webp/viimfQi_pUw/mqdefault_6s.webp?du=3000&sqp=CNT1qJEG&rs=AOn4CLCP16iZ3Mw6OJzhJL9Og315CO24qQ"));
        imageContainer.add(new CardListItem("Thriller", "Michael Jackson",
                "https://i.ytimg.com/an_webp/sOnqjkJTMaA/mqdefault_6s.webp?du=3000&sqp=CIC9qJEG&rs=AOn4CLDIzS55tQbQdRY3y9PQyWzBjtF-KQ"));
        imageContainer.add(new CardListItem("Nightcrawler", "Travis Scott",
                "https://i.ytimg.com/vi/rNr6X0_vmWM/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG&rs=AOn4CLDJAyP1a10hiHIjHqXqzE4qeLj3oQ"));
        imageContainer.add(new CardListItem("Fade to Black", "Metallica",
                "https://i.ytimg.com/vi/HdWw9SksiwQ/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG&rs=AOn4CLCmKyD-XkM7RuyISAQtLi_HXE_8Cg"));
        imageContainer.add(new CardListItem("Low Life", "Future",
                "https://i.ytimg.com/vi/K_9tX4eHztY/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG&rs=AOn4CLAwodJymLErzAfXsmRRpVFKQWr89g"));

    }

    private void constructUI() {
        addClassNames("explore-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("items-center", "justify-between");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Browse Our Library");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        Paragraph description = new Paragraph("Royalty free photos and pictures, courtesy of Unsplash");
        description.addClassNames("mb-xl", "mt-0", "text-secondary");
        headerContainer.add(header, description);

        Select<String> sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setItems("Popularity", "Newest first", "Oldest first");
        sortBy.setValue("Popularity");

        imageContainer = new OrderedList();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");

        container.add(header, sortBy);
        add(container, imageContainer);

    }
}