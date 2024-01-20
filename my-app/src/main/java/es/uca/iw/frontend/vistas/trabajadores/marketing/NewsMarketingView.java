package es.uca.iw.frontend.vistas.trabajadores.marketing;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.clases.Noticia;
import es.uca.iw.backend.servicios.ServicioNews;
import es.uca.iw.frontend.vistas.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@PageTitle("Manejar noticias")
@Route(value = "marketing/noticias", layout = MainLayout.class)
@RolesAllowed("MARKETING")
public class NewsMarketingView extends VerticalLayout {
    ServicioNews servicioNews;


    public NewsMarketingView(ServicioNews servicioNews) {
        this.servicioNews = servicioNews;

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

        Grid<Noticia> newsGrid = new Grid<>();

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
                Noticia noticia = new Noticia();
                noticia.setTitle(nombreField.getValue());
                noticia.setDescription(descriptionField.getValue());
                String fileName = buffer.getFiles().iterator().next();
                try (InputStream inputStream = buffer.getInputStream(fileName)) {
                    byte[] fileBytes = inputStream.readAllBytes();
                    noticia.setImageData(fileBytes);
                } catch (IOException e) {
                    Notification.show("Error with reading image file occurred.");
                }
                noticia.setCreationDate(LocalDate.now());
                servicioNews.saveNews(noticia);
                Notification notification = new Notification("Noticia está guardada correctamente");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setDuration(3000);
                notification.open();
                actualizarGrid(newsGrid);

            }
        });
        add(new H3("Añadir noticias o promociones"), nombreField, descriptionField, upload, createNewNewsButton);

        add(new H3("Manejar noticias"));
        List<Noticia> allNews = servicioNews.getAllNews();
        newsGrid.setAllRowsVisible(true);
        newsGrid.addColumn(Noticia::getId).setHeader("№");
        newsGrid.addColumn(Noticia::getTitle).setHeader("Titulo");
        newsGrid.addColumn(Noticia::getDescription).setHeader("Descripción");
        newsGrid.addColumn(noticia -> noticia.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setHeader("Creación");
        newsGrid.addColumn(
                new ComponentRenderer<>(Button::new, (button, noticia) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        eliminarNoticias(noticia);
                        actualizarGrid(newsGrid);
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setHeader("");
        newsGrid.setItems(allNews);
        add(newsGrid);
    }

    private void eliminarNoticias(Noticia noticia) {
        if (noticia != null) {
            Optional<Noticia> tarifaToDelete = servicioNews.findById(noticia.getId());
            tarifaToDelete.ifPresent(servicioNews::removeNews);
            Notification.show("Noticias eliminado correctamente");
        }
    }

    private void actualizarGrid(Grid<Noticia> newsGrid) {
        List<Noticia> updatedNews = servicioNews.getAllNews();
        newsGrid.setItems(updatedNews);
    }
}
