package es.uca.iw.backend.repositorios;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.clases.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioFactura extends JpaRepository<Factura, Long> {

    List<Factura> findAll();

    List<Factura> findAllByCliente(Cliente cliente);

    byte[] findAllById(Long id);
}
