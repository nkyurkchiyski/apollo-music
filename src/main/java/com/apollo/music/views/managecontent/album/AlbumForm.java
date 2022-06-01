package com.apollo.music.views.managecontent.album;

import com.apollo.music.data.entity.Album;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.service.ArtistService;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.EntityForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import org.springframework.data.domain.PageRequest;

import java.util.Objects;

public class AlbumForm extends EntityForm<Album> {
    private static final long serialVersionUID = 1L;

    private TextField name;
    private TextField imageUrl;
    private ComboBox<Artist> artistCombo;
    private DatePicker releasedOn;

    private final ArtistService artistService;

    public AlbumForm(final Album bean, final ArtistService artistService) {
        super(Album.class, bean);
        this.artistService = artistService;
    }

    @Override
    protected Component[] getEntityFormComponents() {
        name = new TextField("Name");
        imageUrl = new TextField("Image URL");
        releasedOn = new DatePicker("Released On");
        binder.forField(releasedOn).withConverter(
                new LocalDateToDateConverter()).bind(Album::getReleasedOn, Album::setReleasedOn);
        artistCombo = new ComboBox<>("Artist");
        artistCombo.setItems(
                (query) -> artistService.fetchByName(PageRequest.of(query.getPage(), query.getPageSize()),
                        query.getFilter()),
                (query) -> artistService.countByName(query.getFilter())
        );
        artistCombo.setItemLabelGenerator(Artist::getName);
        binder.forField(artistCombo)
                .withValidator(Objects::nonNull, String.format(ViewConstants.Validation.EMPTY_FIELD_FORMAT, "artist"))
                .bind(Album::getArtist, Album::setArtist);

        return new Component[]{name, artistCombo, imageUrl, releasedOn};
    }

    @Override
    protected Album createBean() {
        return new Album();
    }
}
