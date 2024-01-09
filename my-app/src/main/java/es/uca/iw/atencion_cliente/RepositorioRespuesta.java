package es.uca.iw.atencion_cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RepositorioRespuesta extends JpaRepository<Respuesta, UUID> {
}