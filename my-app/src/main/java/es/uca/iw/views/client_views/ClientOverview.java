package es.uca.iw.views.client_views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.contract.Contract;
import es.uca.iw.custom_components.CustomCardElement;
import es.uca.iw.simcard.SimCardService;
import es.uca.iw.views.MainLayout;
import com.vaadin.flow.component.textfield.TextField;
import jakarta.annotation.security.RolesAllowed;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


//TODO Add opportunity to choose contract to show information
@Route(value = "general", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("General")
public class ClientOverview extends VerticalLayout {
    private final SimCardService simCardService;
    private final AuthenticatedUser authenticatedUser;

    public ClientOverview(SimCardService simCardService, AuthenticatedUser authenticatedUser) {
        this.simCardService = simCardService;
        this.authenticatedUser = authenticatedUser;
        Optional<Cliente> optionalCliente = authenticatedUser.get();
        setWidth("100%");
        getStyle().set("flex-grow", "1");

        optionalCliente.ifPresent(cliente -> {
            add(new H1(" \uD83D\uDC4B, " + cliente.getNombre() + " " + cliente.getApellidos()));

            if (!cliente.getContracts().isEmpty()){
                add(new H4("Tu contratos:"));
                List<Contract> contracts = cliente.getContracts();
                Grid<Contract> grid = new Grid<>(Contract.class, false);
                grid.setAllRowsVisible(true);
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                grid.addColumn(contract -> contract.getSimCard().getNumber()).setHeader("Numero de tarjeta SIM");
                grid.addColumn(contract -> contract.getStartDate().format(dateFormatter)).setHeader("Fecha de inicio");
                grid.addColumn(contract -> contract.getEndDate().format(dateFormatter)).setHeader("Fecha de fin");
                grid.addColumn(contract -> contract.getSimCard().getTarifa().getPrecio()).setHeader("Precio");
                grid.addColumn(Contract::getDiscount).setHeader("Descuento");
                grid.setItems(contracts);
                add(grid);

                add(new H4("Tu saldo:"));

                // Minutes progress bar
                ProgressBar progressBarMin = new ProgressBar();
                double percentageMin = (double) (cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableMin() - cliente.getContracts().get(0).getSimCard().getUsedMinutes()) / (cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableMin());
                progressBarMin.setValue(percentageMin);

                NativeLabel progressBarLabelTextMin = new NativeLabel("Llamadas:");
                progressBarLabelTextMin.setId("pblabel");
                progressBarMin.getElement().setAttribute("aria-labelledby", "pblabel");
                progressBarMin.getStyle().set("--lumo-primary-color", "#5AC2F7 !important");

                Span progressBarLabelValue = new Span(cliente.getContracts().get(0).getSimCard().getUsedMinutes().toString() + "/" + cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableMin().toString() + " min.");
                HorizontalLayout progressBarLabel = new HorizontalLayout(progressBarLabelTextMin, progressBarLabelValue);
                progressBarLabel.setJustifyContentMode(JustifyContentMode.BETWEEN);
                progressBarLabel.setWidth("100%");

                add(progressBarLabel, progressBarMin);

                // Mb progress bar
                ProgressBar progressBarMb = new ProgressBar();
                double percentageMb = (double) (cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableMB() - cliente.getContracts().get(0).getSimCard().getUsedMb()) / (cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableMB());
                progressBarMb.setValue(percentageMb);

                NativeLabel progressBarLabelTextMb = new NativeLabel("Internet:");
                progressBarLabelTextMb.setId("pblabel");
                progressBarMb.getElement().setAttribute("aria-labelledby", "pblabel");
                progressBarMb.getStyle().set("--lumo-primary-color", "#3ABB66 !important");

                Span progressBarLabelValueMb = new Span(cliente.getContracts().get(0).getSimCard().getUsedMb().toString() + "/" + cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableMB().toString() + " mb.");
                HorizontalLayout progressBarLabelMb = new HorizontalLayout(progressBarLabelTextMb, progressBarLabelValueMb);
                progressBarLabelMb.setJustifyContentMode(JustifyContentMode.BETWEEN);
                progressBarLabelMb.setWidth("100%");

                add(progressBarLabelMb, progressBarMb);


                // SMS progress bar
                ProgressBar progressBarSMS = new ProgressBar();
                double percentageSMS = (double) (cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableSMS() - cliente.getContracts().get(0).getSimCard().getUsedSms()) / (cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableSMS());
                progressBarSMS.setValue(percentageSMS);

                NativeLabel progressBarLabelTextSMS = new NativeLabel("Mensajes:");
                progressBarLabelTextSMS.setId("pblabel");
                progressBarSMS.getElement().setAttribute("aria-labelledby", "pblabel");
                progressBarSMS.getStyle().set("--lumo-primary-color", "#EBFC84 !important");

                Span progressBarLabelValueSMS = new Span(cliente.getContracts().get(0).getSimCard().getUsedSms().toString() + "/" + cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableSMS().toString() + " SMS");
                HorizontalLayout progressBarLabelSMS = new HorizontalLayout(progressBarLabelTextSMS, progressBarLabelValueSMS);
                progressBarLabelSMS.setJustifyContentMode(JustifyContentMode.BETWEEN);
                progressBarLabelSMS.setWidth("100%");

                add(progressBarLabelSMS, progressBarSMS);

                // Tarifa card y recargar option
                add(new H4("Tu tarifa:"));

                FlexLayout contentLayout = new FlexLayout();
                contentLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
                contentLayout.getStyle().set("flex-wrap", "wrap");
                contentLayout.getStyle().set("display", "flex");
                contentLayout.getStyle().set("width", "100%");
                contentLayout.getStyle().set("justify-content", "space-around");

                CustomCardElement userTarifaCardElement = new CustomCardElement(cliente.getContracts().get(0).getSimCard().getTarifa().getNombre(), cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableMB().toString(), cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableMin().toString(), cliente.getContracts().get(0).getSimCard().getTarifa().getAvailableSMS().toString(), cliente.getContracts().get(0).getSimCard().getTarifa().isPermiteRoaming(), "Cambiar");
                userTarifaCardElement.getStyle().set("width", "auto");

                contentLayout.add(userTarifaCardElement);

                Div balanceDiv = new Div();
                Image walletImage = new Image("icons/wallet_icon.png", "wallet icon");
                walletImage.setWidth("80px");
                walletImage.setHeight("80px");
                H4 saldo = new H4(cliente.getContracts().get(0).getSimCard().getBalance() + "€");
                saldo.getStyle().set("margin", "10px 0px 10px 0px");

                Dialog recargarDialog = new Dialog();
                recargarDialog.getElement().setAttribute("aria-label", "Recargar tarjeta SIM");

                VerticalLayout dialogLayout = createDialogLayout(recargarDialog, cliente, simCardService);
                recargarDialog.add(dialogLayout);

                Button recargarButton = new Button("Recargar", e -> recargarDialog.open());
                balanceDiv.add(walletImage, saldo, recargarButton);
                balanceDiv.getStyle().set("flex-wrap", "wrap");
                balanceDiv.getStyle().set("display", "flex");
                balanceDiv.getStyle().set("flex-direction", "column");
                balanceDiv.getStyle().set("justify-content", "center");
                balanceDiv.getStyle().set("align-items", "center");
                contentLayout.add(balanceDiv);

                add(contentLayout, recargarDialog);
            }
            else {
                add(new H4("Todavia no tienes el contrato"));
            }
        });
    }

    private static VerticalLayout createDialogLayout(Dialog dialog, Cliente cliente, SimCardService simCardService) {
        H2 headline = new H2("Recargar tarjeta SIM");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        TextField amountOfMoneyField = new TextField("Cantidad");
        VerticalLayout fieldLayout = new VerticalLayout(amountOfMoneyField);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Cancel", e -> dialog.close());
        Button saveButton = new Button("Pagar", e -> {
            if (!amountOfMoneyField.isEmpty()){
                simCardService.addMoney(cliente.getContracts().get(0).getSimCard().getNumber(), Float.parseFloat(amountOfMoneyField.getValue()));
            }
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }

}
