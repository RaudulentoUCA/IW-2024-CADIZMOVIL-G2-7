package es.uca.iw.backend.componentes;

import com.vaadin.flow.spring.security.AuthenticationContext;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class AuthenticatedUser {
    private final RepositorioUsuario repositorioUsuario;

    private final AuthenticationContext authenticationContext;

    @Autowired
    public AuthenticatedUser(RepositorioUsuario repositorioUsuario, AuthenticationContext authenticationContext) {
        this.repositorioUsuario = repositorioUsuario;
        this.authenticationContext = authenticationContext;
    }

    @Transactional
    public Optional<Cliente> get() {
        return authenticationContext.getAuthenticatedUser(Cliente.class)
                .map(userDetails -> repositorioUsuario.findByEmail(userDetails.getEmail()).get());
    }

    public void logout() {
        authenticationContext.logout();
    }

}