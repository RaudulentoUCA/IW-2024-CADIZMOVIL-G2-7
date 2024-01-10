package es.uca.iw.views.client_views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.atencion_cliente.Respuesta;
import es.uca.iw.atencion_cliente.ServicioRespuesta;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Route(value = "respuestas_recibidas", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Respuestas Recibidas")
public class RespuestasRecibidas extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;
    private final ServicioRespuesta servicioRespuesta;

    private final Grid<Respuesta> respuestaGrid;

    private final H1 titulo;

    public RespuestasRecibidas(@Autowired AuthenticatedUser authenticatedUser, @Autowired ServicioRespuesta servicioRespuesta) {
        this.authenticatedUser = authenticatedUser;
        this.servicioRespuesta = servicioRespuesta;

        titulo = new H1("Respuestas recibidas a mis consultas y reclamaciones");

        respuestaGrid = new Grid<>(Respuesta.class);
        respuestaGrid.setColumns("asunto", "cuerpo");

        actualizarGrid();

        Button atras = new Button("Volver atrás");
        atras.addClickListener(event -> UI.getCurrent().navigate(ConsultasReclamacionesView.class));

        add(titulo, respuestaGrid, atras);
    }

    private void actualizarGrid() {
        Optional<Cliente> optionalCliente = authenticatedUser.get();
        optionalCliente.ifPresent(cliente -> {
            respuestaGrid.setItems(servicioRespuesta.getRespuestasByCliente(cliente));
        });
    }
}
