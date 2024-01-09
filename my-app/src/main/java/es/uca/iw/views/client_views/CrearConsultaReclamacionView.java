package es.uca.iw.views.client_views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.atencion_cliente.Consulta;
import es.uca.iw.atencion_cliente.ServicioConsulta;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "nueva_consulta", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Nueva Consulta")
public class CrearConsultaReclamacionView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;
    private final ServicioConsulta consultaService;

    private final Binder<Consulta> binder;
    private final TextArea asuntoField;
    private final TextArea cuerpoField;

    public CrearConsultaReclamacionView(AuthenticatedUser authenticatedUser, ServicioConsulta consultaService) {
        this.authenticatedUser = authenticatedUser;
        this.consultaService = consultaService;

        // Crear un nuevo objeto Consulta y configurar el binder
        Consulta nuevaConsulta = new Consulta();
        nuevaConsulta.setCliente(authenticatedUser.get().orElseThrow()); // Obtener el cliente autenticado
        binder = new BeanValidationBinder<>(Consulta.class);
        binder.setBean(nuevaConsulta);

        // Crear los campos del formulario
        asuntoField = new TextArea("Asunto");
        cuerpoField = new TextArea("Cuerpo");

        // Enlazar los campos del formulario al binder
        binder.forField(asuntoField).bind("asunto");
        binder.forField(cuerpoField).bind("cuerpo");

        // Crear el botón de enviar
        Button enviarButton = new Button("Enviar", e -> enviarConsulta());

        // Crear el formulario
        FormLayout formLayout = new FormLayout(asuntoField, cuerpoField, enviarButton);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        // Añadir el formulario a la vista
        add(formLayout);
    }

    private void enviarConsulta() {
        if (binder.validate().isOk()) {
            Consulta nuevaConsulta = binder.getBean();
            nuevaConsulta.setCliente(authenticatedUser.get().orElseThrow()); // Asignar el cliente autenticado
            consultaService.guardarConsulta(nuevaConsulta);
            Notification.show("Consulta enviada con éxito");
            limpiarFormulario();
        } else {
            Notification.show("Por favor, complete correctamente el formulario");
        }
    }

    private void limpiarFormulario() {
        binder.setBean(new Consulta()); // Crear un nuevo objeto Consulta para el siguiente formulario
        asuntoField.clear();
        cuerpoField.clear();
    }
}

