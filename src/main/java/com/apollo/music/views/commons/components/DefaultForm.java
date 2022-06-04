package com.apollo.music.views.commons.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.function.Consumer;

public class DefaultForm extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    protected final Button cancel = new Button("Cancel");
    protected final Button save = new Button("Save");

    private final Component[] formComponents;
    private final ComponentEventListener<ClickEvent<Button>> onSaveListener;
    private final Consumer<ClickEvent<Button>> onCancelListener;

    public DefaultForm(final Component[] formComponents,
                       final ComponentEventListener<ClickEvent<Button>> onSaveListener,
                       final Consumer<ClickEvent<Button>> onCancelListener) {
        this.formComponents = formComponents;
        this.onSaveListener = onSaveListener;
        this.onCancelListener = onCancelListener;
        initContent();
    }

    private void initContent() {
        final FormLayout formLayout = new FormLayout();
        formLayout.add(formComponents);

        add(formLayout);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(onSaveListener);

        cancel.addClickListener(e -> {
            clearForm();
            onCancelListener.accept(e);
        });
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        buttonLayout.add(save, cancel);
        buttonLayout.getStyle().set("marginRight", "10px");
        add(buttonLayout);
        setHorizontalComponentAlignment(Alignment.END, buttonLayout);
    }

    public void clearForm() {
        getChildren().filter(c -> c instanceof HasValue).map(HasValue.class::cast).forEach(f -> f.setValue(""));
    }

    public void setSaveButtonText(final String text) {
        save.setText(text);
    }


    public void setCancelButtonText(final String text) {
        cancel.setText(text);
    }

}
