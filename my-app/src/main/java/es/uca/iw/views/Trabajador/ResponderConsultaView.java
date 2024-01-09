package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.atencion_cliente.RepositorioRespuesta;
import es.uca.iw.atencion_cliente.Respuesta;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.RepositorioCliente;
import es.uca.iw.views.MainLayout;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@PageTitle("Cádiz Móvil - Responder Consulta")
@Route(value = "respuesta", layout = MainLayout.class)
@AnonymousAllowed
public class ResponderConsultaView extends VerticalLayout {
    private final RepositorioRespuesta repositorioRespuesta;
    private final RepositorioCliente repositorioCliente;

    private TextField correoField;
    private TextArea asuntoArea;
    private TextArea cuerpoArea;
    public ResponderConsultaView(RepositorioRespuesta repositorioRespuesta, RepositorioCliente repositorioCliente) {
        this.repositorioRespuesta = repositorioRespuesta;
        this.repositorioCliente = repositorioCliente;

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

    @Transactional
    protected void enviarRespuesta() {
        String correoDestinatario = correoField.getValue();
        String asunto = asuntoArea.getValue();
        String cuerpo = cuerpoArea.getValue();

        Optional<Cliente> clienteOptional = repositorioCliente.findByEmail(correoDestinatario);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Respuesta respuesta = new Respuesta();
            respuesta.setCliente(cliente);
            respuesta.setAsunto(asunto);
            respuesta.setCuerpo(cuerpo);

            repositorioRespuesta.save(respuesta);

            Notification.show("Respuesta enviada correctamente", 3000, Notification.Position.TOP_CENTER);
        } else {
            Notification.show("El cliente con el correo especificado no existe", 3000, Notification.Position.TOP_CENTER);
        }
    }
}


