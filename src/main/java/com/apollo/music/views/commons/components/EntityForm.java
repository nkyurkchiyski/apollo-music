package com.apollo.music.views.commons.components;

import com.apollo.music.data.entity.EntityWithId;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import org.apache.commons.lang3.ObjectUtils;

public abstract class EntityForm<T extends EntityWithId> extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    protected final Button cancel = new Button("Cancel");
    protected final Button save = new Button("Save");

    private boolean isSaved = false;
    protected T bean;
    protected final BeanValidationBinder<T> binder;

    public EntityForm(final Class<T> beanType, final T bean) {
        this.bean = ObjectUtils.firstNonNull(bean, createBean());
        setId(beanType.getSimpleName() + "-editor-layout".toLowerCase());
        binder = new BeanValidationBinder<>(beanType);
        initContent();
    }

    private void initContent() {
        final FormLayout formLayout = new FormLayout();
        final Component[] fields = getEntityFormComponents();
        formLayout.add(fields);

        add(formLayout);
        binder.bindInstanceFields(this);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e ->
        {
            try {
                if (binder.isValid()) {
                    if (this.bean == null) {
                        this.bean = createBean();
                    }
                    binder.writeBean(this.bean);
                    isSaved = true;
                } else {
                    Notification.show("There are errors present in the form!");
                }
            } catch (final ValidationException validationException) {
                Notification.show("An has occurred while performing the save operation. No changes were made!");
            }
        });

        cancel.addClickListener(e -> clearForm());
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        buttonLayout.add(save, cancel);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);
        add(buttonLayout);
        binder.readBean(bean);
    }

    protected abstract Component[] getEntityFormComponents();

    protected abstract T createBean();

    public void clearForm() {
        setBean(createBean());
        isSaved = false;
    }

    public void setBean(final T bean) {
        this.bean = bean;
        binder.readBean(this.bean);
    }

    public void addCancelClickListener(final ComponentEventListener<ClickEvent<Button>> eventListener) {
        cancel.addClickListener(eventListener);
    }


    public void addSaveClickListener(final ComponentEventListener<ClickEvent<Button>> eventListener) {
        save.addClickListener(eventListener);
    }

    public T getBean() {
        return bean;
    }

    public boolean isSaved() {
        return isSaved;
    }
}