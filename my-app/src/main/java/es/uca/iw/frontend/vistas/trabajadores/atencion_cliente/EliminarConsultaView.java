package es.uca.iw.frontend.vistas.trabajadores.atencion_cliente;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.clases.Consulta;
import es.uca.iw.backend.servicios.ServicioConsulta;
import es.uca.iw.frontend.vistas.MainLayout;
import es.uca.iw.frontend.vistas.pantallas_iniciales.ProfileView;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@PageTitle("Cádiz Móvil")
@Route(value = "consultas/bajas", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class EliminarConsultaView extends VerticalLayout {
    private final ServicioConsulta servicioConsulta;
    private final AtomicReference<List<Consulta>> consultas;

    public EliminarConsultaView(ServicioConsulta servicioConsulta) {
        this.servicioConsulta = servicioConsulta;


        this.consultas = new AtomicReference<>(obtenerConsultas());

        H1 titulo = new H1("Cerrar consultas");

        Grid<Consulta> gridConsultas = new Grid<>(Consulta.class);
        gridConsultas.setItems(consultas.get());

        gridConsultas.setColumns("id");
        gridConsultas.addColumn(consulta -> consulta.getUsuario().getEmail()).setHeader("Correo del Cliente");
        gridConsultas.addColumn(Consulta::getAsunto).setHeader("Asunto").setSortable(true);

        // Botón para eliminar consulta seleccionada
        Button btnEliminar = new Button("Cerrar Consulta");

        // Confirmation Dialog
        Dialog confirmDialog = new Dialog();

        Button btnConfirmar = new Button("Confirmar", e -> {
            Consulta consultaSeleccionada = gridConsultas.asSingleSelect().getValue();
            if (consultaSeleccionada != null) {
                if (consultaSeleccionada.getRespuesta() == null) {
                    Notification.show("Error: No puedes cerrar una consulta no atendida.", 3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                } else {
                    servicioConsulta.eliminarConsulta(consultaSeleccionada.getId());
                    consultas.set(obtenerConsultas());
                    gridConsultas.setItems(consultas.get());
                }
            }
            confirmDialog.close();
        });

        Button btnCancel = new Button("Cancelar", e -> confirmDialog.close());

        // Alineación y separación de los botones en el dialog
        HorizontalLayout buttonLayout = new HorizontalLayout(btnConfirmar, btnCancel);
        VerticalLayout notificationLayout = new VerticalLayout(new Span("¿Estás seguro de cerrar la consulta?"), buttonLayout);
        notificationLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setSpacing(true);

        confirmDialog.add(notificationLayout);

        btnEliminar.setEnabled(false);
        gridConsultas.asSingleSelect().addValueChangeListener(event -> btnEliminar.setEnabled(event.getValue() != null));

        btnEliminar.addClickListener(event -> {
            Consulta consultaSeleccionada = gridConsultas.asSingleSelect().getValue();
            if (consultaSeleccionada != null) {
                confirmDialog.open();
            }
        });

        Button inicio = new Button("Volver a tu página principal");
        inicio.addClickListener(event -> UI.getCurrent().navigate(ProfileView.class));

        add(titulo, gridConsultas, new HorizontalLayout(btnEliminar, inicio));
    }

    private List<Consulta> obtenerConsultas() {
        return servicioConsulta.obtenerTodasLasConsultas();
    }
}



