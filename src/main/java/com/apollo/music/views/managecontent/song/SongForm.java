package com.apollo.music.views.managecontent.song;

import com.apollo.music.data.entity.Album;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.entity.Genre;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.service.ArtistService;
import com.apollo.music.data.service.GenreService;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.EntityForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import org.springframework.data.domain.PageRequest;

import java.util.Objects;

public class SongForm extends EntityForm<Song> {
    private static final long serialVersionUID = 1L;

    private TextField name;
    private TextField sourceUrl;
    private ComboBox<Artist> artistCombo;
    private ComboBox<Album> albumCombo;
    private IntegerField trackNumber;
    private ComboBox<Genre> genreCombo;
    private DatePicker releasedOn;

    private final ArtistService artistService;
    private final GenreService genreService;

    public SongForm(final Song bean, final ArtistService artistService, final GenreService genreService) {
        super(Song.class, bean);
        this.artistService = artistService;
        this.genreService = genreService;
    }

    @Override
    protected Component[] getEntityFormComponents() {
        name = new TextField("Name");
        sourceUrl = new TextField("Source URL");
        releasedOn = new DatePicker("Released On");
        binder.forField(releasedOn).withConverter(
                new LocalDateToDateConverter()).bind(Song::getReleasedOn, Song::setReleasedOn);

        genreCombo = new ComboBox<>("Genre");
        genreCombo.setItems(
                (query) -> genreService.fetchByName(PageRequest.of(query.getPage(), query.getPageSize()),
                        query.getFilter()),
                (query) -> genreService.countByName(query.getFilter())
        );
        genreCombo.setItemLabelGenerator(Genre::getName);
        binder.forField(genreCombo).withValidator(Objects::nonNull, String.format(ViewConstants.Validation.EMPTY_FIELD_FORMAT, "genre"))
                .bind(Song::getGenre, Song::setGenre);

        albumCombo = new ComboBox<>("Album");
        albumCombo.setItemLabelGenerator(Album::getName);
        binder.forField(albumCombo)
                .withValidator(Objects::nonNull, String.format(ViewConstants.Validation.EMPTY_FIELD_FORMAT, "album"))
                .bind(Song::getAlbum, Song::setAlbum);


        artistCombo = new ComboBox<>("Artist");
        artistCombo.setItems(
                (query) -> artistService.fetchByName(PageRequest.of(query.getPage(), query.getPageSize()),
                        query.getFilter()),
                (query) -> artistService.countByName(query.getFilter())
        );
        artistCombo.addValueChangeListener(event -> albumCombo.setItems(event.getValue().getAlbums()));
        artistCombo.setItemLabelGenerator(Artist::getName);

        trackNumber = new IntegerField("Track Number");
        return new Component[]{name, sourceUrl, genreCombo, artistCombo, albumCombo, trackNumber, releasedOn};
    }

    @Override
    protected Song createBean() {
        return new Song();
    }
}
