package es.uca.iw.views.client_views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;
@Route(value = "ajustes", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Ajustes")

public class AjustesView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    public AjustesView(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
    }
}