package es.uca.iw.news;

import es.uca.iw.tarifa.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepositorio extends JpaRepository<News, Long> {
    List<News> findAll();

    Optional<News> findById(Long id);
}