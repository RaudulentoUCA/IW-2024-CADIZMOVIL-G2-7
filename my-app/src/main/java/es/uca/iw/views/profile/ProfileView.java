package es.uca.iw.views.profile;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.Role;
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.helloworld.HelloWorldView;
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

        Cliente usuario = optionalCliente.get();
        H3 saludo = new H3("Bienvenido, ¡" + usuario.getNombre() + " " + usuario.getApellidos() + "!");
        add(saludo);

        Image img = new Image("images/puente-cadiz.jpg", "foto puente");
        img.addClassName("img");

        Button cierresesion = new Button("Cerrar sesión");
        cierresesion.addClickListener(event -> cerrarSesion());

        if (usuario.getRoles().stream().anyMatch(role -> role.equals(Role.MARKETING))){
            H1 titulo = new H1("Sección de Marketing");
            H3 trabajo = new H3("Comience su jornada mientras disfruta de las vistas de nuestra maravillosa Ciudad");
            setWidth("100%");
            getStyle().set("flex-grow", "1");
            add(titulo, trabajo);
        }else if (usuario.getRoles().stream().anyMatch(role -> role.equals(Role.ATTENTION))){
            H1 titulo = new H1("Sección de Marketing");
            H3 trabajo = new H3("Comience su jornada mientras disfruta de las vistas de nuestra maravillosa Ciudad");
            setWidth("100%");
            getStyle().set("flex-grow", "1");
            add(titulo, trabajo);
        }else if (usuario.getRoles().stream().anyMatch(role -> role.equals(Role.FINANCE))) {
            H1 titulo = new H1("Sección de Marketing");
            H3 trabajo = new H3("Comience su jornada mientras disfruta de las vistas de nuestra maravillosa Ciudad");
            setWidth("100%");
            getStyle().set("flex-grow", "1");
            add(titulo, trabajo);
        }else{
            H1 titulo = new H1("Sección de Clientes");
            H3 publi = new H3("Navegue por nuestra web y descubra las mejores ofertas");
            setWidth("100%");
            getStyle().set("flex-grow", "1");
            add(titulo, publi);
        }
        add(img, cierresesion);
    }

    private void cerrarSesion() {
        authenticatedUser.logout();
        UI.getCurrent().navigate(HelloWorldView.class);
    }
}