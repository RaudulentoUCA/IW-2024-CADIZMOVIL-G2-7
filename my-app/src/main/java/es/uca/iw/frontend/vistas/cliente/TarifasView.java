package es.uca.iw.frontend.vistas.cliente;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.componentes.AuthenticatedUser;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.clases.Contrato;
import es.uca.iw.backend.servicios.ServicioContrato;
import es.uca.iw.frontend.custom_components.CustomCardElement;
import es.uca.iw.backend.clases.SimCard;
import es.uca.iw.backend.servicios.ServicioSimCard;
import es.uca.iw.backend.clases.Tarifa;
import es.uca.iw.backend.servicios.ServicioTarifa;
import es.uca.iw.frontend.vistas.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import com.vaadin.flow.component.dialog.Dialog;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route(value = "tarifas", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Tarifas")

public class TarifasView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    private final ServicioTarifa servicioTarifa;

    private final ServicioContrato servicioContrato;

    private final ServicioSimCard servicioSimCard;

    private final PasswordEncoder passwordEncoder;

    public TarifasView(AuthenticatedUser authenticatedUser, ServicioTarifa servicioTarifa, ServicioContrato servicioContrato, ServicioSimCard servicioSimCard, PasswordEncoder passwordEncoder){
        this.authenticatedUser = authenticatedUser;
        this.servicioTarifa = servicioTarifa;
        this.servicioContrato = servicioContrato;
        this.servicioSimCard = servicioSimCard;
        this.passwordEncoder = passwordEncoder;

        Optional<Cliente> optionalCliente = authenticatedUser.get();

        optionalCliente.ifPresent(cliente -> {
            if (!servicioContrato.getContratosByCliente(cliente).isEmpty()){
                List<Contrato> userContracts = servicioContrato.getContratosByCliente(cliente);
                List<SimCard> userSimCards = new ArrayList<>();
                for(int i = 0; i<userContracts.size(); i++){
                    List<SimCard> simCardByContract = servicioSimCard.getSimCardsByContrato(userContracts.get(i));
                    userSimCards.addAll(simCardByContract);
                }
                Select<SimCard> select = new Select<>();

                FlexLayout contentLayout = new FlexLayout();
                contentLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
                contentLayout.getStyle().set("flex-wrap", "wrap");
                contentLayout.getStyle().set("display", "flex");
                contentLayout.getStyle().set("width", "100%");
                contentLayout.getStyle().set("justify-content", "space-around");

                if (!userSimCards.isEmpty()){
                    add(new H5("Elige tarjeta SIM"));
                    select.getStyle().setOverflow(Style.Overflow.INITIAL);

                    select.setItemLabelGenerator(c->String.valueOf(c.getNumber()));
                    select.setItems(userSimCards);
                    select.setValue(userSimCards.get(0));


                    add(select);

                    CustomCardElement userTarifaCardElement = new CustomCardElement(userSimCards.get(0).getTarifa().getNombre(),userSimCards.get(0).getTarifa().getAvailableMB().toString(),userSimCards.get(0).getTarifa().getAvailableMin().toString(),userSimCards.get(0).getTarifa().getAvailableSMS().toString(),userSimCards.get(0).getTarifa().isPermiteRoaming(),"Cambiar", String.format("%.2f", userSimCards.get(0).getTarifa().getPrecio()));
                    contentLayout.add(userTarifaCardElement);

                    add(contentLayout);


                    Tarifa currentPlanOfUser = select.getValue().getTarifa();

                    add(new H4("Tarifas disponibles:"));

                    List<Tarifa> allPlans = servicioTarifa.getAllTarifas();
                    allPlans.remove(currentPlanOfUser);

                    HorizontalLayout horizontalLayout = new HorizontalLayout();
                    horizontalLayout.getStyle().set("flex-wrap", "wrap");
                    for (Tarifa tarifa : allPlans){
                        CustomCardElement tarifaCardElement = new CustomCardElement(tarifa.getNombre(),tarifa.getAvailableMB().toString(),tarifa.getAvailableMin().toString(),tarifa.getAvailableSMS().toString(),tarifa.isPermiteRoaming(),"Elegir", String.format("%.2f", tarifa.getPrecio()));
                        tarifaCardElement.setButtonClickListener(() -> {
                            Dialog confirmDialog = new Dialog();
                            confirmDialog.setCloseOnEsc(false);
                            confirmDialog.setCloseOnOutsideClick(false);

                            VerticalLayout dialogLayout = new VerticalLayout();
                            Paragraph parrafo = new Paragraph("¿Estás seguro de cambiar la tarifa?");
                            dialogLayout.add(parrafo);
                            parrafo = new Paragraph("Escribe tu contraseña para confirmar la operación");
                            dialogLayout.add(parrafo);

                            // Contraseña para re-autenticación
                            PasswordField passwordField = new PasswordField("Contraseña");
                            dialogLayout.add(passwordField);

                            // Botones confirmar y cancelar
                            Button confirmButton = new Button("Confirmar", event -> {
                                if (isValidPassword(passwordField.getValue())) {
                                    // Realizar la acción después de confirmar
                                    servicioSimCard.changePlan(select.getValue().getNumber(), tarifa);
                                    Notification notification = Notification.show("Tarifa fue cambiada.");
                                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                                    Optional<SimCard> oldSimCard = servicioSimCard.getSimCardByNumber(select.getValue().getNumber());

                                    contentLayout.removeAll();
                                    Optional<SimCard> newSimCardRef = servicioSimCard.getSimCardByNumber(select.getValue().getNumber());

                                    contentLayout.add(new CustomCardElement(newSimCardRef.get().getTarifa().getNombre(), newSimCardRef.get().getTarifa().getAvailableMB().toString(), newSimCardRef.get().getTarifa().getAvailableMin().toString(), newSimCardRef.get().getTarifa().getAvailableSMS().toString(), newSimCardRef.get().getTarifa().isPermiteRoaming(), "Cambiar", String.format("%.2f", newSimCardRef.get().getTarifa().getPrecio())));

                                    UI.getCurrent().getPage().reload();

                                    select.setValue(oldSimCard.get());
                                    confirmDialog.close();
                                } else {
                                    Notification.show("Contraseña incorrecta. Por favor, inténtelo de nuevo.");
                                }
                            });
                            confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                            Button cancelButton = new Button("Cancelar", event -> {
                                // Cancelar
                                confirmDialog.close();
                            });

                            dialogLayout.add(confirmButton, cancelButton);

                            confirmDialog.add(dialogLayout);

                            // Mostrar el cuadro de diálogo
                            confirmDialog.open();
                        });
                        tarifaCardElement.getStyle().set("width", "auto");
                        horizontalLayout.add(tarifaCardElement);
                    }

                    select.addValueChangeListener(event -> {
                        contentLayout.removeAll();
                        contentLayout.add(new CustomCardElement(event.getValue().getTarifa().getNombre(),event.getValue().getTarifa().getAvailableMB().toString(),event.getValue().getTarifa().getAvailableMin().toString(),event.getValue().getTarifa().getAvailableSMS().toString(),event.getValue().getTarifa().isPermiteRoaming(),"Cambiar", String.format("%.2f", event.getValue().getTarifa().getPrecio())));

                        horizontalLayout.removeAll();
                        allPlans.clear();
                        allPlans.addAll(servicioTarifa.getAllTarifas());
                        allPlans.remove(select.getValue().getTarifa());
                    });
                    add(horizontalLayout);
                }
            }
        });
    }

    private boolean isValidPassword(String enteredPassword) {
        Optional<Cliente> cliente = authenticatedUser.get();
        if (cliente.isPresent()) {
            String storedHashedPassword = cliente.get().getPassword();
            return passwordEncoder.matches(enteredPassword, storedHashedPassword);
        }
        return false;
    }
}