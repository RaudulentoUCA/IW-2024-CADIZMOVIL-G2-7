package es.uca.iw.Cliente;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ServiciosCliente {

    private final RepositorioCliente repositorio;

    @Autowired
    public ServiciosCliente(RepositorioCliente repositorio) {
        this.repositorio = repositorio;
    }

    public boolean registrarCliente(Cliente cliente) {
        String hashedPassword = BCrypt.hashpw(cliente.getPassword(), BCrypt.gensalt());
        cliente.setPassword(hashedPassword);
        try {
            repositorio.save(cliente);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }
}