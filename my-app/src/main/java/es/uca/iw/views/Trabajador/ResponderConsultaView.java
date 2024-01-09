package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.atencion_cliente.Respuesta;
import es.uca.iw.views.MainLayout;

@PageTitle("Cádiz Móvil - Responder Consulta")
@Route(value = "respuesta", layout = MainLayout.class)
@AnonymousAllowed
public class ResponderConsultaView extends VerticalLayout {
    public ResponderConsultaView() {
        H1 titulo = new H1("Responder Consulta");
        add(titulo);

        FormLayout formLayout = new FormLayout();

        TextField correoField = new TextField("Correo del destinatario");
        correoField.setRequired(true); // Puedes establecer este campo como obligatorio

        TextArea asuntoArea = new TextArea("Asunto");
        asuntoArea.setValue("RE: "); // Establecer "RE: " por defecto en el campo de asunto
        asuntoArea.setRequired(true); // Puedes establecer este campo como obligatorio

        TextArea cuerpoArea = new TextArea("Cuerpo");
        cuerpoArea.setRequired(true); // Puedes establecer este campo como obligatorio

        formLayout.add(asuntoArea, correoField, cuerpoArea);

        // Botón para enviar la respuesta (debes implementar la lógica de almacenamiento)
        Button enviarButton = new Button("Enviar Respuesta", e -> {
            // Aquí deberías implementar la lógica para almacenar la respuesta en la base de datos
            // Utiliza los valores de correoField.getValue(), asuntoArea.getValue(), cuerpoArea.getValue()
            // para crear una instancia de Respuesta y guardarla en tu repositorio correspondiente.
        });

        BeanValidationBinder<Respuesta> binder = new BeanValidationBinder<>(Respuesta.class);
        binder.bindInstanceFields(this);

// TODO: Implementa la validación de todos los campos según los requisitos

        binder.forField(asuntoArea)
                .withValidator(new NotBlankValidator("El asunto no puede estar vacío"))
                .bind(Respuesta::getAsunto, Respuesta::setAsunto);

        binder.forField(cuerpoArea)
                .withValidator(new NotBlankValidator("El cuerpo no puede estar vacío"))
                .bind(Respuesta::getCuerpo, Respuesta::setCuerpo);

        correoField.addValueChangeListener(
                event -> binder.validate());

        binder.setBean(new Respuesta());


        add(formLayout, enviarButton);
    }
}

