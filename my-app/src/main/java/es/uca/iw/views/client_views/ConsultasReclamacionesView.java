package es.uca.iw.views.client_views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.atencion_cliente.Consulta;
import es.uca.iw.atencion_cliente.ServicioConsulta;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.Trabajador.AtencionView;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@Route(value = "mis_consultas", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Mis Consultas")
public class ConsultasReclamacionesView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;
    private final ServicioConsulta consultaService;

    private final Grid<Consulta> consultaGrid;

    private final H1 titulo;

    public ConsultasReclamacionesView(AuthenticatedUser authenticatedUser, ServicioConsulta consultaService) {
        this.authenticatedUser = authenticatedUser;
        this.consultaService = consultaService;

        titulo = new H1("Desglose de mis consultas y reclamaciones");

        consultaGrid = new Grid<>(Consulta.class);
        consultaGrid.setColumns("asunto", "cuerpo");

        actualizarGrid();

        Button crearConsulta = new Button("Nueva Reclamación/Consulta");
        crearConsulta.addClickListener(event -> UI.getCurrent().navigate(CrearConsultaReclamacionView.class));

        Button verRespuestas = new Button("Respuestas recibidas");
        verRespuestas.addClickListener(event -> UI.getCurrent().navigate(RespuestasRecibidas.class));

        add(titulo, consultaGrid, new HorizontalLayout(crearConsulta, verRespuestas));
    }

    private void actualizarGrid() {
        Optional<Cliente> optionalCliente = authenticatedUser.get();
        optionalCliente.ifPresent(cliente -> {
            consultaGrid.setItems(consultaService.getConsultasByCliente(cliente));
        });
    }
}

