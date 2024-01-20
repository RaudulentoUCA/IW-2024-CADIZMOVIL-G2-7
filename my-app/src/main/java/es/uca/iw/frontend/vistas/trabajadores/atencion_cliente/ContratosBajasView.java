package es.uca.iw.frontend.vistas.trabajadores.atencion_cliente;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.clases.Contrato;
import es.uca.iw.backend.servicios.ServicioContrato;
import es.uca.iw.frontend.vistas.MainLayout;
import es.uca.iw.frontend.vistas.pantallas_iniciales.ProfileView;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@PageTitle("Cádiz Móvil")
@Route(value = "contratos/bajas", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class ContratosBajasView extends VerticalLayout {
    private final ServicioContrato servicioContrato;

    public ContratosBajasView(ServicioContrato servicioContrato) {
        this.servicioContrato = servicioContrato;

        H1 titulo = new H1("Gestión de bajas de contratos");
        Button btnEliminar = new Button("Dar de baja contrato");

        Grid<Contrato> gridContratos = new Grid<>(Contrato.class);
        List<Contrato> contratos = obtenerContratos();
        gridContratos.setItems(contratos);
        gridContratos.setColumns("id");
        gridContratos.addColumn(contrato -> contrato.getUsuario().getEmail()).setHeader("Correo del Cliente");
        gridContratos.addColumn(Contrato::getFechaInicio).setHeader("Fecha Inicio").setSortable(true);
        gridContratos.addColumn(Contrato::getFechaFin).setHeader("Fecha Fin").setSortable(true);
        gridContratos.addColumn(Contrato::getDescuento).setHeader("Descuento en %").setSortable(true);
        // Manejar la selección del grid
        gridContratos.asSingleSelect().addValueChangeListener(event -> btnEliminar.setEnabled(event.getValue() != null));

        Dialog confirmDialog = new Dialog();
        Button btnConfirmar = new Button("Confirmar", e -> {
            Contrato contratoSeleccionado = gridContratos.asSingleSelect().getValue();
            if (contratoSeleccionado != null) {
                servicioContrato.eliminarContrato(contratoSeleccionado.getId());
                List<Contrato> nuevosContratos = obtenerContratos();
                gridContratos.setItems(nuevosContratos);
                confirmDialog.close();
            }
        });
        Button btnCancel = new Button("Cancelar", e -> confirmDialog.close());
        HorizontalLayout buttonLayout = new HorizontalLayout(new Span("¿Deseas seguir adelante con el alta?"), new HorizontalLayout(btnConfirmar, btnCancel));
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setSpacing(true);
        confirmDialog.add(buttonLayout);

        btnEliminar.setEnabled(false);
        btnEliminar.addClickListener(event -> {
            Contrato contratoSeleccionado = gridContratos.asSingleSelect().getValue();
            if (contratoSeleccionado != null) {
                confirmDialog.open();
            }
        });

        Button volver = new Button("Volver a tu página principal");
        volver.addClickListener(event -> UI.getCurrent().navigate(ProfileView.class));

        add(titulo, gridContratos, btnEliminar, volver);
    }

    private List<Contrato> obtenerContratos() {
        return servicioContrato.getAllContratos();
    }
}


