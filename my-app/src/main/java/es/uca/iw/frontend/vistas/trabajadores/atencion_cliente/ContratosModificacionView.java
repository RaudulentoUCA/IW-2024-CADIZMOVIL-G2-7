package es.uca.iw.frontend.vistas.trabajadores.atencion_cliente;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.clases.Role;
import es.uca.iw.backend.servicios.ServicioUsuario;
import es.uca.iw.backend.clases.Contrato;
import es.uca.iw.backend.servicios.ServicioContrato;
import es.uca.iw.frontend.vistas.MainLayout;
import es.uca.iw.frontend.vistas.pantallas_iniciales.ProfileView;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@PageTitle("Cádiz Móvil")
@Route(value = "contratos/mod", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class ContratosModificacionView extends VerticalLayout {
    private final ServicioContrato servicioContrato;

    private final ServicioUsuario servicioUsuario;

    private final FormLayout formLayout = new FormLayout();

    Grid<Contrato> gridContratos = new Grid<>(Contrato.class);

    Dialog confirmDialog;

    public ContratosModificacionView(ServicioContrato servicioContrato, ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
        this.servicioContrato = servicioContrato;
        H1 titulo = new H1("Gestión de modificación de contratos.");

        List<Contrato> contratos = obtenerContratos();
        gridContratos.setItems(contratos);
        gridContratos.setColumns("id");
        gridContratos.addColumn(contrato -> contrato.getUsuario().getEmail()).setHeader("Correo del Cliente");
        gridContratos.addColumn(Contrato::getFechaInicio).setHeader("Fecha Inicio").setSortable(true);
        gridContratos.addColumn(Contrato::getFechaFin).setHeader("Fecha Fin").setSortable(true);
        gridContratos.addColumn(Contrato::getDescuento).setHeader("Descuento en %").setSortable(true);

        Button modBtn = new Button("Modificar Contrato");
        modBtn.addClickListener(event -> modificarContratoSeleccionado());

        Button volver = new Button("Volver a tu página principal");
        volver.addClickListener(event -> UI.getCurrent().navigate(ProfileView.class));

        add(titulo, gridContratos, new HorizontalLayout(modBtn, volver), formLayout);
    }
    private void modificarContratoSeleccionado() {
        Contrato selectedContrato = gridContratos.asSingleSelect().getValue();
        if (selectedContrato != null) {
            TextArea id = new TextArea("ID");
            id.setValue(String.valueOf(selectedContrato.getId()));
            id.setRequired(true);
            id.setReadOnly(true);

            DatePicker fechaInicio = new DatePicker();
            fechaInicio.setRequiredIndicatorVisible(true);
            fechaInicio.setLabel("Inicio de contrato");
            fechaInicio.setValue(selectedContrato.getFechaInicio());

            DatePicker fechaFin = new DatePicker();
            fechaFin.setRequiredIndicatorVisible(true);
            fechaFin.setLabel("Fin de contrato");
            fechaFin.setValue(selectedContrato.getFechaFin());

            TextArea descuento = new TextArea("Descuento");
            descuento.setValue(String.valueOf(selectedContrato.getDescuento()));
            descuento.setRequired(true);

            TextArea titular = new TextArea("Titular");
            titular.setValue(selectedContrato.getUsuario().getDni());
            titular.setRequired(true);

            confirmDialog = new Dialog();
            Button btnConfirmar = new Button("Confirmar", e -> actualizarContrato());
            Button btnCancel = new Button("Cancelar", e -> confirmDialog.close());
            HorizontalLayout buttonLayout = new HorizontalLayout(new Span("¿Deseas seguir adelante con la modificación?"), new HorizontalLayout(btnConfirmar, btnCancel));
            buttonLayout.setAlignItems(Alignment.CENTER);
            buttonLayout.setSpacing(true);

            confirmDialog.add(buttonLayout);

            Button modificar = new Button("Modificar");
            modificar.addClickListener(event -> confirmDialog.open());

            formLayout.add(id, titular, fechaInicio, fechaFin, descuento, modificar);

        } else {
            Notification.show("Error: No has seleccionado ningun contrato todavía.", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
    private List<Contrato> obtenerContratos() {
        return servicioContrato.getAllContratos();
    }

    private void actualizarContrato() {

        TextArea id = new TextArea(), titular = new TextArea(), descuento = new TextArea();
        DatePicker fechaInicio = new DatePicker(), fechaFin = new DatePicker();
        if (formLayout.getChildren().findFirst().isPresent()){
            id = (TextArea) formLayout.getChildren().findFirst().get();
            titular = (TextArea) formLayout.getChildren().toArray()[1];
            fechaInicio = (DatePicker) formLayout.getChildren().toArray()[2];
            fechaFin = (DatePicker) formLayout.getChildren().toArray()[3];
            descuento = (TextArea) formLayout.getChildren().toArray()[4];
        }


        if (!id.isEmpty() && !titular.isEmpty() && fechaInicio.getValue() != null && fechaFin.getValue() != null && !descuento.isEmpty()) {

            Cliente cliente = servicioUsuario.findByDni(titular.getValue()).orElse(null);

            if (cliente != null) {
                if(cliente.getRoles().stream().noneMatch(role -> role.equals(Role.USER))){
                    Notification.show("Error: Existe el usuario, pero es administrador.", 3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    confirmDialog.close();
                }else {
                    if(fechaInicio.getValue().isAfter(fechaFin.getValue())){
                        Notification.show("Error: La fecha de inicio no puede ser posterior a la fecha de fin.", 3000, Notification.Position.TOP_CENTER)
                                .addThemeVariants(NotificationVariant.LUMO_ERROR);
                        confirmDialog.close();
                    }else {
                        Contrato contrato = new Contrato();
                        contrato.setId(Integer.parseInt(id.getValue()));
                        contrato.setFechaInicio(fechaInicio.getValue());
                        contrato.setFechaFin(fechaFin.getValue());
                        contrato.setDescuento(Float.parseFloat(descuento.getValue()));
                        contrato.setUsuario(cliente);

                        servicioContrato.guardarContrato(contrato);

                        UI.getCurrent().getPage().reload();

                    }
                }
            } else {
                Notification.show("Error: Cliente no encontrado", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                confirmDialog.close();
            }
        } else {
            Notification.show("Error: Por favor, complete todos los campos", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            confirmDialog.close();
        }
    }

}