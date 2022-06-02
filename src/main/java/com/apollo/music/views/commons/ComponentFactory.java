package com.apollo.music.views.commons;

import com.vaadin.flow.component.dialog.Dialog;

public class ComponentFactory {

    public static Dialog createDialog() {
        final com.vaadin.flow.component.dialog.Dialog dialog = new com.vaadin.flow.component.dialog.Dialog();
        dialog.setWidth("600px");
        return dialog;
    }
}
