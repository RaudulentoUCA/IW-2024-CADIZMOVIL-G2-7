package es.uca.iw.backend.servicios;

import es.uca.iw.backend.clases.Consulta;
import es.uca.iw.backend.repositorios.RepositorioConsulta;
import es.uca.iw.backend.clases.Cliente;
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
    public void eliminarConsulta(UUID consultaId) { repositorioConsulta.deleteById(consultaId); }

    @Transactional
    public List<Consulta> getConsultasByCliente(Cliente cliente) {
        return repositorioConsulta.findByCliente(cliente);
    }

    @Transactional
    public void guardarConsulta(Consulta consulta) {
        repositorioConsulta.save(consulta);
    }

    @Transactional
    public List<Consulta> obtenerConsultasNoRespondidas() {
        return repositorioConsulta.findByRespondidoFalse();
    }

    @Transactional
    public Consulta getConsultaById(UUID id) {
        return repositorioConsulta.findById(id).orElse(null);
    }


}

