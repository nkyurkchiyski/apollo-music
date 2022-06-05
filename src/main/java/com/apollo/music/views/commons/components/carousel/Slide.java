package com.apollo.music.views.commons.components.carousel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;

@Tag("paper-slide")
public class Slide extends Component implements HasComponents {

    public Slide(Component... components) {
        this.add(components);
    }
}