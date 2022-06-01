package com.apollo.music.views.commons.components.card;

import com.apollo.music.data.entity.Song;
import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.UI;

public class SongCardListItem extends CardListItem {
    public SongCardListItem(final Song song) {
        super(song.getName(), song.getAlbum().getImageUrl(), song.getAlbum().getArtist().getName());
        addClickListener(e -> UI.getCurrent().navigate(String.format(ViewConstants.Route.ROUTE_FORMAT, ViewConstants.Route.SONG, song.getId())));
    }
}
