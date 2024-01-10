package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.helloworld.HelloWorldView;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@PageTitle("Cádiz Móvil")
@Route(value = "atencion", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class AtencionView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    public AtencionView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        Optional<Cliente> optionalUsuario = authenticatedUser.get();

        Cliente usuario = null;

        if (optionalUsuario.isPresent()) { usuario = optionalUsuario.get();}

        H1 titulo = new H1("Sección de atención al cliente");

        H3 saludo = new H3("Bienvenido, ¡" + usuario.getNombre() + " " + usuario.getApellidos() + "!");

        H3 trabajo = new H3("Comience su jornada mientras disfruta de las vistas de nuestra maravillosa Ciudad");

        Image img = new Image("images/puente-cadiz.jpg", "foto puente");
        img.addClassName("img");

        Button cierresesion = new Button("Cerrar sesión");
        cierresesion.addClickListener(event -> cerrarSesion());


        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(titulo, saludo, trabajo, img, cierresesion);

    }

    private void cerrarSesion() {
        authenticatedUser.logout();
        UI.getCurrent().navigate(HelloWorldView.class);
    }
}