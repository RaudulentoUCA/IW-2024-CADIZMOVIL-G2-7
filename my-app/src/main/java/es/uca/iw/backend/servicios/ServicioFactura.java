package es.uca.iw.backend.servicios;

import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.clases.Factura;
import es.uca.iw.backend.repositorios.RepositorioFactura;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioFactura {
    private final RepositorioFactura repositorioFactura;

    @Autowired
    public ServicioFactura(RepositorioFactura repositorioFactura) {
        this.repositorioFactura = repositorioFactura;
    }

    @Transactional
    public List<Factura> getAllFacturas() {
        return repositorioFactura.findAll();
    }

    @Transactional
    public List<Factura> getFacturasByCliente(Cliente cliente) {
        return repositorioFactura.findAllByCliente(cliente);
    }

    public boolean registrarFactura(Factura factura) {
        try {
            repositorioFactura.save(factura);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    public byte[] getFacturabyId(Long id) {
        return repositorioFactura.findAllById(id);
    }
}
