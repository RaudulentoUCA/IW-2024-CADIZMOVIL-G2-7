package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.atencion_cliente.Respuesta;
import es.uca.iw.atencion_cliente.ServicioRespuesta;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.ServiciosCliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@PageTitle("Cádiz Móvil - Responder Consulta")
@Route(value = "respuesta", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class ResponderConsultaView extends VerticalLayout {
    private final ServicioRespuesta servicioRespuesta;
    private final ServiciosCliente serviciosCliente;

    final private TextField correoField;
    final private TextArea asuntoArea;
    final private TextArea cuerpoArea;
    public ResponderConsultaView(ServicioRespuesta servicioRespuesta, ServiciosCliente serviciosCliente) {
        this.servicioRespuesta = servicioRespuesta;
        this.serviciosCliente = serviciosCliente;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 titulo = new H1("Responder Consulta");

        FormLayout formLayout = new FormLayout();


        correoField = new TextField("Correo del destinatario");
        correoField.setRequired(true);

        asuntoArea = new TextArea("Asunto");
        asuntoArea.setValue("RE: ");
        asuntoArea.setRequired(true);

        cuerpoArea = new TextArea("Cuerpo");
        cuerpoArea.setRequired(true);

        formLayout.add(asuntoArea, correoField, cuerpoArea);

        Button enviarButton = new Button("Enviar respuesta", event -> enviarRespuesta());
        formLayout.add(enviarButton);

        add(titulo, formLayout);
    }

    protected void enviarRespuesta() {
        String correoDestinatario = correoField.getValue();
        String asunto = asuntoArea.getValue();
        String cuerpo = cuerpoArea.getValue();

        Optional<Cliente> clienteOptional = serviciosCliente.cargarUsuarioPorEmail(correoDestinatario);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Respuesta respuesta = new Respuesta();
            respuesta.setCliente(cliente);
            respuesta.setAsunto(asunto);
            respuesta.setCuerpo(cuerpo);

            if (respuesta.getAsunto().isEmpty() || respuesta.getCuerpo().isEmpty()) {
                Notification.show("Error: El asunto y el cuerpo no pueden estar vacíos.", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }else {
                servicioRespuesta.guardarRespuesta(respuesta);
                Notification.show("Respuesta enviada correctamente", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
        } else {
            Notification.show("El cliente con el correo especificado no existe", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}


