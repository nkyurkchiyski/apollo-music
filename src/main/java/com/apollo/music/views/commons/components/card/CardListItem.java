package com.apollo.music.views.commons.components.card;

import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.commons.lang3.StringUtils;

public class CardListItem extends ListItem {

    public CardListItem(final String mainTitle, final String imageUrl, final String subTitle) {
        addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l");

        final Image image = new Image(StringUtils.isNotBlank(imageUrl) ? imageUrl : ViewConstants.DEFAULT_COVER, "card-img");
        image.addClassNames("bg-contrast", "flex items-center", "justify-center", "mb-m", "overflow-hidden",
                "rounded-m w-full", "img-cover");

        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        final Span titleSpan = new Span();
        titleSpan.addClassNames("text-xl", "font-semibold");
        titleSpan.setText(mainTitle);
        verticalLayout.add(titleSpan);

        if (StringUtils.isNotBlank(subTitle)) {
            final Span subTitleSpan = new Span();
            subTitleSpan.addClassNames("text-s", "text-secondary");
            subTitleSpan.setText(subTitle);
            verticalLayout.add(subTitleSpan);
        }
        add(image, verticalLayout);
    }

}
