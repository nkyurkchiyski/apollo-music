package com.apollo.music.views.commons.components.carousel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.OrderedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarouselList extends Div {
    private final List<Component> componentsList = new ArrayList<>();
    private final int pageSize;

    public CarouselList(final int pageSize, final Component... components) {
        this.pageSize = pageSize;
        componentsList.addAll(Arrays.asList(components));
        setWidthFull();
        setHeight("250px");
        initContent();
    }

    private void initContent() {
        final List<Slide> slides = new ArrayList<>();
        final int actualPageSize = (int) Math.min((double) componentsList.size(), pageSize);
        final int pages = (int) Math.ceil((double) componentsList.size() / actualPageSize);

        for (int i = 0; i < pages; i++) {
            final Slide slide = new Slide();
            final OrderedList container = new OrderedList();
            container.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");
            for (int j = 0; j < actualPageSize; j++) {
                container.add(componentsList.get((i + 1) * j));
            }
            slide.add(container);
            slides.add(slide);
        }
        final Carousel carousel = new Carousel(slides.toArray(new Slide[0])).withoutNavigation().withSlideDuration(0);
        add(carousel);
    }

    public void addItems(final Component... components) {
        removeAll();
        componentsList.addAll(Arrays.asList(components));
        initContent();
    }
}
