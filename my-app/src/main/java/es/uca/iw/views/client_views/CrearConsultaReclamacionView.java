package es.uca.iw.views.client_views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.atencion_cliente.Consulta;
import es.uca.iw.atencion_cliente.ServicioConsulta;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "nueva_consulta", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Nueva Consulta")
public class CrearConsultaReclamacionView extends VerticalLayout implements BeforeLeaveObserver {

    private final AuthenticatedUser authenticatedUser;
    private final ServicioConsulta consultaService;

    private final Binder<Consulta> binder;

    @NotBlank(message = "El asunto no puede estar vacío")
    private final TextArea asuntoField;

    @NotBlank(message = "El cuerpo no puede estar vacío")
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
            if (nuevaConsulta.getAsunto().isEmpty() || nuevaConsulta.getCuerpo().isEmpty()) {
                Notification.show("Error: El asunto y el cuerpo no pueden estar vacíos.", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }else {
                consultaService.guardarConsulta(nuevaConsulta);
                UI.getCurrent().navigate("mis_consultas");
            }
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        Notification.show("Consulta/Reclamación enviada correctamente.", 3000, Notification.Position.TOP_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}

