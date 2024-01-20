package es.uca.iw.backend.servicios;

import es.uca.iw.backend.clases.SimCard;
import es.uca.iw.backend.repositorios.RepositorioSimCard;
import es.uca.iw.backend.clases.Tarifa;
import es.uca.iw.backend.clases.Contrato;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ServicioSimCard {
    private final RepositorioSimCard repositorioSimCard;

    @Autowired
    public ServicioSimCard(RepositorioSimCard repositorioSimCard) {
        this.repositorioSimCard = repositorioSimCard;
    }

    @Transactional
    public SimCard createSimCard(Integer number, Tarifa tarifa, Contrato contrato) {
        SimCard simCard = new SimCard();
        simCard.setNumber(number);
        simCard.setTarifa(tarifa);
        simCard.setContrato(contrato);
        simCard.setActive(true);
        simCard.setUsedMb(0);
        simCard.setUsedSms(0);
        simCard.setUsedMinutes(0);
        return repositorioSimCard.save(simCard);
    }

    @Transactional
    public Optional<SimCard> getSimCardByNumber(Integer number) {
        return repositorioSimCard.findByNumber(number);
    }

    @Transactional
    public List<SimCard> getSimCardsByContrato(Contrato contrato) {
        return repositorioSimCard.findAllByContrato(contrato);
    }

    public Optional<SimCard> getSimCardsByTarifa(Tarifa tarifa) {
        return repositorioSimCard.findByTarifa(tarifa);
    }

    @Transactional
    public List<SimCard> getAllSimCards() {
        return repositorioSimCard.findAll();
    }

    @Transactional
    public void changePlan(Integer number, Tarifa tarifa) {
        Optional<SimCard> optionalSimCard = repositorioSimCard.findByNumber(number);

        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            simCard.setTarifa(tarifa);
            repositorioSimCard.save(simCard);
        }
    }

    @Transactional
    public void deactivateSimCard(Integer number) {
        Optional<SimCard> optionalSimCard = repositorioSimCard.findByNumber(number);

        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            simCard.setActive(false);
            repositorioSimCard.save(simCard);
        }
    }

    @Transactional
    public void addUsedMinutes(Integer number, int minutesToAdd) {
        Optional<SimCard> optionalSimCard = repositorioSimCard.findByNumber(number);

        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            simCard.setUsedMinutes(simCard.getUsedMinutes() + minutesToAdd);
            repositorioSimCard.save(simCard);
        }
    }

    @Transactional
    public void addUsedMb(Integer number, int mbToAdd) {
        Optional<SimCard> optionalSimCard = repositorioSimCard.findByNumber(number);

        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            simCard.setUsedMb(simCard.getUsedMb() + mbToAdd);
            repositorioSimCard.save(simCard);
        }
    }

    @Transactional
    public void addUsedSms(Integer number, int smsToAdd) {
        Optional<SimCard> optionalSimCard = repositorioSimCard.findByNumber(number);

        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            simCard.setUsedSms(simCard.getUsedSms() + smsToAdd);
            repositorioSimCard.save(simCard);
        }
    }

    @Transactional
    public void resetUsage(Integer number) {
        Optional<SimCard> optionalSimCard = repositorioSimCard.findByNumber(number);

        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            simCard.setUsedMinutes(0);
            simCard.setUsedMb(0);
            simCard.setUsedSms(0);
            repositorioSimCard.save(simCard);
        }
    }

    @Transactional
    public Set<Long> getUniqueTarifaIds() {
        Set<Long> uniqueTarifaIds = new HashSet<>();

        List<SimCard> allSimCards = repositorioSimCard.findAll();

        for (SimCard simCard : allSimCards) {
            Tarifa tarifa = simCard.getTarifa();
            if (tarifa != null) {
                uniqueTarifaIds.add(tarifa.getId());
            }
        }

        return uniqueTarifaIds;
    }

}