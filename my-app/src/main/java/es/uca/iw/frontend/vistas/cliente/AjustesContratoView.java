package es.uca.iw.frontend.vistas.cliente;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.componentes.AuthenticatedUser;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.clases.Contrato;
import es.uca.iw.backend.servicios.ServicioContrato;
import es.uca.iw.frontend.vistas.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Route(value = "ajustes-contrato", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Ajustes de contrato")
public class AjustesContratoView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    private final ServicioContrato servicioContrato;
    private final ComboBox<Contrato> contratoComboBox = new ComboBox<>("Seleccionar Contrato");
    private final Checkbox compartirDatos;
    private final TextField numerosBloqueadosField;  // Nuevo campo para los números bloqueados
    private final Button guardar;

    public AjustesContratoView(AuthenticatedUser authenticatedUser, ServicioContrato servicioContrato){
        this.authenticatedUser = authenticatedUser;
        this.servicioContrato = servicioContrato;

        add(new H2("Seleccione uno de sus contratos"));
        compartirDatos = new Checkbox();
        compartirDatos.setLabel("Compartir Datos");
        compartirDatos.setVisible(false);

        numerosBloqueadosField = new TextField("Números Bloqueados");  // Campo para los números bloqueados
        numerosBloqueadosField.setVisible(false);

        guardar = new Button();
        guardar.setText("Guardar Datos");
        guardar.setVisible(false);

        Optional<Cliente> optionalCliente = authenticatedUser.get();

        add(contratoComboBox, compartirDatos, numerosBloqueadosField, guardar);

        if(optionalCliente.isPresent()){
            // ComboBox
            List<Contrato> contratos = servicioContrato.getContratosByCliente(optionalCliente.get());
            contratoComboBox.setItems(contratos);
            contratoComboBox.setItemLabelGenerator(contrato -> String.valueOf(contrato.getId()));
            contratoComboBox.addValueChangeListener(event -> ejecutarFuncion(event.getValue()));
        }
    }

    private void ejecutarFuncion(Contrato value) {
        compartirDatos.setVisible(true);
        numerosBloqueadosField.setVisible(true);
        guardar.setVisible(true);


        guardar.addClickListener(event -> actualizarContrato(value));
    }

    private void actualizarContrato(Contrato value) {
        value.setCompartirDatos(compartirDatos.getValue());

        // Obtener y guardar los números bloqueados desde el campo de texto
        Set<String> numerosBloqueados = value.getNumerosBloqueados();
        numerosBloqueados.add(numerosBloqueadosField.getValue());
        value.setNumerosBloqueados(numerosBloqueados);

        servicioContrato.guardarContrato(value);
    }
}