package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.news.News;
import es.uca.iw.news.NewsServicio;
import es.uca.iw.tarifa.Tarifa;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@PageTitle("Manejar noticias")
@Route(value = "marketing/noticias", layout = MainLayout.class)
@RolesAllowed("MARKETING")
public class NewsMarketingView extends VerticalLayout {
    NewsServicio newsServicio;


    public NewsMarketingView(NewsServicio newsServicio) {
        this.newsServicio = newsServicio;

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");

        TextField nombreField = new TextField("Nombre");
        nombreField.setRequired(true);
        nombreField.setRequiredIndicatorVisible(true);

        TextArea descriptionField = new TextArea("Description");
        descriptionField.setRequired(true);
        descriptionField.setRequiredIndicatorVisible(true);
        descriptionField.setMaxLength(255);


        Button createNewNewsButton = new Button("Añadir");

        createNewNewsButton.addClickListener(buttonClickEvent -> {
            if (nombreField.isEmpty()) {
                Notification notification = new Notification("Escribe el nombre por favor.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.TOP_CENTER);
                notification.setDuration(3000);
                notification.open();
            } else if (descriptionField.isEmpty()) {
                Notification notification = new Notification("Escribe descripción por favor.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.TOP_CENTER);
                notification.setDuration(3000);
                notification.open();
            } else if (buffer.getFiles().isEmpty()) {
                Notification notification = new Notification("Añade el fichero por favor.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setPosition(Notification.Position.TOP_CENTER);
                notification.setDuration(3000);
                notification.open();
            } else {
                News news = new News();
                news.setTitle(nombreField.getValue());
                news.setDescription(descriptionField.getValue());
                String fileName = buffer.getFiles().iterator().next();
                try (InputStream inputStream = buffer.getInputStream(fileName)) {
                    byte[] fileBytes = inputStream.readAllBytes();
                    news.setImageData(fileBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                news.setCreationDate(LocalDate.now());
                newsServicio.saveNews(news);
                Notification notification = new Notification("Noticia está guardada correctamente");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setDuration(3000);
                notification.open();
            }
        });
        add(new H3("Añadir noticias o promociones"), nombreField, descriptionField, upload, createNewNewsButton);

        add(new H3("Manejar noticias"));
        List<News> allNews = newsServicio.getAllNews();
        Grid<News> newsGrid = new Grid<>();
        newsGrid.setAllRowsVisible(true);
        newsGrid.addColumn(News::getId).setHeader("№");
        newsGrid.addColumn(News::getTitle).setHeader("Titulo");
        newsGrid.addColumn(News::getDescription).setHeader("Descripción");
        newsGrid.addColumn(news -> news.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setHeader("Creación");
        newsGrid.addColumn(
                new ComponentRenderer<>(Button::new, (button, news) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        eliminarNoticias(news);
                        actualizarGrid(newsGrid);
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("");
        newsGrid.setItems(allNews);
        add(newsGrid);
    }

    private void eliminarNoticias(News news) {
        if (news != null) {
            Optional<News> tarifaToDelete = newsServicio.findById(news.getId());
            tarifaToDelete.ifPresent(newsServicio::removeNews);
            Notification.show("Tarifa eliminada correctamente");
        }
    }

    private void actualizarGrid(Grid<News> newsGrid) {
        List<News> updatedNews = newsServicio.getAllNews();
        newsGrid.setItems(updatedNews);
    }
}
