package es.uca.iw.views.client_views;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.contract.Contract;
import es.uca.iw.custom_components.CustomCardElement;
import es.uca.iw.tarifa.Tarifa;
import es.uca.iw.tarifa.TarifaService;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Optional;

@Route(value = "tarifas", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Tarifas")

public class TarifasView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    private final TarifaService tarifaService;
    public TarifasView(AuthenticatedUser authenticatedUser, TarifaService tarifaService){
        this.authenticatedUser = authenticatedUser;
        this.tarifaService = tarifaService;

        Optional<Cliente> optionalCliente = authenticatedUser.get();

        optionalCliente.ifPresent(cliente -> {
            if (!cliente.getContracts().isEmpty()){
                add(new H4("Tu tarifa:"));
                List<Contract> contracts = cliente.getContracts();
                if (!contracts.isEmpty()){
                    Tarifa currentPlanOfUser = contracts.get(0).getSimCard().getTarifa();

                    CustomCardElement userTarifaCardElement = new CustomCardElement(currentPlanOfUser.getNombre(),currentPlanOfUser.getAvailableMB().toString(),currentPlanOfUser.getAvailableMin().toString(),currentPlanOfUser.getAvailableSMS().toString(),currentPlanOfUser.isPermiteRoaming(),"Cambiar");

                    add(userTarifaCardElement);

                    add(new H4("Tarifas disponibles:"));

                    List<Tarifa> allPlans = this.tarifaService.getAllTarifas();
                    allPlans.remove(currentPlanOfUser);

                    HorizontalLayout horizontalLayout = new HorizontalLayout();
                    horizontalLayout.getStyle().set("flex-wrap", "wrap");
                    for (Tarifa tarifa : allPlans){
                        CustomCardElement tarifaCardElement = new CustomCardElement(tarifa.getNombre(),tarifa.getAvailableMB().toString(),tarifa.getAvailableMin().toString(),tarifa.getAvailableSMS().toString(),tarifa.isPermiteRoaming(),"Elegir");
                        tarifaCardElement.getStyle().set("width", "auto");
                        horizontalLayout.add(tarifaCardElement);
                    }
                    add(horizontalLayout);

                }


            }
        });
    }
}