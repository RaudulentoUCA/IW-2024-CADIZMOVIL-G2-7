package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.views.MainLayout;

@PageTitle("Cádiz Móvil")
@Route(value = "contratos/altas", layout = MainLayout.class)
@AnonymousAllowed
public class ContratosNuevosView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;
    private final TextField dni;
    private final TextField tarifa;

    public ContratosNuevosView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        H1 titulo = new H1("Gestión de altas de contratos");

        dni = new TextField();
        dni.setRequiredIndicatorVisible(true);
        dni.setLabel("DNI");

        tarifa = new TextField();
        tarifa.setRequiredIndicatorVisible(true);
        tarifa.setLabel("Tarifa");

        Button volver = new Button("Volver a tu página principal");
        volver.addClickListener(event -> UI.getCurrent().navigate(AtencionView.class));

        add(titulo, new HorizontalLayout(dni, tarifa), volver);
    }
}
