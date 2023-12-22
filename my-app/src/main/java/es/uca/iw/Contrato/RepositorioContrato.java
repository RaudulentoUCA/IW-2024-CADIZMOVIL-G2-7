package es.uca.iw.Contrato;

import es.uca.iw.Cliente.Cliente;
import es.uca.iw.SimCard.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepositorioContrato extends JpaRepository<Contrato, Integer> {

    List<Contrato> findAll();

    Optional<Contrato> findByCliente(Cliente cliente);
}
