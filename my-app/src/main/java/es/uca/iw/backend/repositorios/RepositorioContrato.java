package es.uca.iw.backend.repositorios;

import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.clases.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioContrato extends JpaRepository<Contrato, Integer> {

    List<Contrato> findAll();

    List<Contrato> findAllByCliente(Cliente cliente);

    void deleteById(int id);
}