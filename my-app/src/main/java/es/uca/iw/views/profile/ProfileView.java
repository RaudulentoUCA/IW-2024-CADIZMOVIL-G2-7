package es.uca.iw.views.profile;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.Cliente.Cliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PageTitle("Profile")
@Route(value = "profile", layout = MainLayout.class)
@PermitAll
public class ProfileView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;

    public ProfileView(AuthenticatedUser authenticatedUser) {


        this.authenticatedUser = authenticatedUser;

        Optional<Cliente> maybeUser = authenticatedUser.get();

        H1 h1 = new H1();
        setWidth("100%");
        getStyle().set("flex-grow", "1");

        h1.setText("¡Hola!, + {userName}");
        h1.setWidth("max-content");
        add(h1);

        maybeUser.ifPresent(user -> {
            // Customize UI based on user information
            add(new H1("Welcome, " + user.getUsername()));
            // Add other components as needed
        });
    }
}