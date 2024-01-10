package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.RepositorioCliente;
import es.uca.iw.contrato.Contrato;
import es.uca.iw.contrato.RepositorioContrato;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;

@PageTitle("Cádiz Móvil")
@Route(value = "contratos/altas", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class ContratosNuevosView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    private final RepositorioCliente repositorioCliente;
    private final RepositorioContrato repositorioContrato;
    private final TextField dni;
    private final DatePicker fechaInicio;

    private final DatePicker fechaFin;

    private final TextField descuento;
    public ContratosNuevosView(AuthenticatedUser authenticatedUser, RepositorioCliente repositorioCliente, RepositorioContrato repositorioContrato) {
        this.authenticatedUser = authenticatedUser;
        this.repositorioCliente = repositorioCliente;
        this.repositorioContrato = repositorioContrato;

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

        Button crearContrato = new Button("Dar de alta");
        crearContrato.addClickListener(event -> crearContrato());

        Button volver = new Button("Volver a tu página principal");
        volver.addClickListener(event -> UI.getCurrent().navigate(AtencionView.class));

        formLayout.add(dni, fechaInicio, fechaFin, descuento);
        add(titulo, formLayout, new HorizontalLayout(crearContrato, volver));
    }

    private void crearContrato() {
        if (!dni.isEmpty() && fechaInicio.getValue() != null && fechaFin.getValue() != null && !descuento.isEmpty()) {
            String dniValue = dni.getValue();
            LocalDate fechaInicioValue = fechaInicio.getValue();
            LocalDate fechaFinValue = fechaFin.getValue();
            float descuentoValue = Float.parseFloat(descuento.getValue());

            Cliente cliente = repositorioCliente.findByDni(dniValue).orElse(null);

            if (cliente != null) {
                Contrato contrato = new Contrato();
                contrato.setCliente(cliente);
                contrato.setFechaInicio(fechaInicioValue);
                contrato.setFechaFin(fechaFinValue);
                contrato.setDescuento(descuentoValue);

                repositorioContrato.save(contrato);

                Notification.show("Contrato almacenado correctamente", 3000, Notification.Position.TOP_CENTER);

                dni.setValue("");
                fechaInicio.clear();
                fechaFin.clear();
                descuento.clear();
                fechaInicio.setValue(LocalDate.now());
            } else {
                Notification.show("Error: Cliente no encontrado", 3000, Notification.Position.TOP_CENTER);
            }
        } else {
            Notification.show("Error: Por favor, complete todos los campos", 3000, Notification.Position.TOP_CENTER);
        }
    }
}
