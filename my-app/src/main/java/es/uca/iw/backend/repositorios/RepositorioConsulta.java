package es.uca.iw.backend.repositorios;

import es.uca.iw.backend.clases.Consulta;
import es.uca.iw.backend.clases.Respuesta;
import es.uca.iw.backend.clases.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositorioConsulta extends JpaRepository<Consulta, UUID> {
    List<Consulta> findAll();
    List<Consulta> findByCliente(Cliente cliente);

    List<Consulta> findByRespondidoFalse();

    Optional<Respuesta> findRespuestaById(UUID consultaId);

}
