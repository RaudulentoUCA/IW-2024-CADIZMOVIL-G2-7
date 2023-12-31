package es.uca.iw.Contrato;

import es.uca.iw.cliente.Cliente;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Contrato> getContratosByCliente(Cliente cliente) {
        return repositorioContrato.findByCliente(cliente);
    }
}
