package es.uca.iw.tarifa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
    List<Tarifa> findAll();

    Optional<Tarifa> findByNombre(String nombre);
}
