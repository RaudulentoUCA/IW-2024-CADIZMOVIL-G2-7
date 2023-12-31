package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.views.MainLayout;

@PageTitle("Cádiz Móvil")
@Route(value = "consultas", layout = MainLayout.class)
@AnonymousAllowed
public class ConsultasView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    public ConsultasView(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
        H1 titulo = new H1("Consultas pendientes de clientes");
        add(titulo);
    }
}
