package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.atencion_cliente.Consulta;
import es.uca.iw.atencion_cliente.RepositorioConsulta;
import es.uca.iw.views.MainLayout;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;
import java.util.UUID;

@PageTitle("Cádiz Móvil")
@Route(value = "cerrarConsulta", layout = MainLayout.class)
@AnonymousAllowed
public class CerrarConsultaView extends VerticalLayout {

    private final RepositorioConsulta consultaRepository;

    public CerrarConsultaView(RepositorioConsulta consultaRepository) {
        this.consultaRepository = consultaRepository;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        FormLayout formLayout = new FormLayout();

        TextField idField = new TextField("ID de la consulta");
        Button cerrarButton = new Button("Cerrar consulta");
        cerrarButton.setWidth("150px");

        cerrarButton.addClickListener(event -> cerrarConsulta(idField.getValue()));

        formLayout.add(idField, cerrarButton);
        add(formLayout);
    }

    private void cerrarConsulta(String consultaId) {
        if (!consultaId.isEmpty()) {
            try {
                UUID uuid = UUID.fromString(consultaId);
                Optional<Consulta> optionalConsulta = consultaRepository.findById(uuid);

                if (optionalConsulta.isPresent()) {
                    Consulta consulta = optionalConsulta.get();
                    consultaRepository.delete(consulta);
                    Notification.show("Consulta cerrada con éxito");
                    // Puedes agregar más acciones después de cerrar la consulta si es necesario
                } else {
                    Notification.show("No se encontró la consulta con el ID proporcionado");
                }
            } catch (IllegalArgumentException e) {
                Notification.show("ID de consulta no válido");
            }
        } else {
            Notification.show("Por favor, introduce el ID de la consulta");
        }
    }


}

