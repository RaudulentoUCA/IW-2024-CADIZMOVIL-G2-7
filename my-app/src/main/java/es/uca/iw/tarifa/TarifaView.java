package es.uca.iw.tarifa;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = "tarifa-view", layout = MainLayout.class)
@RolesAllowed("MARKETING")
@PageTitle("Manejar tarifas")
public class TarifaView extends VerticalLayout {

    private final ComboBox<Tarifa> tarifaComboBox = new ComboBox<>("Seleccionar Tarifa");
    private final FormLayout formLayout = new FormLayout();
    private final Button guardarButton = new Button("Guardar");

    private final Binder<Tarifa> binder = new Binder<>(Tarifa.class);

    private final TarifaService tarifaService;

    public TarifaView(TarifaService tarifaService) {
        this.tarifaService = tarifaService;

        add(new H3("Cambiar tarifas existentes"));

        // ComboBox
        List<Tarifa> tarifas = tarifaService.getAllTarifas();
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
        formLayout.addFormItem(megas, "Megas Disponibles");
        binder.forField(megas)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableMB, Tarifa::setAvailableMB);

        TextField min = new TextField();
        formLayout.addFormItem(min, "Minutos Disponibles");
        binder.forField(min)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableMin, Tarifa::setAvailableMin);

        TextField sms = new TextField();
        formLayout.addFormItem(sms, "Mensajes Disponibles");
        binder.forField(sms)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableSMS, Tarifa::setAvailableSMS);

        guardarButton.addClickListener(event -> guardarTarifa());
        tarifaComboBox.addValueChangeListener(event -> mostrarDetallesTarifa(event.getValue()));
        add(formLayout, guardarButton);

        // Formulario para añadir nueva tarifa
        H3 nuevaTarifaHeader = new H3("Añadir nueva tarifa");
        add(nuevaTarifaHeader);

        FormLayout nuevaTarifaFormLayout = new FormLayout();

        TextField nuevoNombre = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoNombre, "Nombre");
        binder.bind(nuevoNombre, "nombre");

        TextField nuevoPrecio = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoPrecio, "Precio");
        binder.forField(nuevoPrecio)
                .withConverter(new StringToFloatConverter("Ingrese un valor válido"))
                .bind(Tarifa::getPrecio, Tarifa::setPrecio);

        Checkbox nuevoPermiteRoaming = new Checkbox();
        nuevaTarifaFormLayout.addFormItem(nuevoPermiteRoaming, "Roaming");
        binder.bind(nuevoPermiteRoaming, "permiteRoaming");

        TextField nuevoDescripcion = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoDescripcion, "Descripción");
        binder.bind(nuevoDescripcion, "descripcion");

        Checkbox nuevoFijo = new Checkbox();
        nuevaTarifaFormLayout.addFormItem(nuevoFijo, "Fijo");
        binder.bind(nuevoFijo, "fijo");

        Checkbox nuevoFibra = new Checkbox();
        nuevaTarifaFormLayout.addFormItem(nuevoFibra, "Fibra");
        binder.bind(nuevoFibra, "fibra");

        TextField nuevoMegas = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoMegas, "Megas Disponibles");
        binder.forField(nuevoMegas)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableMB, Tarifa::setAvailableMB);

        TextField nuevoMin = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoMin, "Minutos Disponibles");
        binder.forField(nuevoMin)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableMin, Tarifa::setAvailableMin);

        TextField nuevoSms = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoSms, "Mensajes Disponibles");
        binder.forField(nuevoSms)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableSMS, Tarifa::setAvailableSMS);

        Button agregarButton = new Button("Agregar");
        agregarButton.addClickListener(event -> agregarNuevaTarifa());
        add(nuevaTarifaFormLayout, agregarButton);

    }

    private void mostrarDetallesTarifa(Tarifa tarifa) {
        if (tarifa != null) {
            binder.setBean(tarifa);
        }
    }

    private void agregarNuevaTarifa() {
        Tarifa nuevaTarifa = binder.getBean();
        tarifaService.guardarTarifa(nuevaTarifa);
        Notification.show("Nueva tarifa agregada correctamente");
    }

    private void guardarTarifa() {
        Tarifa tarifa = binder.getBean();
        tarifaService.guardarTarifa(tarifa);
        Notification.show("Tarifa guardada correctamente");
    }
}