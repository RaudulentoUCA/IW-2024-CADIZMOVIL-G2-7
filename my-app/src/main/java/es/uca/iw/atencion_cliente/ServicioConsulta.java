package es.uca.iw.atencion_cliente;

import es.uca.iw.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ServicioConsulta {
    private final RepositorioConsulta repositorioConsulta;

    @Autowired
    public ServicioConsulta(RepositorioConsulta repositorioConsulta){
        this.repositorioConsulta = repositorioConsulta;
    }

    @Transactional
    public List<Consulta> obtenerTodasLasConsultas() {
        return repositorioConsulta.findAll();
    }

    @Transactional
    public void eliminarConsulta(UUID consultaId) {
        repositorioConsulta.deleteById(consultaId);
    }

    @Transactional
    public List<Consulta> getConsultasByCliente(Cliente cliente) {
        return repositorioConsulta.findByCliente(cliente);
    }

    @Transactional
    public void guardarConsulta(Consulta consulta) {
        repositorioConsulta.save(consulta);
    }
}

