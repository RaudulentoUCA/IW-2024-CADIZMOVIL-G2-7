package es.uca.iw.backend.servicios;

import es.uca.iw.backend.clases.Respuesta;
import es.uca.iw.backend.repositorios.RepositorioRespuesta;
import es.uca.iw.backend.clases.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioRespuesta {

    private final RepositorioRespuesta respuestaRepository;

    @Autowired
    public ServicioRespuesta(RepositorioRespuesta respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }

    @Transactional
    public void guardarRespuesta(Respuesta respuesta) {
        respuestaRepository.save(respuesta);
    }

    @Transactional
    public List<Respuesta> getRespuestasByCliente(Cliente cliente) {
        return respuestaRepository.findByCliente(cliente);
    }
}
