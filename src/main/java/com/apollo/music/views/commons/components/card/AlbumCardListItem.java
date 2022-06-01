package com.apollo.music.views.commons.components.card;

import com.apollo.music.data.entity.Album;
import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.UI;

public class AlbumCardListItem extends CardListItem {
    public AlbumCardListItem(final Album album) {
        super(album.getName(), album.getImageUrl(), null);
        addClickListener(e -> UI.getCurrent().navigate(String.format(ViewConstants.Route.ROUTE_FORMAT, ViewConstants.Route.ALBUM, album.getId())));
    }
}
