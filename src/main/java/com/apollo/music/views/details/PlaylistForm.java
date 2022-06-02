package com.apollo.music.views.details;

import com.apollo.music.data.entity.Playlist;
import com.apollo.music.views.commons.components.EntityForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;

public class PlaylistForm extends EntityForm<Playlist> {
    private TextField name;

    public PlaylistForm(final Playlist bean) {
        super(Playlist.class, bean);
    }

    @Override
    protected Component[] getEntityFormComponents() {
        name = new TextField("Name");
        return new Component[]{name};
    }

    @Override
    protected Playlist createBean() {
        return new Playlist();
    }
}
