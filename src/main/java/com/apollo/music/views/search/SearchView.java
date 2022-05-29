package com.apollo.music.views.search;

import com.apollo.music.data.service.ArtistService;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.explore.SongCard;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Collections;

@PageTitle("Search")
@Route(value = "search", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class SearchView extends Div {

    private TextField artistName = new TextField("Artist");
    private TextField songName = new TextField("Song");
    private TextField year = new TextField("Year");
    private ComboBox<String> genre = new ComboBox<>("Genre");

    private Button reset = new Button("Reset");
    private Button search = new Button("Find");

//    private Binder<SamplePerson> binder = new Binder(SamplePerson.class);
    private OrderedList imageContainer;

    public SearchView(ArtistService personService) {
        addClassName("search-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        add(createSongsLayout());

//        binder.bindInstanceFields(this);
        clearForm();

        reset.addClickListener(e -> clearForm());
//        search.addClickListener(e -> {
//            personService.update(binder.getBean());
//            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
//            clearForm();
//        });
    }

    private Component createSongsLayout() {
        imageContainer = new OrderedList();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");
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

        return imageContainer;
    }

    private void clearForm() {
//        binder.setBean(new SamplePerson());
    }

    private Component createTitle() {
        return new H3("Search Terms");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        genre.setItems(new ListDataProvider<>(Collections.singleton("Pop")));
        formLayout.add(artistName, songName, year, genre);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        search.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(search);
        buttonLayout.add(reset);
        return buttonLayout;
    }
}
