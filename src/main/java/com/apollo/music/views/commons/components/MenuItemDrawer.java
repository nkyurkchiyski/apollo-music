package com.apollo.music.views.commons.components;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MenuItemDrawer extends ListItem {
    private final ListItem[] subMenus;

    private final AccordionPanel panel;

    public MenuItemDrawer(final String menuTitle,
                          final String iconClass,
                          final ListItem... subMenus) {
        this.subMenus = subMenus;
        final Accordion accordion = new Accordion();
        final VerticalLayout subMenuItems = new VerticalLayout(subMenus);
        subMenuItems.setSpacing(false);
        subMenuItems.setPadding(false);

        final Span parentMenuItem = new Span();
        parentMenuItem.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");

        final Span text = new Span(menuTitle);
        text.addClassNames("font-medium", "text-s");
        parentMenuItem.add(new LineAwesomeIcon(iconClass), text);

        panel = new AccordionPanel(parentMenuItem, subMenuItems);
        panel.addThemeVariants(DetailsVariant.REVERSE);
        panel.addClassName("hide-arrow");
        accordion.add(panel);
        add(accordion);
    }

    public ListItem[] getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(final ListItem... subMenus) {
        final VerticalLayout subMenuItems = new VerticalLayout(subMenus);
        subMenuItems.setSpacing(false);
        subMenuItems.setPadding(false);
        panel.setContent(subMenuItems);
    }
}
