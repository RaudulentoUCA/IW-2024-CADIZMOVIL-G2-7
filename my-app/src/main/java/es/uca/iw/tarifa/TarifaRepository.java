package es.uca.iw.tarifa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
    List<Tarifa> findAll();
}
