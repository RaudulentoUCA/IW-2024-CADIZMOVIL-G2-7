package es.uca.iw.backend.servicios;

import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.clases.Contrato;
import es.uca.iw.backend.repositorios.RepositorioContrato;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioContrato {
    private final RepositorioContrato repositorioContrato;

    @Autowired
    public ServicioContrato(RepositorioContrato repositorioContrato) {
        this.repositorioContrato = repositorioContrato;
    }

    @Transactional
    public List<Contrato> getAllContratos() {
        return repositorioContrato.findAll();
    }

    @Transactional
    public List<Contrato> getContratosByCliente(Cliente cliente) {
        return repositorioContrato.findAllByCliente(cliente);
    }

    @Transactional
    public void eliminarContrato(int id) {
        repositorioContrato.deleteById(id);
    }

    @Transactional
    public void guardarContrato(Contrato contrato) {
        repositorioContrato.save(contrato);
    }
}
