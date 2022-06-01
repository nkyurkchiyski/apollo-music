package com.apollo.music.views;

import com.apollo.music.data.entity.User;
import com.apollo.music.security.AuthenticatedUser;
import com.apollo.music.views.commons.components.MenuItemDrawer;
import com.apollo.music.views.commons.components.MenuItemInfo;
import com.apollo.music.views.explore.ExploreView;
import com.apollo.music.views.managecontent.album.AlbumContentManagerView;
import com.apollo.music.views.managecontent.artist.ArtistContentManagerView;
import com.apollo.music.views.managecontent.genre.GenreContentManagerView;
import com.apollo.music.views.managecontent.song.SongContentManagerView;
import com.apollo.music.views.myaccount.MyAccountView;
import com.apollo.music.views.search.SearchView;
import com.apollo.music.views.song.SongLikedView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;

import java.util.Arrays;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H1 viewTitle;

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("m-0", "text-l");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
                "w-full");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Apollo Music");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (final ListItem listItem : createMenuItems()) {
            if (listItem instanceof MenuItemInfo && accessChecker.hasAccess(((MenuItemInfo) listItem).getView())) {
                list.add(listItem);
            } else if (listItem instanceof MenuItemDrawer) {
                final MenuItemDrawer drawer = (MenuItemDrawer) listItem;
                if (Arrays.stream(drawer.getSubMenus()).allMatch(subMenu -> accessChecker.hasAccess(subMenu.getView()))) {
                    list.add(drawer);
                }
            }
        }

        return nav;
    }

    private ListItem[] createMenuItems() {
        return new ListItem[]{ //
                new MenuItemInfo("Search", "la la-search", SearchView.class), //

                new MenuItemInfo("Explore", "la la-compass", ExploreView.class), //

                new MenuItemInfo("Liked Songs", "la la-th-list", SongLikedView.class), //

                new MenuItemInfo("My Account", "la la-user", MyAccountView.class), //

                new MenuItemDrawer("Manage Content", "la la-pencil-ruler",
                        new MenuItemInfo("Genres", "la la-drum", GenreContentManagerView.class),
                        new MenuItemInfo("Artists", "la la-microphone-alt", ArtistContentManagerView.class),
                        new MenuItemInfo("Album", "la la-record-vinyl", AlbumContentManagerView.class),
                        new MenuItemInfo("Songs", "la la-music", SongContentManagerView.class)), //

        };
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName(), user.getImageUrl());
            avatar.addClassNames("me-xs");

            ContextMenu userMenu = new ContextMenu(avatar);
            userMenu.setOpenOnClick(true);
            userMenu.addItem("Logout", e -> {
                authenticatedUser.logout();
            });

            Span name = new Span(user.getName());
            name.addClassNames("font-medium", "text-s", "text-secondary");

            layout.add(avatar, name);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
