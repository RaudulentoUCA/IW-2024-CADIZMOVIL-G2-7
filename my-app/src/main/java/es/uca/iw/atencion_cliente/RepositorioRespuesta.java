package es.uca.iw.atencion_cliente;

import es.uca.iw.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RepositorioRespuesta extends JpaRepository<Respuesta, UUID> {

    List<Respuesta> findByCliente(Cliente cliente);
}