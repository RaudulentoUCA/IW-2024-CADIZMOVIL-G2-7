package es.uca.iw.tarifa;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.simcard.SimCardService;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Route(value = "tarifa-view", layout = MainLayout.class)
@RolesAllowed("MARKETING")
@PageTitle("Manejar tarifas")
public class TarifaView extends VerticalLayout {

    private final ComboBox<Tarifa> tarifaComboBox = new ComboBox<>("Seleccionar Tarifa");
    private final FormLayout formLayout = new FormLayout();
    private final Button guardarButton = new Button("Guardar");

    private final Binder<Tarifa> binderForChangeTarifa = new Binder<>(Tarifa.class);

    private final Binder<Tarifa> binderForAddTarifa = new Binder<>(Tarifa.class);

    Grid<Tarifa> tarifaGrid = new Grid<>(Tarifa.class, false);


    private final TarifaService tarifaService;
    private final SimCardService simCardService;


    public TarifaView(TarifaService tarifaService, SimCardService simCardService) {


        this.tarifaService = tarifaService;
        this.simCardService = simCardService;

        add(new H3("Cambiar tarifas existentes"));

        // ComboBox
        List<Tarifa> tarifas = tarifaService.getAllTarifas();
        tarifaComboBox.setItems(tarifas);
        tarifaComboBox.setItemLabelGenerator(Tarifa::getNombre);

        // Formulario para cambiar tarifa
        formLayout.addFormItem(tarifaComboBox, "Tarifa");

        TextField nombre = new TextField();
        formLayout.addFormItem(nombre, "Nombre");
        binderForChangeTarifa.bind(nombre, "nombre");

        TextField precio = new TextField();
        formLayout.addFormItem(precio, "Precio");
        binderForChangeTarifa.forField(precio)
                .withConverter(new StringToFloatConverter("Ingrese un valor válido"))
                .bind(Tarifa::getPrecio, Tarifa::setPrecio);

        Checkbox permiteRoaming = new Checkbox();
        formLayout.addFormItem(permiteRoaming, "Roaming");
        binderForChangeTarifa.bind(permiteRoaming, "permiteRoaming");

        TextField descripcion = new TextField();
        formLayout.addFormItem(descripcion, "Descripción");
        binderForChangeTarifa.bind(descripcion, "descripcion");

        Checkbox fijo = new Checkbox();
        formLayout.addFormItem(fijo, "Fijo");
        binderForChangeTarifa.bind(fijo, "fijo");

        Checkbox fibra = new Checkbox();
        formLayout.addFormItem(fibra, "Fibra");
        binderForChangeTarifa.bind(fibra, "fibra");

        TextField megas = new TextField();
        formLayout.addFormItem(megas, "Megas Disponibles");
        binderForChangeTarifa.forField(megas)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableMB, Tarifa::setAvailableMB);

        TextField min = new TextField();
        formLayout.addFormItem(min, "Minutos Disponibles");
        binderForChangeTarifa.forField(min)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableMin, Tarifa::setAvailableMin);

        TextField sms = new TextField();
        formLayout.addFormItem(sms, "Mensajes Disponibles");
        binderForChangeTarifa.forField(sms)
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
        binderForAddTarifa.forField(nuevoNombre).bind(Tarifa::getNombre, Tarifa::setNombre);

        TextField nuevoPrecio = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoPrecio, "Precio");
        binderForAddTarifa.forField(nuevoPrecio)
                .withConverter(new StringToFloatConverter("Ingrese un valor válido"))
                .bind(Tarifa::getPrecio, Tarifa::setPrecio);

        Checkbox nuevoPermiteRoaming = new Checkbox();
        nuevaTarifaFormLayout.addFormItem(nuevoPermiteRoaming, "Roaming");
        binderForAddTarifa.forField(nuevoPermiteRoaming).bind(Tarifa::isPermiteRoaming, Tarifa::setPermiteRoaming);

        TextField nuevoDescripcion = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoDescripcion, "Descripción");
        binderForAddTarifa.forField(nuevoDescripcion).bind(Tarifa::getDescripcion, Tarifa::setDescripcion);

        Checkbox nuevoFijo = new Checkbox();
        nuevaTarifaFormLayout.addFormItem(nuevoFijo, "Fijo");
        binderForAddTarifa.forField(nuevoFijo).bind(Tarifa::isFijo, Tarifa::setFijo);

        Checkbox nuevoFibra = new Checkbox();
        nuevaTarifaFormLayout.addFormItem(nuevoFibra, "Fibra");
        binderForAddTarifa.forField(nuevoFibra).bind(Tarifa::isFibra, Tarifa::setFibra);

        TextField nuevoMegas = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoMegas, "Megas Disponibles");
        binderForAddTarifa.forField(nuevoMegas)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableMB, Tarifa::setAvailableMB);

        TextField nuevoMin = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoMin, "Minutos Disponibles");
        binderForAddTarifa.forField(nuevoMin)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableMin, Tarifa::setAvailableMin);

        TextField nuevoSms = new TextField();
        nuevaTarifaFormLayout.addFormItem(nuevoSms, "Mensajes Disponibles");
        binderForAddTarifa.forField(nuevoSms)
                .withConverter(new StringToIntegerConverter("Ingrese un valor válido"))
                .bind(Tarifa::getAvailableSMS, Tarifa::setAvailableSMS);

        Button agregarButton = new Button("Agregar");
        agregarButton.addClickListener(event -> agregarNuevaTarifa());
        add(nuevaTarifaFormLayout, agregarButton);

        add(new H3("Lista de todas tarifas:"));
        List<Tarifa> allTarifas = tarifaService.getAllTarifas();
        Set<Long> assignedTarifasIds = simCardService.getUniqueTarifaIds();
        tarifaGrid.setAllRowsVisible(true);
        tarifaGrid.addColumn(Tarifa::getId).setHeader("№");
        tarifaGrid.addColumn(Tarifa::getNombre).setHeader("Nombre");
        tarifaGrid.addColumn(Tarifa::getDescripcion).setHeader("Descripcion");
        tarifaGrid.addColumn(Tarifa::getPrecio).setHeader("Precio");
        tarifaGrid.addColumn(tarifa -> tarifa.isFibra() ? "Sí" : "No").setHeader("Fibra");
        tarifaGrid.addColumn(tarifa -> tarifa.isFijo() ? "Sí" : "No").setHeader("Fijo");
        tarifaGrid.addColumn(tarifa -> tarifa.isPermiteRoaming() ? "Sí" : "No").setHeader("Roaming");
        tarifaGrid.addColumn(Tarifa::getAvailableMB).setHeader("Mb.");
        tarifaGrid.addColumn(Tarifa::getAvailableMin).setHeader("Min.");
        tarifaGrid.addColumn(Tarifa::getAvailableSMS).setHeader("SMS");

        // Notification message for used tarifa
        Notification existRelationNotification = new Notification();
        existRelationNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text("No puedes borrar tarifa que usa el cliente!"));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            existRelationNotification.close();
        });

        HorizontalLayout layoutForNotification = new HorizontalLayout(text, closeButton);
        layoutForNotification.setAlignItems(Alignment.CENTER);

        existRelationNotification.add(layoutForNotification);
        existRelationNotification.setPosition(Notification.Position.MIDDLE);


        tarifaGrid.addColumn(
                new ComponentRenderer<>(Button::new, (button, tarifa) -> {
                    if (assignedTarifasIds.contains(tarifa.getId())){
                        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                                ButtonVariant.LUMO_ERROR,
                                ButtonVariant.LUMO_TERTIARY);
                        button.addClickListener(e -> {
                            existRelationNotification.open();
                        });
                        button.setIcon(new Icon(VaadinIcon.INFO));
                    }
                    else {
                        button.addThemeVariants(ButtonVariant.LUMO_ICON,
                                ButtonVariant.LUMO_ERROR,
                                ButtonVariant.LUMO_TERTIARY);
                        button.addClickListener(e -> {
                            eliminarTarifa(tarifa);
                            actualizarGrid();
                        });
                        button.setIcon(new Icon(VaadinIcon.TRASH));
                    }
                })).setHeader("");
        tarifaGrid.setItems(allTarifas);
        add(tarifaGrid);
    }

    private void mostrarDetallesTarifa(Tarifa tarifa) {
        if (tarifa != null) {
            binderForChangeTarifa.setBean(tarifa);
        }
    }

    private void agregarNuevaTarifa() {
        Tarifa nuevaTarifa = binderForAddTarifa.getBean();
        tarifaService.guardarTarifa(nuevaTarifa);
        Notification.show("Nueva tarifa agregada correctamente");
    }

    private void guardarTarifa() {
        Tarifa tarifa = binderForChangeTarifa.getBean();
        tarifaService.guardarTarifa(tarifa);
        Notification.show("Tarifa guardada correctamente");
    }

    // Method to delete Tarifa
    private void eliminarTarifa(Tarifa tarifa) {
        if (tarifa != null) {
            Optional<Tarifa> tarifaToDelete = tarifaService.getTarifaById(tarifa.getId());
            tarifaToDelete.ifPresent(tarifaService::removeTarifa);
            Notification.show("Tarifa eliminada correctamente");
        }
    }

    // Method to update the grid after deletion
    private void actualizarGrid() {
        List<Tarifa> updatedTarifas = tarifaService.getAllTarifas();
        tarifaGrid.setItems(updatedTarifas);
    }
}