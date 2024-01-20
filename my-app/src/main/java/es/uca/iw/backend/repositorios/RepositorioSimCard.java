package es.uca.iw.backend.repositorios;

import es.uca.iw.backend.clases.Contrato;
import es.uca.iw.backend.clases.SimCard;
import es.uca.iw.backend.clases.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositorioSimCard extends JpaRepository<SimCard, Integer> {

    Optional<SimCard> findByNumber(Integer number);

    List<SimCard> findAll();

    List<SimCard> findAllByContrato(Contrato contrato);

    Optional<SimCard> findByTarifa(Tarifa tarifa);


}
