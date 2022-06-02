package com.apollo.music.views.commons.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.RouterLink;

public class MenuItemInfo extends ListItem {

    private final Class<?> view;

    public MenuItemInfo(final String menuTitle,
                        final String iconClass,
                        final Class<? extends Component> view) {
        this.view = view;
        final RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(view);

        final Span text = new Span(menuTitle);
        text.addClassNames("font-medium", "text-s");

        link.add(new LineAwesomeIcon(iconClass), text);
        add(link);
    }

    public <C extends Component & HasUrlParameter<String>> MenuItemInfo(final String menuTitle,
                                                                        final String iconClass,
                                                                        final Class<C> view,
                                                                        final String param) {
        this.view = view;
        final RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(view, param);

        final Span text = new Span(menuTitle);
        text.addClassNames("font-medium", "text-s");

        link.add(new LineAwesomeIcon(iconClass), text);
        add(link);
    }


    public Class<?> getView() {
        return view;
    }


}
