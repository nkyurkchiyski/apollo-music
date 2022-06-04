package com.apollo.music.views.auth;

import com.apollo.music.data.service.UserService;
import com.apollo.music.security.AuthenticatedUser;
import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle(ViewConstants.Title.REGISTER)
@Route(value = ViewConstants.Route.REGISTER)
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    private final UserService userService;


    @Autowired
    public RegisterView(final AuthenticatedUser authenticatedUser,
                        final UserService userService) {
        this.userService = userService;

        if (authenticatedUser.get().isPresent()) {
            UI.getCurrent().navigate(ViewConstants.Route.EXPLORE);
        } else {
            constructUI();
        }
    }

    private void constructUI() {
        final UserForm form = new UserForm(null, userService);
        form.setWidth("50%");
        final VerticalLayout layout = new VerticalLayout(new H1("Register"));
        layout.add(form);
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(layout);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
