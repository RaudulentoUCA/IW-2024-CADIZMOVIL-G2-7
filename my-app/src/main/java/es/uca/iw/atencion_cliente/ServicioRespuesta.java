package es.uca.iw.atencion_cliente;

import es.uca.iw.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Respuesta> getRespuestasByCliente(Cliente cliente) {
        return respuestaRepository.findByCliente(cliente);
    }
}
