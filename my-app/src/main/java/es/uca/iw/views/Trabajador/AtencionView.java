package es.uca.iw.views.profile;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import es.uca.iw.views.helloworld.HelloWorldView;
import java.util.Optional;

@PageTitle("Cádiz Móvil")
@Route(value = "atencion", layout = MainLayout.class)

public class AtencionView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    public AtencionView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        Optional<Cliente> optionalCliente = authenticatedUser.get();

        Cliente cliente = null;

        if (optionalCliente.isPresent()) { cliente = optionalCliente.get();}

        H1 titulo = new H1("Sección de atención al cliente");

        H3 saludo = new H3("Bienvenido, " + cliente.getNombre() + " " + cliente.getApellidos() + "!");

        Button cierresesion = new Button("Cerrar sesión");
        cierresesion.addClickListener(event -> cerrarSesion());

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(titulo, saludo, cierresesion);

    }

    private void cerrarSesion() {
        authenticatedUser.logout();
        UI.getCurrent().navigate(HelloWorldView.class);
    }
}