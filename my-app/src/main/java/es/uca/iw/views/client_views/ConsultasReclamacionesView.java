package es.uca.iw.views.client_views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.notification.Notification;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.atencion_cliente.Consulta;
import es.uca.iw.atencion_cliente.Respuesta;
import es.uca.iw.atencion_cliente.ServicioConsulta;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@Route(value = "mis_consultas", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Mis Consultas")
public class ConsultasReclamacionesView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;
    private final ServicioConsulta consultaService;

    private final Grid<Consulta> consultaGrid;
    private final Div consultaDetailsDiv;

    public ConsultasReclamacionesView(AuthenticatedUser authenticatedUser, ServicioConsulta consultaService) {
        this.authenticatedUser = authenticatedUser;
        this.consultaService = consultaService;

        H1 titulo = new H1("Desglose de mis consultas y reclamaciones");

        consultaGrid = new Grid<>(Consulta.class);
        consultaGrid.setColumns("id","asunto", "cuerpo");

        Button verRespuestaBtn = new Button("Ver Respuesta");
        verRespuestaBtn.addClickListener(event -> mostrarRespuestaSeleccionada());

        consultaDetailsDiv = new Div();

        Button crearConsulta = new Button("Nueva Reclamación/Consulta");
        crearConsulta.addClickListener(event -> UI.getCurrent().navigate(CrearConsultaReclamacionView.class));

        add(titulo, consultaGrid, verRespuestaBtn, consultaDetailsDiv, crearConsulta);

        actualizarGrid();
    }

    private void mostrarRespuestaSeleccionada() {
        Consulta selectedConsulta = consultaGrid.asSingleSelect().getValue();
        if (selectedConsulta != null) {
            if (selectedConsulta.getRespuesta() != null) {
                mostrarConsultaDetails(selectedConsulta.getRespuesta());
            } else {
                Notification.show("Error: No hemos respondido a esta consulta todavía.", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            Notification.show("Error: No has seleccionado ninguna consulta.", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void mostrarConsultaDetails(Respuesta respuesta) {
        // Crea la sección de detalles y actualiza su contenido
        consultaDetailsDiv.removeAll();
        consultaDetailsDiv.add(
                new H5("ID de la consulta que se resuelve: " + respuesta.getConsulta().getId()),
                new Hr(),
                new Hr(),
                new H5("Asunto: " + respuesta.getAsunto()),
                new Hr(),
                new Paragraph("Cuerpo: " + respuesta.getCuerpo())
        );
    }

    private void actualizarGrid() {
        Optional<Cliente> optionalCliente = authenticatedUser.get();
        optionalCliente.ifPresent(cliente -> consultaGrid.setItems(consultaService.getConsultasByCliente(cliente)));
    }
}

