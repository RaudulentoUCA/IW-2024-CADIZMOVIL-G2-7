package es.uca.iw.factura;

import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiciosFactura {
    private final RepositorioFactura repositorioFactura;

    @Autowired
    public ServiciosFactura(RepositorioFactura repositorioFactura) {
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
