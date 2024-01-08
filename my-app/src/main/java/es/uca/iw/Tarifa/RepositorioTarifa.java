package es.uca.iw.Tarifa;

import es.uca.iw.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioTarifa extends JpaRepository<Tarifa, Long> {
    Optional<Tarifa> findByNombre(String nombre);
}
