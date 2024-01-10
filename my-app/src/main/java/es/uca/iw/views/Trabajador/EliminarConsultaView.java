package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.atencion_cliente.Consulta;
import es.uca.iw.atencion_cliente.ServicioConsulta;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@PageTitle("Cádiz Móvil")
@Route(value = "consultas/bajas", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class EliminarConsultaView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;
    private final ServicioConsulta servicioConsulta;
    private final AtomicReference<List<Consulta>> consultas;

    public EliminarConsultaView(AuthenticatedUser authenticatedUser, ServicioConsulta servicioConsulta) {
        this.authenticatedUser = authenticatedUser;
        this.servicioConsulta = servicioConsulta;
        this.consultas = new AtomicReference<>(obtenerConsultas());

        H1 titulo = new H1("Cerrar consultas");

        Grid<Consulta> gridConsultas = new Grid<>(Consulta.class);
        gridConsultas.setItems(consultas.get());

        gridConsultas.setColumns("id");
        gridConsultas.addColumn(consulta -> consulta.getCliente().getEmail()).setHeader("Correo del Cliente");
        gridConsultas.addColumn(Consulta::getAsunto).setHeader("Asunto").setSortable(true);

        // Botón para eliminar consulta seleccionada
        Button btnEliminar = new Button("Cerrar Consulta");
        btnEliminar.addClickListener(event -> {
            Consulta consultaSeleccionada = gridConsultas.asSingleSelect().getValue();
            if (consultaSeleccionada != null) {
                servicioConsulta.eliminarConsulta(consultaSeleccionada.getId());
                // Actualiza la lista de consultas en el grid
                consultas.set(obtenerConsultas());
                gridConsultas.setItems(consultas.get());
            }
        });

        Button inicio = new Button("Volver a tu página principal");
        inicio.addClickListener(event -> UI.getCurrent().navigate(AtencionView.class));

        Button atras = new Button("Volver a la página anterior");
        atras.addClickListener(event -> UI.getCurrent().navigate(ConsultasView.class));

        add(titulo, gridConsultas, new HorizontalLayout(btnEliminar, inicio, atras));
    }
    private List<Consulta> obtenerConsultas() {
        return servicioConsulta.obtenerTodasLasConsultas();
    }
}

