package es.uca.iw.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.Role;
import es.uca.iw.tarifa.TarifaView;
import es.uca.iw.views.Trabajador.*;
import es.uca.iw.views.about.AboutView;
import es.uca.iw.views.client_views.*;
import es.uca.iw.views.helloworld.HelloWorldView;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout implements BeforeEnterObserver {

    private AuthenticatedUser authenticatedUser;

    private H2 viewTitle;

    public MainLayout(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();


    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Navegación");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        Optional<Cliente> optionalCliente = authenticatedUser.get();
        SideNav nav = new SideNav();
        if (authenticatedUser.get().isEmpty()) {
            nav.addItem(new SideNavItem("Página Principal", HelloWorldView.class));
            nav.addItem(new SideNavItem("Sobre Nosotros", AboutView.class));
        } else if (optionalCliente.get().getRoles().stream().anyMatch(role -> role.equals(Role.USER))) {
            nav.addItem(new SideNavItem("General", ClientOverview.class, VaadinIcon.DASHBOARD.create()));
            nav.addItem(new SideNavItem("Tarifas", TarifasView.class, VaadinIcon.ABACUS.create()));
            nav.addItem(new SideNavItem("Noticias y promociones", NoticiasView.class, VaadinIcon.NEWSPAPER.create()));
            nav.addItem(new SideNavItem("Facturas", FacturasView.class, VaadinIcon.INVOICE.create()));
            nav.addItem(new SideNavItem( "Chat", ChatView.class, VaadinIcon.CHAT.create()));
            nav.addItem(new SideNavItem("Ajustes", AjustesView.class, VaadinIcon.TOOLS.create()));
            nav.addItem(new SideNavItem("Ajustes Contrato", AjustesContrato.class, VaadinIcon.TOOLS.create()));
            nav.addItem(new SideNavItem("Consultas/Reclamaciones", ConsultasReclamacionesView.class, VaadinIcon.CHAT.create()));
        } else if (optionalCliente.get().getRoles().stream().anyMatch(role -> role.equals(Role.MARKETING))) {
            nav.addItem(new SideNavItem("Servicios", AboutView.class, VaadinIcon.SLIDERS.create()));
            nav.addItem(new SideNavItem("Tarifas", TarifaView.class, VaadinIcon.ABACUS.create()));
            nav.addItem(new SideNavItem("Noticias y promociones", NewsMarketingView.class, VaadinIcon.NEWSPAPER.create()));
        } else if (optionalCliente.get().getRoles().stream().anyMatch(role -> role.equals(Role.ATTENTION))) {
            nav.addItem(new SideNavItem("Alta de Contratos", ContratosNuevosView.class, VaadinIcon.INVOICE.create()));
            nav.addItem(new SideNavItem("Baja de Contratos", ContratosBajasView.class, VaadinIcon.INVOICE.create()));
            nav.addItem(new SideNavItem("Modificación de Contratos", ContratosModificacionView.class, VaadinIcon.INVOICE.create()));
            nav.addItem(new SideNavItem("Consultas pendientes", ConsultasView.class, VaadinIcon.CHAT.create()));
            nav.addItem(new SideNavItem("Cerrar Consultas", EliminarConsultaView.class, VaadinIcon.CHAT.create()));
        }
        else {
            nav.addItem(new SideNavItem("Facturacion", FacturacionView.class, VaadinIcon.INVOICE.create()));
            nav.addItem(new SideNavItem("Estadistica", AboutView.class, VaadinIcon.BAR_CHART_H.create()));
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<Cliente> optionalCliente = authenticatedUser.get();
        if (optionalCliente.isPresent()) {
            Cliente user = optionalCliente.get();

            Avatar avatar = new Avatar(user.getUsername());
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getUsername());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e ->
                    authenticatedUser.logout());

            layout.add(userMenu);
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

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    }

}
