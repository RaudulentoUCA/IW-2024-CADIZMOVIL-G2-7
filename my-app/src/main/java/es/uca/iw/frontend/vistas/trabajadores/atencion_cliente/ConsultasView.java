package es.uca.iw.frontend.vistas.trabajadores.atencion_cliente;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.clases.Consulta;
import es.uca.iw.backend.clases.Respuesta;
import es.uca.iw.backend.servicios.ServicioConsulta;
import es.uca.iw.backend.servicios.ServicioRespuesta;
import es.uca.iw.frontend.vistas.MainLayout;
import es.uca.iw.frontend.vistas.pantallas_iniciales.ProfileView;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Optional;

@PageTitle("Cádiz Móvil")
@Route(value = "consultas", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class ConsultasView extends VerticalLayout {
    private final ServicioConsulta servicioConsulta;
    private final ServicioRespuesta servicioRespuesta;

    FormLayout respuestaFormLayout = new FormLayout();

    public ConsultasView(ServicioConsulta servicioConsulta, ServicioRespuesta servicioRespuesta) {
        this.servicioConsulta = servicioConsulta;
        this.servicioRespuesta = servicioRespuesta;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        H1 titulo = new H1("Consultas pendientes de clientes");
        add(titulo);

        List<Consulta> consultasNoRespondidas = servicioConsulta.obtenerConsultasNoRespondidas();

        if (consultasNoRespondidas.isEmpty()) {
            add(new Paragraph("No hay consultas pendientes."));
        } else {
            Consulta primeraConsulta = consultasNoRespondidas.get(0);

            String correoCliente = primeraConsulta.getUsuario().getEmail();
            Div consultaDiv = new Div();
            consultaDiv.getStyle().set("border", "1px solid #ccc");
            consultaDiv.getStyle().set("padding", "10px");
            consultaDiv.setWidth("1000px");

            consultaDiv.add(
                    new Paragraph("ID: " + primeraConsulta.getId()),
                    new Hr(),
                    new H5("Remitente: " + correoCliente),
                    new Hr(),
                    new H5("Asunto: " + primeraConsulta.getAsunto()),
                    new Hr(),
                    new Paragraph("Cuerpo: " + primeraConsulta.getCuerpo()),
                    new Hr()
            );
            add(consultaDiv);


            TextArea asunto = new TextArea("Asunto");
            asunto.setValue("RE: " + primeraConsulta.getAsunto());
            asunto.setRequired(true);

            TextArea correoClienteField = new TextArea("Correo del Cliente");
            correoClienteField.setValue(correoCliente);
            correoClienteField.setReadOnly(true);

            TextArea cuerpo = new TextArea("Cuerpo");
            cuerpo.setRequired(true);

            respuestaFormLayout.add(asunto, correoClienteField, cuerpo);

            Button responderButton = new Button("Responder consulta", event -> responderConsulta(primeraConsulta));
            respuestaFormLayout.add(responderButton);

            add(respuestaFormLayout);

            Button cerrar = new Button("Cerrar consultas");
            cerrar.addClickListener(event -> UI.getCurrent().navigate(EliminarConsultaView.class));
            add(new HorizontalLayout(responderButton, cerrar));
        }

        Button volver = new Button("Volver a tu página principal");
        volver.addClickListener(event -> UI.getCurrent().navigate(ProfileView.class));
        add(volver);
    }

    private void responderConsulta(Consulta consulta) {
        Respuesta respuesta = new Respuesta();
        respuesta.setCliente(consulta.getUsuario());

        Optional<Component> asuntoOptional = respuestaFormLayout.getChildren().findFirst();
        TextArea asunto = (TextArea) asuntoOptional.orElse(null);
        if (asunto != null) {
            respuesta.setAsunto(asunto.getValue());
        }

        TextArea cuerpo = (TextArea) respuestaFormLayout.getChildren().toArray()[2];
        respuesta.setCuerpo(cuerpo.getValue());

        respuesta.setConsulta(consulta);

        if (respuesta.getCuerpo().isEmpty() || respuesta.getAsunto().isEmpty()) {
            Notification.show("Error: El cuerpo/asunto de la respuesta no puede estar vacío.", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {
            servicioRespuesta.guardarRespuesta(respuesta);

            consulta.setRespuesta(respuesta);
            consulta.setRespondido(true);
            servicioConsulta.guardarConsulta(consulta);


            // Recargar la vista para reflejar los cambios
            UI.getCurrent().getPage().reload();

        }
    }
}

