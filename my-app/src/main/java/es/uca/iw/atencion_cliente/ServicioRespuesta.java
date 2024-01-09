package es.uca.iw.atencion_cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ServicioRespuesta {

    private final RepositorioRespuesta respuestaRepository;

    @Autowired
    public ServicioRespuesta(RepositorioRespuesta respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }

    public Respuesta guardarRespuesta(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }
}