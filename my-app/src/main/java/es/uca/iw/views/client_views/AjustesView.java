package es.uca.iw.views.client_views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.ServiciosCliente;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Route(value = "ajustes", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Ajustes")

public class AjustesView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    private final ServiciosCliente serviciosCliente;

    public AjustesView(AuthenticatedUser authenticatedUser, ServiciosCliente serviciosCliente){
        this.authenticatedUser = authenticatedUser;
        this.serviciosCliente = serviciosCliente;

        add(new H2("Tus datos personales"));
        Optional<Cliente> optionalCliente = authenticatedUser.get();

        optionalCliente.ifPresent(cliente->{

            TextField nameField = new TextField("Nombre");
            nameField.setValue(cliente.getNombre());
            nameField.setRequired(true);

            TextField surnameField = new TextField("Apellidos");
            surnameField.setValue(cliente.getApellidos());
            surnameField.setRequired(true);

            DatePicker birthDateField = new DatePicker("Fecha de nacimiento");
            birthDateField.setValue(cliente.getFechaDeNacimiento());
            birthDateField.setRequired(true);

            TextField documentNumberField = new TextField("Documento de identificación");
            documentNumberField.setValue(cliente.getDni());
            documentNumberField.setRequired(true);

            TextField emailField = new TextField("Email");
            emailField.setValue(cliente.getEmail());
            emailField.setReadOnly(true);

            TextField contactNumberField = new TextField("Numero de contacto");
            contactNumberField.setValue(cliente.getNumeroContacto());


            Button saveButton = new Button("Guardar cambios");
            add(nameField, surnameField, birthDateField, documentNumberField,contactNumberField,emailField,saveButton);
            saveButton.addClickListener((event)->{
                if (serviciosCliente.actualizarDatosDelCliente(cliente.getId(), nameField.getValue(), surnameField.getValue(), birthDateField.getValue(), documentNumberField.getValue(), contactNumberField.getValue())){
                    Notification notification = Notification.show("Datos cambiados.");
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }
                else{
                    Notification notification = Notification.show("Ha ocurrido un error, revisa los datos introducidos");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
                UI.getCurrent().navigate(AjustesView.class);
            });

        });




    }
}