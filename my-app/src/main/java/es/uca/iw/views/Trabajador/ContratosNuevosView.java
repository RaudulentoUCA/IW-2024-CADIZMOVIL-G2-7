package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.Role;
import es.uca.iw.cliente.ServiciosCliente;
import es.uca.iw.contrato.Contrato;
import es.uca.iw.contrato.ServiciosContrato;
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.profile.ProfileView;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;

@PageTitle("Cádiz Móvil")
@Route(value = "contratos/altas", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class ContratosNuevosView extends VerticalLayout {

    private final ServiciosCliente serviciosCliente;

    private final ServiciosContrato serviciosContrato;
    private final TextField dni;
    private final DatePicker fechaInicio;

    private final DatePicker fechaFin;

    private final TextField descuento;

    Dialog confirmDialog;
    public ContratosNuevosView(AuthenticatedUser authenticatedUser, ServiciosCliente serviciosCliente, ServiciosContrato serviciosContrato) {
        this.serviciosCliente = serviciosCliente;
        this.serviciosContrato = serviciosContrato;

        FormLayout formLayout = new FormLayout();
        H1 titulo = new H1("Gestión de altas de contratos");

        dni = new TextField();
        dni.setRequiredIndicatorVisible(true);
        dni.setLabel("DNI");

        fechaInicio = new DatePicker();
        fechaInicio.setRequiredIndicatorVisible(true);
        fechaInicio.setLabel("Inicio de contrato");
        fechaInicio.setValue(LocalDate.now());

        fechaFin = new DatePicker();
        fechaFin.setRequiredIndicatorVisible(true);
        fechaFin.setLabel("Fin de contrato");

        descuento = new TextField();
        descuento.setRequiredIndicatorVisible(true);
        descuento.setLabel("Descuento en %");

        confirmDialog = new Dialog();
        Button btnConfirmar = new Button("Confirmar", e -> crearContrato());
        Button btnCancel = new Button("Cancelar", e -> confirmDialog.close());
        HorizontalLayout buttonLayout = new HorizontalLayout(new Span("¿Deseas seguir adelante con el alta?"), new HorizontalLayout(btnConfirmar, btnCancel));
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setSpacing(true);
        confirmDialog.add(buttonLayout);

        Button crearContrato = new Button("Dar de alta");
        crearContrato.addClickListener(event -> confirmDialog.open());

        Button volver = new Button("Volver a tu página principal");
        volver.addClickListener(event -> UI.getCurrent().navigate(ProfileView.class));

        formLayout.add(dni, fechaInicio, fechaFin, descuento);
        add(titulo, formLayout, new HorizontalLayout(crearContrato, volver));
    }

    private void crearContrato() {
        if (!dni.isEmpty() && fechaInicio.getValue() != null && fechaFin.getValue() != null && !descuento.isEmpty()) {
            String dniValue = dni.getValue();
            LocalDate fechaInicioValue = fechaInicio.getValue();
            LocalDate fechaFinValue = fechaFin.getValue();
            float descuentoValue = Float.parseFloat(descuento.getValue());

            Cliente cliente = serviciosCliente.findByDni(dniValue).orElse(null);

            if (cliente != null) {
                if(cliente.getRoles().stream().noneMatch(role -> role.equals(Role.USER))){
                    Notification.show("Error: Existe el usuario, pero es administrador.", 3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);

                }else {
                    if(fechaInicioValue.isAfter(fechaFinValue)){
                        Notification.show("Error: La fecha de inicio no puede ser posterior a la fecha de fin.", 3000, Notification.Position.TOP_CENTER)
                                .addThemeVariants(NotificationVariant.LUMO_ERROR);

                    }else {
                        Contrato contrato = new Contrato();
                        contrato.setCliente(cliente);
                        contrato.setFechaInicio(fechaInicioValue);
                        contrato.setFechaFin(fechaFinValue);
                        contrato.setDescuento(descuentoValue);

                        serviciosContrato.guardarContrato(contrato);

                        Notification.show("Contrato almacenado correctamente", 3000, Notification.Position.TOP_CENTER)
                                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                        dni.setValue("");
                        fechaInicio.clear();
                        fechaFin.clear();
                        descuento.clear();
                        fechaInicio.setValue(LocalDate.now());
                    }
                }
            } else {
                Notification.show("Error: Cliente no encontrado", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            Notification.show("Error: Por favor, complete todos los campos", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        confirmDialog.close();
    }
}
