package com.apollo.music.views.myaccount;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.entity.User;
import com.apollo.music.data.service.UserService;
import com.apollo.music.security.AuthenticatedUser;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle(ViewConstants.Title.MY_ACCOUNT)
@Route(value = ViewConstants.Route.ACCOUNT, layout = MainLayout.class)
@RolesAllowed(EntityConfiguration.USER)
@Uses(Icon.class)
public class MyAccountView extends Div {

    private final TextField username = new TextField("Username");
    private final TextField name = new TextField("Full Name");
    private final EmailField email = new EmailField("Email address");
    private final TextField imageUrl = new TextField("Image");

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete Account");

    private final Binder<User> binder = new Binder<>(User.class);

    private final AuthenticatedUser authenticatedUser;

    public MyAccountView(final UserService userService, final AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        addClassName("my-account-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();
        binder.forField(email)
                .withValidator(email -> getCurrentUser().getEmail().equalsIgnoreCase(email) || !userService.existsWithEmail(email),
                        String.format(ViewConstants.Validation.USER_ALREADY_EXISTS, EntityConfiguration.EMAIL_FIELD_NAME))
                .bind(User::getEmail, User::setEmail);
        binder.forField(username)
                .withValidator(username -> getCurrentUser().getUsername().equalsIgnoreCase(username) || !userService.existsWithUsername(username),
                        String.format(ViewConstants.Validation.USER_ALREADY_EXISTS, EntityConfiguration.USERNAME_FIELD_NAME))
                .bind(User::getUsername, User::setUsername);


        save.addClickListener(e -> {
            userService.update(binder.getBean());
            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(getCurrentUser());
    }

    private User getCurrentUser() {
        return authenticatedUser.get().orElse(new User());
    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email.setErrorMessage("Please enter a valid email address");
        formLayout.add(username, name, email, imageUrl);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonLayout.add(delete);
        return buttonLayout;
    }
}
