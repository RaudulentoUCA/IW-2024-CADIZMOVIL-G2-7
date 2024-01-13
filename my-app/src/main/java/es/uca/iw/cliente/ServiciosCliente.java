package es.uca.iw.cliente;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        cliente.addRole(Role.MARKETING);
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
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user present with username: " + email);
        } else {
            return user.get();
        }
    }

    @Transactional
    public Optional<Cliente> cargarUsuarioPorEmail(String email) throws UsernameNotFoundException {
        return repositorio.findByEmail(email);
    }

    public void eliminar(Cliente user) {
        repositorio.delete(user);
    }

    @Transactional
    public List<Cliente> getAllClientes(){
        return repositorio.findAll();
    }

    @Transactional
    public boolean actualizarDatosDelCliente(UUID clientId, String newName, String newSurname, LocalDate newBirthDate,String newDNI, String newContactNumber) {
        Optional<Cliente> optionalCliente = repositorio.findById(clientId);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setNombre(newName);
            cliente.setApellidos(newSurname);
            cliente.setFechaDeNacimiento(newBirthDate);
            cliente.setDni(newDNI);
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

    @Transactional
    public Optional<Cliente> findByDni(String dni) {
        return repositorio.findByDni(dni);
    }
}