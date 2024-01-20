package es.uca.iw.frontend.vistas.cliente;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import es.uca.iw.backend.componentes.AuthenticatedUser;
import es.uca.iw.frontend.custom_components.CustomNewsComponent;
import es.uca.iw.backend.clases.Noticia;
import es.uca.iw.backend.servicios.ServicioNews;
import es.uca.iw.frontend.vistas.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.io.ByteArrayInputStream;
import java.util.List;

@Route(value = "noticias", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Noticias y promociónes")

public class NoticiasView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    private final ServicioNews servicioNews;

    public NoticiasView(AuthenticatedUser authenticatedUser, ServicioNews servicioNews){
        this.authenticatedUser = authenticatedUser;
        this.servicioNews = servicioNews;


        FlexLayout contentLayout = new FlexLayout();
        contentLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        contentLayout.getStyle().set("flex-wrap", "wrap");
        contentLayout.getStyle().set("display", "flex");
        contentLayout.getStyle().set("width", "100%");
        contentLayout.getStyle().set("justify-content", "space-around");


        List<Noticia> noticiaList = servicioNews.getAllNews();
        for (Noticia noticia : noticiaList) {
            byte[] imageData = noticia.getImageData();
            StreamResource imgResource = new StreamResource("image.png", () -> new ByteArrayInputStream(imageData));
            CustomNewsComponent newNewsComponent = new CustomNewsComponent(noticia.getTitle(), noticia.getDescription(), imgResource);
            newNewsComponent.getStyle().set("width", "auto");
            contentLayout.add(newNewsComponent);
        }

        add(contentLayout);
    }
}