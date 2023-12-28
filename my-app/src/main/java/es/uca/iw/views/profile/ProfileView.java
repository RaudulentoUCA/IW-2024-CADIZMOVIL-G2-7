package es.uca.iw.views.profile;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.util.Optional;

@PageTitle("Cádiz Móvil")
@Route(value = "profile", layout = MainLayout.class)
@PermitAll
public class ProfileView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;

    public ProfileView(AuthenticatedUser authenticatedUser) {


        this.authenticatedUser = authenticatedUser;

        Optional<Cliente> optionalCliente = authenticatedUser.get();

        H1 h1 = new H1();
        setWidth("100%");
        getStyle().set("flex-grow", "1");

        h1.setText("Profile page");
        h1.setWidth("max-content");
        add(h1);

        optionalCliente.ifPresent(user ->
            add(new H1("Welcome, " + user.getNombre() + " " + user.getApellidos())));
    }
}