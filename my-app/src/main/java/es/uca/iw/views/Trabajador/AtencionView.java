package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.helloworld.HelloWorldView;
import java.util.Optional;

@PageTitle("Cádiz Móvil")
@Route(value = "atencion", layout = MainLayout.class)
@AnonymousAllowed
public class AtencionView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    public AtencionView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        Optional<Cliente> optionalCliente = authenticatedUser.get();

        Cliente cliente = null;

        if (optionalCliente.isPresent()) { cliente = optionalCliente.get();}

        H1 titulo = new H1("Sección de atención al cliente");

        H3 saludo = new H3("Bienvenido, ¡" + cliente.getNombre() + " " + cliente.getApellidos() + "!");

        H3 trabajo = new H3("Comience su jornada con una de las siguientes opciones");

        Button consultas = new Button("Consultas");
        consultas.addClickListener(event -> UI.getCurrent().navigate(ConsultasView.class));

        Button altas = new Button("Nuevas altas");
        altas.addClickListener(event -> UI.getCurrent().navigate(ContratosNuevosView.class));

        Button bajas = new Button("Gestión de bajas");
        bajas.addClickListener(event -> UI.getCurrent().navigate(ContratosBajasView.class));

        Button cierresesion = new Button("Cerrar sesión");
        cierresesion.addClickListener(event -> cerrarSesion());


        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(titulo, saludo, trabajo, new HorizontalLayout(consultas, altas, bajas), cierresesion);

    }

    private void cerrarSesion() {
        authenticatedUser.logout();
        UI.getCurrent().navigate(HelloWorldView.class);
    }
}