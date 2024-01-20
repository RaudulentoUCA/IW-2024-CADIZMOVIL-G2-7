package es.uca.iw.backend.servicios;
import es.uca.iw.backend.clases.Noticia;
import es.uca.iw.backend.repositorios.RepositorioNoticia;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ServicioNews {
    private final RepositorioNoticia repositorioNoticia;

    @Autowired
    public ServicioNews(RepositorioNoticia repositorioNoticia) {
        this.repositorioNoticia = repositorioNoticia;
    }

    @Transactional
    public List<Noticia> getAllNews(){
        return repositorioNoticia.findAll();
    }

    @Transactional
    public Noticia saveNews(Noticia noticia) {
        return repositorioNoticia.save(noticia);
    }

    public Optional<Noticia> findById(Long id) {
        Optional<Noticia> news = repositorioNoticia.findById(id);
        return news;
    }

    public void removeNews(Noticia noticia){
        repositorioNoticia.delete(noticia);
    }



}
