package es.uca.iw.views.client_views;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.custom_components.CustomNewsComponent;
import es.uca.iw.news.News;
import es.uca.iw.news.NewsServicio;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Route(value = "noticias", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Noticias y promociónes")

public class NoticiasView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    private final NewsServicio newsServicio;

    public NoticiasView(AuthenticatedUser authenticatedUser, NewsServicio newsServicio){
        this.authenticatedUser = authenticatedUser;
        this.newsServicio = newsServicio;


        FlexLayout contentLayout = new FlexLayout();
        contentLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        contentLayout.getStyle().set("flex-wrap", "wrap");
        contentLayout.getStyle().set("display", "flex");
        contentLayout.getStyle().set("width", "100%");
        contentLayout.getStyle().set("justify-content", "space-around");


        List<News> newsList = newsServicio.getAllNews();
        for (News news: newsList) {
            byte[] imageData = news.getImageData();
            StreamResource imgResource = new StreamResource("image.png", () -> new ByteArrayInputStream(imageData));
            CustomNewsComponent newNewsComponent = new CustomNewsComponent(news.getTitle(), news.getDescription(), imgResource);
            newNewsComponent.getStyle().set("width", "auto");
            contentLayout.add(newNewsComponent);
        }

        add(contentLayout);
    }
}