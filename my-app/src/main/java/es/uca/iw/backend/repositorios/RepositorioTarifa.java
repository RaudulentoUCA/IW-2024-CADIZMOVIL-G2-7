package es.uca.iw.backend.repositorios;

import es.uca.iw.backend.clases.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositorioTarifa extends JpaRepository<Tarifa, Integer> {
    List<Tarifa> findAll();

    Optional<Tarifa> findByNombre(String nombre);

    Optional<Tarifa> findById(Long id);
}
