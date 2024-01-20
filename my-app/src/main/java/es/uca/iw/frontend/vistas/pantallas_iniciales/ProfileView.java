package es.uca.iw.frontend.vistas.pantallas_iniciales;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.componentes.AuthenticatedUser;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.clases.Role;
import es.uca.iw.frontend.vistas.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.Optional;

@PageTitle("Cádiz Móvil")
@Route(value = "profile", layout = MainLayout.class)
@PermitAll
public class ProfileView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;

    public ProfileView(AuthenticatedUser authenticatedUser) {

        this.authenticatedUser = authenticatedUser;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Optional<Cliente> optionalCliente = authenticatedUser.get();

        optionalCliente.ifPresent(cliente -> {
            H3 saludo = new H3("Bienvenido, ¡" + cliente.getNombre() + " " + cliente.getApellidos() + "!");
            add(saludo);

            Image img = new Image("images/puente-cadiz.jpg", "foto puente");
            img.addClassName("img");

            if (cliente.getRoles().stream().anyMatch(role -> role.equals(Role.MARKETING))) {
                H1 titulo = new H1("Sección de Marketing");
                H3 trabajo = new H3("Comience su jornada mientras disfruta de las vistas de nuestra maravillosa Ciudad");
                setWidth("100%");
                getStyle().set("flex-grow", "1");
                add(titulo, trabajo);
            } else if (cliente.getRoles().stream().anyMatch(role -> role.equals(Role.ATTENTION))) {
                H1 titulo = new H1("Sección de Atención al Cliente");
                H3 trabajo = new H3("Comience su jornada mientras disfruta de las vistas de nuestra maravillosa Ciudad");
                setWidth("100%");
                getStyle().set("flex-grow", "1");
                add(titulo, trabajo);
            } else if (cliente.getRoles().stream().anyMatch(role -> role.equals(Role.FINANCE))) {
                H1 titulo = new H1("Sección de Finanzas");
                H3 trabajo = new H3("Comience su jornada mientras disfruta de las vistas de nuestra maravillosa Ciudad");
                setWidth("100%");
                getStyle().set("flex-grow", "1");
                add(titulo, trabajo);
            } else {
                H1 titulo = new H1("Sección de Clientes");
                H3 publi = new H3("Navegue por nuestra web y descubra las mejores ofertas");
                setWidth("100%");
                getStyle().set("flex-grow", "1");
                add(titulo, publi);
            }
            add(img);
        });

    }
}