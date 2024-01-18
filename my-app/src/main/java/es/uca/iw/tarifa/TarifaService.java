package es.uca.iw.tarifa;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifaService {
    private final TarifaRepository tarifaRepository;

    @Autowired
    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    @Transactional
    public List<Tarifa> getAllTarifas(){
        return tarifaRepository.findAll();
    }

    @Transactional
    public Tarifa guardarTarifa(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    public Optional<Tarifa> cargarTarifaPorNombre(String nombre) {
        Optional<Tarifa> tarifa = tarifaRepository.findByNombre(nombre);
        return tarifa;
    }

    public Optional<Tarifa> getTarifaById(Long id) {
        Optional<Tarifa> tarifa = tarifaRepository.findById(id);
        return tarifa;
    }

    public void removeTarifa(Tarifa tarifa){
        tarifaRepository.delete(tarifa);
    }


}
