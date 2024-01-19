package es.uca.iw.atencion_cliente;

import es.uca.iw.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
