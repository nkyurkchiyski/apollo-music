package com.apollo.music.views.commons.components;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Span;

@NpmPackage(value = "line-awesome", version = "1.3.0")
public class LineAwesomeIcon extends Span {
    public LineAwesomeIcon(String lineawesomeClassnames) {
        // Use Lumo classnames for suitable font size and margin
        addClassNames("me-s", "text-l");
        if (!lineawesomeClassnames.isEmpty()) {
            addClassNames(lineawesomeClassnames);
        }
    }
}