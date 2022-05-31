package com.apollo.music.views.managecontent.artist;

import com.apollo.music.data.entity.Artist;
import com.apollo.music.views.commons.components.EntityForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;

public class ArtistForm extends EntityForm<Artist> {
    private static final long serialVersionUID = 1L;

    private TextField name;
    private TextField imageUrl;

    public ArtistForm(final Artist bean) {
        super(Artist.class, bean);
    }

    @Override
    protected Component[] getEntityFormComponents() {
        name = new TextField("Name");
        imageUrl = new TextField("Image URL");
        return new Component[]{name, imageUrl};
    }

    @Override
    protected Artist createBean() {
        return new Artist();
    }
}
