package es.uca.iw.tarifa;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("tarifa-view")
public class TarifaView extends VerticalLayout {

    private final ComboBox<Tarifa> tarifaComboBox = new ComboBox<>("Seleccionar Tarifa");
    private final FormLayout formLayout = new FormLayout();
    private final Button guardarButton = new Button("Guardar");

    private final Binder<Tarifa> binder = new Binder<>(Tarifa.class);

    // Aquí deberías inyectar tu servicio o repositorio de tarifas para obtener la lista de tarifas
    private final ServiciosTarifa tarifaService;

    public TarifaView(ServiciosTarifa tarifaService) {
        this.tarifaService = tarifaService;

        // ComboBox
        List<Tarifa> tarifas = tarifaService.obtenerTodasLasTarifas();
        tarifaComboBox.setItems(tarifas);
        tarifaComboBox.setItemLabelGenerator(Tarifa::getNombre);

        // Formulario
        formLayout.addFormItem(tarifaComboBox, "Tarifa");

        TextField nombre = new TextField();
        formLayout.addFormItem(nombre, "Nombre");
        binder.bind(nombre, "nombre");

        TextField precio = new TextField();
        formLayout.addFormItem(precio, "Precio");
        binder.forField(precio)
                .withConverter(new StringToFloatConverter("Ingrese un valor válido"))
                .bind(Tarifa::getPrecio, Tarifa::setPrecio);

        Checkbox permiteRoaming = new Checkbox();
        formLayout.addFormItem(permiteRoaming, "Roaming");
        binder.bind(permiteRoaming, "permiteRoaming");

        TextField descripcion = new TextField();
        formLayout.addFormItem(descripcion, "Descripción");
        binder.bind(descripcion, "descripcion");

        Checkbox fijo = new Checkbox();
        formLayout.addFormItem(fijo, "Fijo");
        binder.bind(fijo, "fijo");

        Checkbox fibra = new Checkbox();
        formLayout.addFormItem(fibra, "Fibra");
        binder.bind(fibra, "fibra");

        TextField megas = new TextField();
        formLayout.addFormItem(megas, "Megas Móvil");
        binder.forField(megas)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getmegasMovil, Tarifa::setmegasMovil);

        guardarButton.addClickListener(event -> guardarTarifa());
        tarifaComboBox.addValueChangeListener(event -> mostrarDetallesTarifa(event.getValue()));
        add(formLayout, guardarButton);

    }

    private void mostrarDetallesTarifa(Tarifa tarifa) {
        if (tarifa != null) {
            binder.setBean(tarifa);
        }
    }

    private void guardarTarifa() {
        Tarifa tarifa = binder.getBean();
        tarifaService.guardarTarifa(tarifa);
        Notification.show("Tarifa guardada correctamente");
    }
}