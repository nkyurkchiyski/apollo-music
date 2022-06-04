package com.apollo.music.views.auth;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.entity.User;
import com.apollo.music.data.service.UserService;
import com.apollo.music.views.commons.ViewConstants;
import com.apollo.music.views.commons.components.EntityForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

public class UserForm extends EntityForm<User> {
    private static final long serialVersionUID = 1L;

    private TextField name;
    private TextField username;
    private TextField imageUrl;
    private EmailField email;
    private PasswordField password;
    private PasswordField confirmPassword;
    private final UserService userService;

    @Autowired
    public UserForm(final User bean,
                    final UserService userService) {
        super(User.class, bean);
        this.userService = userService;
        addValidationForFields();
        configureButtons();
    }

    private void configureButtons() {
        save.setText("Sign Up");
        save.addClickListener(e -> {
            if (isSaved()) {
                final User user = getBean();
                userService.update(user);
                UI.getCurrent().navigate(ViewConstants.Route.LOGIN);
                Notification.show(ViewConstants.Notification.REGISTER_SUCCESS);
            }
        });

        cancel.setText("Clear");
        cancel.addClickListener(e -> clearForm());
    }

    @Override
    protected Component[] getEntityFormComponents() {
        username = new TextField("Username");
        email = new EmailField("Email");
        name = new TextField("Name");
        imageUrl = new TextField("Image URL");
        password = new PasswordField("Password");
        confirmPassword = new PasswordField("Confirm Password");
        return new Component[]{username, email, name, imageUrl, password, confirmPassword};
    }

    private void addValidationForFields() {
        binder.forField(username)
                .withValidator(text -> !userService.existsWithUsername(text),
                        String.format(ViewConstants.Validation.USER_ALREADY_EXISTS, EntityConfiguration.USERNAME_FIELD_NAME))
                .bind(User::getUsername, User::setUsername);


        binder.forField(email)
                .withValidator(text -> !userService.existsWithEmail(text),
                        String.format(ViewConstants.Validation.USER_ALREADY_EXISTS, EntityConfiguration.EMAIL_FIELD_NAME))
                .bind(User::getEmail, User::setEmail);


        binder.forField(confirmPassword)
                .withValidator(
                        pass -> pass.equals(password.getValue()),
                        ViewConstants.Validation.PASSWORDS_NOT_MATCH)
                .bind(User::getPassword, User::setPassword);
    }

    @Override
    protected User createBean() {
        return new User();
    }
}
