package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.atencion_cliente.Consulta;
import es.uca.iw.contrato.Contrato;
import es.uca.iw.contrato.ServiciosContrato;
import es.uca.iw.views.MainLayout;

import java.util.List;

@PageTitle("Cádiz Móvil")
@Route(value = "contratos/bajas", layout = MainLayout.class)
@AnonymousAllowed
public class ContratosBajasView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;
    private final ServiciosContrato serviciosContrato;

    public ContratosBajasView(AuthenticatedUser authenticatedUser, ServiciosContrato serviciosContrato) {
        this.authenticatedUser = authenticatedUser;
        this.serviciosContrato = serviciosContrato;

        H1 titulo = new H1("Gestión de bajas de contratos");
        Button btnEliminar = new Button("Eliminar Contrato");

        // Crear el grid para mostrar los contratos
        Grid<Contrato> gridContratos = new Grid<>(Contrato.class);
        List<Contrato> contratos = obtenerContratos();
        gridContratos.setItems(contratos);
        gridContratos.setColumns("id");
        gridContratos.addColumn(contrato -> contrato.getCliente().getEmail()).setHeader("Correo del Cliente");
        gridContratos.addColumn(Contrato::getFechaInicio).setHeader("Fecha Inicio").setSortable(true);
        gridContratos.addColumn(Contrato::getFechaFin).setHeader("Fecha Fin").setSortable(true);
        gridContratos.addColumn(Contrato::getDescuento).setHeader("Descuento en %").setSortable(true);
        // Manejar la selección del grid
        gridContratos.asSingleSelect().addValueChangeListener(event -> {
            btnEliminar.setEnabled(event.getValue() != null);
        });

        // Botón para eliminar contrato seleccionado

        btnEliminar.setEnabled(false);
        btnEliminar.addClickListener(event -> {
            Contrato contratoSeleccionado = gridContratos.asSingleSelect().getValue();
            if (contratoSeleccionado != null) {
                serviciosContrato.eliminarContrato(contratoSeleccionado.getId());
                List<Contrato> nuevosContratos = obtenerContratos();
                gridContratos.setItems(nuevosContratos);
            }
        });

        // Botón para volver a la página principal
        Button volver = new Button("Volver a tu página principal");
        volver.addClickListener(event -> UI.getCurrent().navigate(AtencionView.class));

        // Agregar los componentes al diseño
        add(titulo, gridContratos, btnEliminar, volver);
    }

    // Método para obtener la lista de contratos
    private List<Contrato> obtenerContratos() {
        return serviciosContrato.getAllContratos();
    }
}


