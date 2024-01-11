package es.uca.iw.contrato;

import es.uca.iw.cliente.Cliente;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosContrato {
    private final RepositorioContrato repositorioContrato;

    @Autowired
    public ServiciosContrato(RepositorioContrato repositorioContrato) {
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
