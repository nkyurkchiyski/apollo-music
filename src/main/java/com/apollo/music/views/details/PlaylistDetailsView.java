package com.apollo.music.views.details;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.entity.Playlist;
import com.apollo.music.data.entity.SongPlaylist;
import com.apollo.music.data.service.PlaylistService;
import com.apollo.music.data.service.SongPlaylistService;
import com.apollo.music.security.AuthenticatedUser;
import com.apollo.music.security.MainLayoutBus;
import com.apollo.music.views.MainLayout;
import com.apollo.music.views.commons.ComponentFactory;
import com.apollo.music.views.commons.ViewConstants;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@PageTitle(ViewConstants.Title.PLAYLIST_DETAILS)
@Route(value = ViewConstants.Route.PLAYLIST, layout = MainLayout.class)
@RolesAllowed(EntityConfiguration.USER)
@Uses(Icon.class)
public class PlaylistDetailsView extends EntityDetailsView<Playlist, PlaylistService> {
    private Grid<SongPlaylist> songGrid;
    private GridListDataView<SongPlaylist> dataView;

    private final SongPlaylistService songPlaylistService;
    private final AuthenticatedUser authenticatedUser;
    private final MainLayoutBus mainLayoutBus;

    @Autowired
    protected PlaylistDetailsView(final PlaylistService entityService,
                                  final SongPlaylistService songPlaylistService,
                                  final AuthenticatedUser authenticatedUser,
                                  final MainLayoutBus mainLayoutBus) {
        super(entityService);
        this.songPlaylistService = songPlaylistService;
        this.authenticatedUser = authenticatedUser;
        this.mainLayoutBus = mainLayoutBus;
    }

    @Override
    protected Component getSubMainComponentTitle(final Playlist entity) {
        final H2 title = new H2("Songs in playlist " + entity.getName());
        title.addClassNames("mb-m", "mt-m", "text-2xl");
        final HorizontalLayout layout = new HorizontalLayout();

        final Button saveButton = new Button(new Icon(VaadinIcon.EDIT));
        saveButton.addClickListener(e -> {
            dataView.getItems().forEach(songPlaylistService::update);
            Notification.show(String.format(ViewConstants.Notification.ENTITY_UPDATED, entity.getClass().getSimpleName()));
        });
        layout.setWidthFull();
        layout.setAlignItems(FlexComponent.Alignment.BASELINE);
        layout.add(title, saveButton);
        return layout;
    }

    @Override
    protected Component createSubMainComponent(final Playlist entity) {
        songGrid = new Grid<>();
        dataView = refreshGrid(entity);
        if (isOwnerOfPlaylist(entity)) {
            configurePlaylistSongReordering(dataView);
        }

        songGrid.removeAllColumns();
        songGrid.addColumn(new ComponentRenderer<>(e -> new Label(e.getTrackNumber().toString())))
                .setWidth("5em")
                .setFlexGrow(0)
                .setHeader("#");
        songGrid.addColumn(new ComponentRenderer<>(e -> new Label(e.getSong().getName())))
                .setHeader("Title");
        songGrid.addColumn(new ComponentRenderer<>(this::createActions))
                .setWidth("8em")
                .setFlexGrow(0);
        return songGrid;
    }

    private void configurePlaylistSongReordering(final GridListDataView<SongPlaylist> dataView) {
        songGrid.setDropMode(GridDropMode.BETWEEN);
        songGrid.setRowsDraggable(true);

        final AtomicReference<SongPlaylist> draggedItemAtomic = new AtomicReference<>();
        songGrid.addDragStartListener(
                e -> draggedItemAtomic.set(e.getDraggedItems().get(0)));

        songGrid.addDropListener(e -> {
            final SongPlaylist draggedItem = draggedItemAtomic.get();
            final SongPlaylist targetSong = e.getDropTargetItem().orElse(null);
            final GridDropLocation dropLocation = e.getDropLocation();

            boolean songWasDroppedOntoItself = draggedItem
                    .equals(targetSong);

            if (targetSong == null || songWasDroppedOntoItself)
                return;

            dataView.removeItem(draggedItem);

            if (dropLocation == GridDropLocation.BELOW) {
                dataView.addItemAfter(draggedItem, targetSong);
            } else {
                dataView.addItemBefore(draggedItem, targetSong);
            }
            refreshSongNumbers(dataView);
        });

        songGrid.addDragEndListener(e -> draggedItemAtomic.set(null));
    }

    private void refreshSongNumbers(final GridListDataView<SongPlaylist> dataView) {
        final AtomicInteger trackNumberAtomic = new AtomicInteger(1);
        dataView.getItems().forEach(song -> song.setTrackNumber(trackNumberAtomic.getAndIncrement()));
    }

    private GridListDataView<SongPlaylist> refreshGrid(final Playlist entity) {
        return songGrid.setItems(entity.getSongs().stream().sorted(Comparator.comparing(SongPlaylist::getTrackNumber)).toArray(SongPlaylist[]::new));
    }

    private Component createActions(final SongPlaylist songPlaylist) {
        final HorizontalLayout layout = new HorizontalLayout();
        final Button details = ComponentFactory.createButton(VaadinIcon.EYE,
                songPlaylist,
                sp -> UI.getCurrent().navigate(String.format(ViewConstants.Route.ROUTE_FORMAT, ViewConstants.Route.SONG, sp.getSong().getId()))
        );

        final Button remove = ComponentFactory.createButton(VaadinIcon.MINUS, songPlaylist, this::performRemoveSongFromPlaylist, ViewConstants.Notification.REMOVED_FROM_PLAYLIST);

        layout.add(details, remove);
        return layout;
    }

    private void performRemoveSongFromPlaylist(final SongPlaylist songPlaylist) {
        entityService.removeSongFromPlaylist(songPlaylist.getPlaylist(), songPlaylist.getSong());
        dataView = refreshGrid(songPlaylist.getPlaylist());
    }

    @Override
    protected Component[] createMoreInfoComponents(final Playlist entity) {
        final Date createdAt = entity.getCreatedAt();
        final String releaseDateText = createdAt != null ? ViewConstants.DATE_FORMAT.format(createdAt) : "N/A";
        final Label releaseDateLabel = new Label("Created On: " + releaseDateText);
        releaseDateLabel.addClassNames("mb-0", "mt-0", "text-m");
        return new Component[]{releaseDateLabel};
    }

    @Override
    protected Component createSubTitle(final Playlist entity) {
        return ComponentFactory.createH2Title(entity.getUser().getName());
    }

    @Override
    protected String getMainTitleText(final Playlist entity) {
        return entity.getName();
    }

    @Override
    protected Component[] createTitleComponents(final Playlist entity) {
        if (isOwnerOfPlaylist(entity)) {
            final Button editButton = ComponentFactory.createButton(VaadinIcon.PENCIL, entity, this::openPlaylistEditDialog);
            final Button deleteButton = ComponentFactory.createButton(VaadinIcon.TRASH, entity, this::performDeletePlaylistAction);
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON);
            return new Component[]{editButton, deleteButton};
        }
        return super.createTitleComponents(entity);
    }

    private void performDeletePlaylistAction(final Playlist entity) {
        entityService.delete(entity.getId());
        mainLayoutBus.getMainLayout().refreshPlaylistSideMenu();
        UI.getCurrent().navigate(ViewConstants.Route.EXPLORE);
        Notification.show(String.format(ViewConstants.Notification.ENTITY_DELETED, entity.getClass().getSimpleName()));
    }

    private void openPlaylistEditDialog(final Playlist entity) {
        final Dialog dialog = ComponentFactory.createDialog();
        final PlaylistForm form = new PlaylistForm(entity);
        form.addSaveClickListener(e -> {
            if (form.isSaved()) {
                entityService.update(form.getBean());
            }
            dialog.close();
            mainLayoutBus.getMainLayout().refreshPlaylistSideMenu();
            UI.getCurrent().navigate(String.format(ViewConstants.Route.ROUTE_FORMAT, ViewConstants.Route.PLAYLIST, form.getBean().getId()));
            Notification.show(String.format(ViewConstants.Notification.ENTITY_SAVED, entity.getClass().getSimpleName()));
        });

        form.addCancelClickListener(e -> dialog.close());
        dialog.add(form);
        dialog.open();
    }

    private boolean isOwnerOfPlaylist(final Playlist entity) {
        return authenticatedUser.get().isPresent() && authenticatedUser.get().get().getPlaylists().contains(entity);
    }
}
