package es.uca.iw.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositorioCliente extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByIsActiveTrue();

    Optional<Cliente> findByDni(String dni);

    @Query("SELECT c FROM Cliente c WHERE c.nombre = ?1")
    Optional<Cliente> findByUsername(String username);
}
