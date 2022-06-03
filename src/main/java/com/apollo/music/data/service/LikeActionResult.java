package com.apollo.music.data.service;

import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.icon.VaadinIcon;

public enum LikeActionResult {
    LIKED(ViewConstants.ADDED_TO, VaadinIcon.THUMBS_DOWN), DISLIKED(ViewConstants.REMOVED_FROM, VaadinIcon.THUMBS_UP);

    private final String actionText;
    private final VaadinIcon oppositeIcon;

    LikeActionResult(final String actionText, final VaadinIcon oppositeIcon) {
        this.actionText = actionText;
        this.oppositeIcon = oppositeIcon;
    }

    public String getActionText() {
        return actionText;
    }

    public VaadinIcon getOppositeIcon() {
        return oppositeIcon;
    }
}
