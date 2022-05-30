package com.apollo.music.views.managecontent;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.entity.Artist;
import com.apollo.music.data.service.ArtistService;
import com.apollo.music.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.Collections;

@PageTitle("Manage Content")
@Route(value = "manage-content", layout = MainLayout.class)
@RolesAllowed(EntityConfiguration.ADMIN)
@Uses(Icon.class)
public class ManageContentView extends Div {

    private final TextField songName = new TextField("Name");
    private final TextField performers = new TextField("Performers");
    private final TextField composers = new TextField("Composers");
    private final ComboBox<String> genre = new ComboBox<>("Genre");
    private final TextField album = new TextField("Album");
    private final TextField source = new TextField("Audio Source URL");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final Binder<Artist> binder = new Binder(Artist.class);

    public ManageContentView(ArtistService personService) {
        addClassName("manage-content-view");

        add(createTitle());
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Div div = new Div();
        Image image = new Image();
        image.setSrc("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Future_-_Openair_Frauenfeld_2019_05.jpg/800px-Future_-_Openair_Frauenfeld_2019_05.jpg");
        image.addClassNames("bg-contrast", "flex items-center", "justify-center", "mb-m", "overflow-hidden",
                "rounded-m w-full");
        div.add(image);
        horizontalLayout.add(div, createFormLayout());
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        add(horizontalLayout);
        add(createButtonLayout());

        clearForm();

        cancel.addClickListener(e -> clearForm());
//        save.addClickListener(e -> {
//            personService.update(binder.getBean());
//            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
//            clearForm();
//        });
    }

    private void clearForm() {
        binder.setBean(new Artist());
    }

    private Component createTitle() {
        return new H3("Add New Song");
    }

    private Component createFormLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        genre.setItems(new ListDataProvider<>(Collections.singleton("Pop")));
        songName.setWidthFull();
        performers.setWidthFull();
        composers.setWidthFull();
        genre.setWidthFull();
        album.setWidthFull();
        source.setWidthFull();
        verticalLayout.add(songName, performers, composers, genre, album, source);
        return verticalLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private static class PhoneNumberField extends CustomField<String> {
        private final ComboBox<String> countryCode = new ComboBox<>();
        private final TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setPattern("\\+\\d*");
            countryCode.setPreventInvalidInput(true);
            countryCode.setItems("+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
            number.setPattern("\\d*");
            number.setPreventInvalidInput(true);
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                String s = countryCode.getValue() + " " + number.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }

}
