package es.uca.iw.views.client_views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.contrato.Contrato;
import es.uca.iw.contrato.ServiciosContrato;
import es.uca.iw.custom_components.CustomCardElement;
import es.uca.iw.simcard.SimCard;
import es.uca.iw.simcard.SimCardService;
import es.uca.iw.tarifa.Tarifa;
import es.uca.iw.tarifa.TarifaService;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route(value = "tarifas", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Tarifas")

public class TarifasView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    private final TarifaService tarifaService;

    private final ServiciosContrato serviciosContrato;

    private final SimCardService simCardService;

    public TarifasView(AuthenticatedUser authenticatedUser, TarifaService tarifaService, ServiciosContrato serviciosContrato, SimCardService simCardService){
        this.authenticatedUser = authenticatedUser;
        this.tarifaService = tarifaService;
        this.serviciosContrato = serviciosContrato;
        this.simCardService = simCardService;

        Optional<Cliente> optionalCliente = authenticatedUser.get();

        optionalCliente.ifPresent(cliente -> {
            if (!serviciosContrato.getContratosByCliente(cliente).isEmpty()){
                List<Contrato> userContracts = serviciosContrato.getContratosByCliente(cliente);
                List<SimCard> userSimCards = new ArrayList<>();
                for(int i = 0; i<userContracts.size(); i++){
                    List<SimCard> simCardByContract = simCardService.getSimCardsByContrato(userContracts.get(i));
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

                    CustomCardElement userTarifaCardElement = new CustomCardElement(userSimCards.get(0).getTarifa().getNombre(),userSimCards.get(0).getTarifa().getAvailableMB().toString(),userSimCards.get(0).getTarifa().getAvailableMin().toString(),userSimCards.get(0).getTarifa().getAvailableSMS().toString(),userSimCards.get(0).getTarifa().isPermiteRoaming(),"Cambiar");
                    contentLayout.add(userTarifaCardElement);

                    add(contentLayout);


                    Tarifa currentPlanOfUser = select.getValue().getTarifa();

                    add(new H4("Tarifas disponibles:"));

                    List<Tarifa> allPlans = tarifaService.getAllTarifas();
                    allPlans.remove(currentPlanOfUser);

                    HorizontalLayout horizontalLayout = new HorizontalLayout();
                    horizontalLayout.getStyle().set("flex-wrap", "wrap");
                    for (Tarifa tarifa : allPlans){
                        CustomCardElement tarifaCardElement = new CustomCardElement(tarifa.getNombre(),tarifa.getAvailableMB().toString(),tarifa.getAvailableMin().toString(),tarifa.getAvailableSMS().toString(),tarifa.isPermiteRoaming(),"Elegir");
                        tarifaCardElement.setButtonClickListener(()->{
                            simCardService.changePlan(select.getValue().getNumber(), tarifa);
                            Notification notification = Notification.show("Tarifa fue cambiado.");
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                            Optional<SimCard> oldSimCard = simCardService.getSimCardByNumber(select.getValue().getNumber());

                            contentLayout.removeAll();
                            Optional<SimCard> newSimCardRef = simCardService.getSimCardByNumber(select.getValue().getNumber());

                            contentLayout.add(new CustomCardElement(newSimCardRef.get().getTarifa().getNombre(),newSimCardRef.get().getTarifa().getAvailableMB().toString(),newSimCardRef.get().getTarifa().getAvailableMin().toString(),newSimCardRef.get().getTarifa().getAvailableSMS().toString(),newSimCardRef.get().getTarifa().isPermiteRoaming(),"Cambiar"));

                            UI.getCurrent().getPage().reload();

                            select.setValue(oldSimCard.get());

                        });
                        tarifaCardElement.getStyle().set("width", "auto");
                        horizontalLayout.add(tarifaCardElement);
                    }

                    select.addValueChangeListener(event -> {
                        contentLayout.removeAll();
                        contentLayout.add(new CustomCardElement(event.getValue().getTarifa().getNombre(),event.getValue().getTarifa().getAvailableMB().toString(),event.getValue().getTarifa().getAvailableMin().toString(),event.getValue().getTarifa().getAvailableSMS().toString(),event.getValue().getTarifa().isPermiteRoaming(),"Cambiar"));

                        horizontalLayout.removeAll();
                        allPlans.clear();
                        allPlans.addAll(tarifaService.getAllTarifas());
                        allPlans.remove(select.getValue().getTarifa());

                        for (Tarifa tarifa : allPlans){
                            CustomCardElement tarifaCardElement = new CustomCardElement(tarifa.getNombre(),tarifa.getAvailableMB().toString(),tarifa.getAvailableMin().toString(),tarifa.getAvailableSMS().toString(),tarifa.isPermiteRoaming(),"Elegir");
                            tarifaCardElement.setButtonClickListener(()->{
                                simCardService.changePlan(select.getValue().getNumber(), tarifa);
                                Notification notification = new Notification("Tarifa fue cambiado.");
                                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                                notification.setDuration(3000);
                                notification.setPosition(Notification.Position.TOP_CENTER);
                                notification.open();

                                contentLayout.removeAll();
                                horizontalLayout.removeAll();
                                allPlans.clear();
                                allPlans.addAll(tarifaService.getAllTarifas());
                                allPlans.remove(tarifa);
                                for(Tarifa tarifa1: allPlans){
                                    CustomCardElement customCardElement = new CustomCardElement(tarifa1.getNombre(),tarifa1.getAvailableMB().toString(),tarifa1.getAvailableMin().toString(),tarifa1.getAvailableSMS().toString(),tarifa1.isPermiteRoaming(),"Cambiar");
                                    customCardElement.getStyle().set("width", "auto");
                                    horizontalLayout.add(customCardElement);
                                }
                                contentLayout.add(new CustomCardElement(tarifa.getNombre(),tarifa.getAvailableMB().toString(),tarifa.getAvailableMin().toString(),event.getValue().getTarifa().getAvailableSMS().toString(),tarifa.isPermiteRoaming(),"Cambiar"));
                             });
                            tarifaCardElement.getStyle().set("width", "auto");
                            horizontalLayout.add(tarifaCardElement);
                        }
                    });

                    add(horizontalLayout);
                }






            }
        });
    }
}