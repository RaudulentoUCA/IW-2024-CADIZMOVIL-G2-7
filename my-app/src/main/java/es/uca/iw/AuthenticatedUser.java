package es.uca.iw;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.spring.security.AuthenticationContext;
import es.uca.iw.Cliente.Cliente;
import es.uca.iw.Cliente.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class AuthenticatedUser {
    private final RepositorioCliente repositorioCliente;

    private final AuthenticationContext authenticationContext;

    @Autowired
    public AuthenticatedUser(RepositorioCliente repositorioCliente, AuthenticationContext authenticationContext) {
        this.repositorioCliente = repositorioCliente;
        this.authenticationContext = authenticationContext;
    }

    @Transactional
    public Optional<Cliente> get() {
        return authenticationContext.getAuthenticatedUser(Cliente.class)
                .map(userDetails -> repositorioCliente.findByEmail(userDetails.getEmail()).get());
    }

    public void logout() {
        authenticationContext.logout();
    }

}