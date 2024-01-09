package es.uca.iw.atencion_cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ServicioConsulta {
    private final RepositorioConsulta consultaRepository;

    @Autowired
    public ServicioConsulta(RepositorioConsulta consultaRepository){
        this.consultaRepository = consultaRepository;
    }

    @Transactional
    public List<Consulta> obtenerTodasLasConsultas() {
        return consultaRepository.findAll();
    }

    @Transactional
    public void eliminarConsulta(UUID consultaId) {
        consultaRepository.deleteById(consultaId);
    }
}

