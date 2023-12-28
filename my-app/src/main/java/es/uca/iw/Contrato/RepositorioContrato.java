package es.uca.iw.Contrato;

import es.uca.iw.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositorioContrato extends JpaRepository<Contrato, Integer> {

    List<Contrato> findAll();

    Optional<Contrato> findByCliente(Cliente cliente);
}
