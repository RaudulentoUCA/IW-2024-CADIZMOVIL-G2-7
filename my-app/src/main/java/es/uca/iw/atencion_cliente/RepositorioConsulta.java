package es.uca.iw.atencion_cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositorioConsulta extends JpaRepository<Consulta, UUID> {
    List<Consulta> findAll();

}
