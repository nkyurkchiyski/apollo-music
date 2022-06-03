package com.apollo.music.views.commons;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;


public class ComponentFactory {

    public static Dialog createDialog() {
        final Dialog dialog = new com.vaadin.flow.component.dialog.Dialog();
        dialog.setWidth("600px");
        return dialog;
    }

    public static <T> Button createButton(final VaadinIcon icon,
                                          final T entity,
                                          final Consumer<T> onClickActionConsumer) {
        return createButton(icon, entity, onClickActionConsumer, null);
    }


    public static <T> Button createButton(final VaadinIcon icon,
                                          final T entity,
                                          final Consumer<T> onClickActionConsumer,
                                          final String onClickNotification) {
        return new Button(
                new Icon(icon),
                e -> {
                    onClickActionConsumer.accept(entity);
                    if (StringUtils.isNotBlank(onClickNotification)) {
                        Notification.show(onClickNotification);
                    }
                }
        );
    }
}
