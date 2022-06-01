package com.apollo.music.views.details;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.data.service.AbstractEntityService;
import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import org.apache.commons.lang3.StringUtils;

public abstract class EntityDetailsView<T extends EntityWithId, S extends AbstractEntityService<T>> extends Main implements HasComponents, HasStyle, HasUrlParameter<String> {

    protected final S entityService;

    protected EntityDetailsView(final S entityService) {
        this.entityService = entityService;
    }

    @Override
    public void setParameter(final BeforeEvent event, final String parameter) {
        removeAll();
        entityService.get(parameter).ifPresent(this::constructUI);
    }

    private void constructUI(final T entity) {
        addClassNames("explore-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");
        final HorizontalLayout container = new HorizontalLayout();
        final VerticalLayout songInfo = new VerticalLayout();

        final H1 mainTitle = new H1(getMainTitleText(entity));
        mainTitle.addClassNames("mb-0", "mt-l");

        final H2 subTitle = new H2(getSubTitleText(entity));
        subTitle.addClassNames("mb-0", "mt-0", "text-l");

        final H2 moreInfoTitle = new H2("More Information");
        moreInfoTitle.addClassNames("mb-0", "mt-0", "text-m");

        final Component[] moreInfoComponents = createMoreInfoComponents(entity);

        final Div div = new Div();
        final Image coverImg = new Image(StringUtils.firstNonBlank(getImageUrl(entity), ViewConstants.DEFAULT_COVER), "details-img");
        coverImg.addClassNames("bg-contrast", "flex items-center", "justify-center", "mt-m", "mb-m", "overflow-hidden",
                "rounded-m w-full");
        coverImg.setMaxHeight("350px");
        div.add(coverImg);
        div.addClassName("pos-r");

        songInfo.add(mainTitle, subTitle, moreInfoTitle);
        songInfo.add(moreInfoComponents);
        container.setWidthFull();
        container.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        if (isPlayButtonVisible()) {
            final Button playButton = new Button(new Icon(VaadinIcon.PLAY));
            playButton.addClassNames("play-button-big", "pos-abr");
            playButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            playButton.addClickListener(this::createPlayButtonClickListener);
            div.add(playButton);
        }
        container.add(songInfo, div);

        final H2 similarSongsTitle = new H2(getSubMainComponentTitle(entity));
        similarSongsTitle.addClassNames("mb-m", "mt-m", "text-2xl");

        final Component subMainComponent = createSubMainComponent(entity);
        add(container, similarSongsTitle, subMainComponent);

    }

    protected abstract String getSubMainComponentTitle(final T entity);

    protected void createPlayButtonClickListener(final ClickEvent<Button> buttonClickEvent) {
        //do nothing
    }

    protected abstract Component createSubMainComponent(final T entity);

    protected abstract String getImageUrl(final T entity);

    protected abstract Component[] createMoreInfoComponents(final T entity);

    protected abstract String getSubTitleText(final T entity);

    protected abstract String getMainTitleText(final T entity);

    protected boolean isPlayButtonVisible() {
        return false;
    }
}
