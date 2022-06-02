package com.apollo.music.security;

import com.apollo.music.views.MainLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class MainLayoutBus {
    private MainLayout mainLayout;

    public MainLayoutBus() {
    }

    public MainLayout getMainLayout() {
        return this.mainLayout;
    }

    public void setMainLayout(final MainLayout mainLayout) {
        this.mainLayout = mainLayout;
    }
}
