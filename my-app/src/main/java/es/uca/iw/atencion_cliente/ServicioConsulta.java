package es.uca.iw.atencion_cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioConsulta {
    private final RepositorioConsulta consultaRepository;

    @Autowired
    public ServicioConsulta(RepositorioConsulta consultaRepository){
        this.consultaRepository = consultaRepository;
    }

    @Transactional
    public void cerrarConsulta(Consulta consulta) {
        consultaRepository.delete(consulta);
    }
}
