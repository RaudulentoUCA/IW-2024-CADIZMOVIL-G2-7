package es.uca.iw.news;
import es.uca.iw.tarifa.Tarifa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class NewsServicio {
    private final NewsRepositorio newsRepositorio;

    @Autowired
    public NewsServicio(NewsRepositorio newsRepositorio) {
        this.newsRepositorio = newsRepositorio;
    }

    @Transactional
    public List<News> getAllNews(){
        return newsRepositorio.findAll();
    }

    @Transactional
    public News saveNews(News news) {
        return newsRepositorio.save(news);
    }

    public Optional<News> findById(Long id) {
        Optional<News> news = newsRepositorio.findById(id);
        return news;
    }

    public void removeNews(News news){
        newsRepositorio.delete(news);
    }



}
