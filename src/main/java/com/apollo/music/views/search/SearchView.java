package com.apollo.music.views.search;

import com.apollo.music.data.entity.Genre;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.service.GenreService;
import com.apollo.music.data.service.SongService;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.DefaultForm;
import com.apollo.music.views.commons.components.card.SongCardListItem;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle(ViewConstants.Title.SEARCH)
@Route(value = ViewConstants.Route.SEARCH, layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class SearchView extends Div {

    private final TextField artistName = new TextField("Artist");
    private final TextField songName = new TextField("Song");
    private final TextField albumName = new TextField("Album");
    private final ComboBox<String> genre = new ComboBox<>("Genre");
    private final H4 resultTitle = new H4();

    private OrderedList imageContainer;
    private final SongService songService;
    private final GenreService genreService;

    @Autowired
    public SearchView(final SongService songService,
                      final GenreService genreService) {
        this.songService = songService;
        this.genreService = genreService;
        addClassName("search-view");

        add(createTitle());
        add(createFormLayout());

        resultTitle.setWidthFull();
        add(resultTitle);
        add(createSongsLayout());
    }

    private Component createSongsLayout() {
        imageContainer = new OrderedList();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");
        return imageContainer;
    }

    private Component createTitle() {
        return new H3("Search Terms");
    }

    private Component createFormLayout() {
        final Component[] formComponents = new Component[]{songName, genre, artistName, albumName};
        final DefaultForm searchForm = new DefaultForm(formComponents, e -> onSearchClick());
        final List<String> genres = genreService.list(PageRequest.of(0, Integer.MAX_VALUE)).stream().map(Genre::getName).collect(Collectors.toList());
        genre.setItems(new ListDataProvider<>(genres));
        searchForm.setSaveButtonText("Search");
        searchForm.setCancelButtonText("Reset");
        return searchForm;
    }

    private void onSearchClick() {
        imageContainer.removeAll();
        final SearchFilter filter = new SearchFilter(songName.getValue(), genre.getValue(), albumName.getValue(), artistName.getValue());
        final Page<Song> result = songService.getAllBySearchFilter(PageRequest.of(0, 25), filter);
        final String resultTitleText = result.getTotalElements() == 0 ? "No songs found." : String.format("%s song/s found.", result.getTotalElements());
        resultTitle.setText(resultTitleText);
        result.forEach(song -> imageContainer.add(new SongCardListItem(song)));

    }
}
