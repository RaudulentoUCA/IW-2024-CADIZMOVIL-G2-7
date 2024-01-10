package es.uca.iw.cliente;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiciosCliente implements UserDetailsService {

    private final RepositorioCliente repositorio;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ServiciosCliente(RepositorioCliente repositorio, PasswordEncoder
            passwordEncoder) {
        this.repositorio = repositorio;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registrarCliente(Cliente cliente) {
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        cliente.setActive(true);
        cliente.addRole(Role.USER);
        try {
            repositorio.save(cliente);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Cliente> user = repositorio.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("No user present with username: " + email);
        } else {
            return user.get();
        }
    }

    public Optional<Cliente> cargarUsuarioPorEmail(String email) throws UsernameNotFoundException {
        Optional<Cliente> user = repositorio.findByEmail(email);
        return user;
    }

    public void eliminar(Cliente user) {
        repositorio.delete(user);
    }

    @Transactional
    public List<Cliente> getAllClientes(){
        return repositorio.findAll();
    }

    @Transactional
    public boolean actualizarContactNumber(UUID clientId, String newContactNumber) {
        Optional<Cliente> optionalCliente = repositorio.findById(clientId);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setNumeroContacto(newContactNumber);
            try {
                repositorio.save(cliente);
                return true;
            } catch (DataIntegrityViolationException e) {
                // Handle exception if needed
                return false;
            }
        }
        return false;
    }
}