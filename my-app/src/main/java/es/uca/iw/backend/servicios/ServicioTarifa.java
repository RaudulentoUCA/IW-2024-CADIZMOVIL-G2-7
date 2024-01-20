package es.uca.iw.backend.servicios;

import es.uca.iw.backend.clases.Tarifa;
import es.uca.iw.backend.repositorios.RepositorioTarifa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioTarifa {
    private final RepositorioTarifa repositorioTarifa;

    @Autowired
    public ServicioTarifa(RepositorioTarifa repositorioTarifa) {
        this.repositorioTarifa = repositorioTarifa;
    }

    @Transactional
    public List<Tarifa> getAllTarifas() {
        return repositorioTarifa.findAll();
    }

    @Transactional
    public Tarifa guardarTarifa(Tarifa tarifa) {
        return repositorioTarifa.save(tarifa);
    }

    public Optional<Tarifa> getTarifaById(Long id) {
        return repositorioTarifa.findById(id);
    }

    public Optional<Tarifa> getTarifaByNombre(String nombre) {
        return repositorioTarifa.findByNombre(nombre);
    }

    public void removeTarifa(Tarifa tarifa) {
        repositorioTarifa.delete(tarifa);
    }

}
