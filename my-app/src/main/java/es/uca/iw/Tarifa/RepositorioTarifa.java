package es.uca.iw.Tarifa;

import es.uca.iw.Cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioTarifa extends JpaRepository<Tarifa, Long> {

}
