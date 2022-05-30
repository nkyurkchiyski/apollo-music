package com.apollo.music.views.managecontent.genre;

import com.apollo.music.data.entity.Genre;
import com.apollo.music.views.commons.components.EntityForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;

public class GenreForm extends EntityForm<Genre> {

    private TextField name;

    public GenreForm(final Genre bean) {
        super(Genre.class, bean);
    }

    @Override
    protected Component[] getEntityFormComponents() {
        name = new TextField("Name");
        return new Component[]{name};
    }

    @Override
    protected Genre createBean() {
        return new Genre();
    }
}
