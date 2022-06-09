package com.apollo.music.views.commons.components.card;

import com.apollo.music.data.entity.Song;
import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class SongCardListItem extends CardListItem {
    public SongCardListItem(final Song song) {
        super(song.getName(), song.getAlbum().getImageUrl(), song.getAlbum().getArtist().getName());
        addClickListener(e -> UI.getCurrent().navigate(String.format(ViewConstants.Route.ROUTE_FORMAT, ViewConstants.Route.SONG, song.getId())));
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        final Label likeLabel = createPropertyLabel("likes", song.getLikesCount());
        final Label playsLabel = createPropertyLabel("plays", song.getPlayedCount());
        horizontalLayout.add(likeLabel, playsLabel);
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        add(horizontalLayout);
    }

    private Label createPropertyLabel(final String propName, final Integer count) {
        final String spanText = count.toString();
        final Label label = new Label(spanText + " " + propName);
        label.addClassNames("text-s", "text-primary", "font-semibold");
        return label;
    }
}
