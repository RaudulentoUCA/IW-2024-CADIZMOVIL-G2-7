package es.uca.iw.Tarifa;

import es.uca.iw.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiciosTarifa {

    private final RepositorioTarifa repositorio;

    @Autowired
    public ServiciosTarifa(RepositorioTarifa repositorio) {
        this.repositorio = repositorio;
    }

    // Obtener todas las tarifas
    public List<Tarifa> obtenerTodasLasTarifas() {
        return repositorio.findAll();
    }

    // Obtener una tarifa por su ID
    public Optional<Tarifa> obtenerTarifaPorId(Long id) {
        return repositorio.findById(id);
    }

    // Guardar una tarifa
    public Tarifa guardarTarifa(Tarifa tarifa) {
        return repositorio.save(tarifa);
    }

    // Eliminar una tarifa por su ID
    public void eliminarTarifaPorId(Long id) {
        repositorio.deleteById(id);
    }

    public Optional<Tarifa> cargarTarifaPorNombre(String nombre) {
        Optional<Tarifa> tarifa = repositorio.findByNombre(nombre);
        return tarifa;
    }
}