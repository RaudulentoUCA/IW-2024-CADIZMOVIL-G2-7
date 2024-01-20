package es.uca.iw.backend.repositorios;

import es.uca.iw.backend.clases.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioNoticia extends JpaRepository<Noticia, Long> {
    List<Noticia> findAll();

    Optional<Noticia> findById(Long id);
}