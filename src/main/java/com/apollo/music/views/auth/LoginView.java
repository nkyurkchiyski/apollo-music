package com.apollo.music.views.auth;

import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle(ViewConstants.Title.LOGIN)
@Route(value = ViewConstants.Route.LOGIN)
public class LoginView extends LoginOverlay {
    public LoginView() {
        setAction("login");

        final LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Apollo Music");
        i18n.getHeader().setDescription("Login using user/user or admin/admin");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }

}
