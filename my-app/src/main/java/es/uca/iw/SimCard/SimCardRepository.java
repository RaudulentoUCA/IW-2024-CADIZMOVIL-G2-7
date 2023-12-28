package es.uca.iw.simcard;

import es.uca.iw.Contrato.Contrato;
import es.uca.iw.Tarifa.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SimCardRepository extends JpaRepository<SimCard, Integer> {

    Optional<SimCard> findByNumber(Integer number);

    List<SimCard> findAll();

    Optional<SimCard> findByContrato(Contrato contrato);
    Optional<SimCard> findByTarifa(Tarifa tarifa);
}
