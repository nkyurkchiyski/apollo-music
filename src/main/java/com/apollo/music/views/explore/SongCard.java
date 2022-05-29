package com.apollo.music.views.explore;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SongCard extends ListItem {

    public SongCard(String songName, String artistName, String url) {
        addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l");

        Div div = new Div();
        Image image = new Image();
        image.setSrc(url);
        image.addClassNames("bg-contrast", "flex items-center", "justify-center", "mb-m", "overflow-hidden",
                "rounded-m w-full", "img-cover");
        div.add(image);

        VerticalLayout verticalLayout = new VerticalLayout();
        Span header = new Span();
        header.addClassNames("text-xl", "font-semibold");
        header.setText(songName);

        Span subtitle = new Span();
        subtitle.addClassNames("text-s", "text-secondary");
        subtitle.setText(artistName);
        verticalLayout.add(header, subtitle);
        add(div, verticalLayout);

    }
}
