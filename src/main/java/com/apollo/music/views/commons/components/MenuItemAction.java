package com.apollo.music.views.commons.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.ListItem;

import java.util.function.Consumer;

public class MenuItemAction extends ListItem {
    private final boolean isWithoutAuth;

    public MenuItemAction(final String menuTitle,
                          final String iconClass,
                          final boolean isWithoutAuth,
                          final Consumer<ClickEvent<Button>> onClickConsumer) {
        this.isWithoutAuth = isWithoutAuth;
        final Button link = new Button(menuTitle, new LineAwesomeIcon(iconClass));
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary", "font-medium", "text-s");

        link.addClickListener(onClickConsumer::accept);
        add(link);
    }

    public boolean isAccessibleWithoutAuthentication() {
        return isWithoutAuth;
    }
}
