package es.uca.iw.backend.repositorios;

import es.uca.iw.backend.clases.Respuesta;
import es.uca.iw.backend.clases.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RepositorioRespuesta extends JpaRepository<Respuesta, UUID> {

    List<Respuesta> findByCliente(Cliente cliente);


}