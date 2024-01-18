package es.uca.iw.views.client_views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
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
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.formulario.FormularioView;
import jakarta.annotation.security.RolesAllowed;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//TODO Add opportunity to choose contract to show information
@Route(value = "general", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("General")
public class ClientOverview extends VerticalLayout {
    private final SimCardService simCardService;

    private final ServiciosContrato contratoService;
    private final AuthenticatedUser authenticatedUser;
    public ClientOverview(SimCardService simCardService, ServiciosContrato contratoService, AuthenticatedUser authenticatedUser) {
        this.simCardService = simCardService;
        this.contratoService = contratoService;
        this.authenticatedUser = authenticatedUser;

        Optional<Cliente> optionalCliente = authenticatedUser.get();
        setWidth("100%");
        getStyle().set("flex-grow", "1");

        optionalCliente.ifPresent(cliente -> {
            add(new H1(" \uD83D\uDC4B, " + cliente.getNombre() + " " + cliente.getApellidos()));

            List<Contrato> userContracts = contratoService.getContratosByCliente(cliente);

            if (!userContracts.isEmpty()){
                add(new H4("Tu contratos:"));
                Grid<Contrato> grid = new Grid<>(Contrato.class, false);
                grid.setAllRowsVisible(true);
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                grid.addColumn(Contrato::getId).setHeader("Numero de contrato");
                grid.addColumn(contract -> contract.getFechaInicio().format(dateFormatter)).setHeader("Fecha de inicio");
                grid.addColumn(contract -> contract.getFechaFin().format(dateFormatter)).setHeader("Fecha de fin");
                grid.addColumn(Contrato::getDescuento).setHeader("Descuento");
                grid.setItems(userContracts);
                add(grid);

                add(new H5("Elige numero de contrato para ver detalles"));
                Select<Contrato> select = new Select<>();
                select.getStyle().setOverflow(Style.Overflow.INITIAL);
                select.setItemLabelGenerator(c->String.valueOf(c.getId()));
                select.setItems(userContracts);

                add(select);

                VerticalLayout simCardsVerticalLayout = new VerticalLayout();
                simCardsVerticalLayout.setPadding(false);

                add(simCardsVerticalLayout);

                VerticalLayout simCardInfoLayout = new VerticalLayout();
                simCardInfoLayout.setPadding(false);
                add(simCardInfoLayout);

                H5 noSimCardToContract = new H5("No hay ninguna tarjeta relacionada con este contrsto :(");
                add(noSimCardToContract);
                noSimCardToContract.setVisible(false);

                H4 simCardsContractH4 = new H4("Tarjetas relacionado con contrato №" + select.getValue() + ":");

                Grid<SimCard> gridSimCards = new Grid<>(SimCard.class, false);
                gridSimCards.setAllRowsVisible(true);
                gridSimCards.addColumn(SimCard::getNumber).setHeader("Numero");
                gridSimCards.addColumn(simCard -> simCard.isActive() ? "Sí" : "No").setHeader("Activado");
                gridSimCards.addColumn(
                        new ComponentRenderer<>(Button::new, (button, simCard) -> {
                            button.addThemeVariants(ButtonVariant.LUMO_ICON,
                                    ButtonVariant.LUMO_TERTIARY,
                                    ButtonVariant.LUMO_TERTIARY);
                            button.addClickListener(e -> {
                                simCardInfoLayout.removeAll();

                                H4 saldoText = new H4("Saldo de tarjeta #" + simCard.getNumber().toString() + ": ");
                                simCardInfoLayout.add(saldoText);

                                // Minutes progress bar
                                ProgressBar progressBarMin = new ProgressBar();
                                Integer availableMin = simCard.getTarifa().getAvailableMin();
                                Integer spentMin = simCard.getUsedMinutes();
                                double percentageMin = (((double)availableMin- (double) spentMin) /availableMin);
                                if (percentageMin < 0.0){percentageMin=0.0;}
                                progressBarMin.setValue(percentageMin);

                                NativeLabel progressBarLabelTextMin = new NativeLabel("Llamadas:");
                                progressBarLabelTextMin.setId("pblabel");
                                progressBarMin.getElement().setAttribute("aria-labelledby", "pblabel");
                                progressBarMin.getStyle().set("--lumo-primary-color", "#5AC2F7 !important");

                                Span progressBarLabelValue = new Span(spentMin + "/" + availableMin + " min.");
                                HorizontalLayout progressBarLabel = new HorizontalLayout(progressBarLabelTextMin, progressBarLabelValue);
                                progressBarLabel.setJustifyContentMode(JustifyContentMode.BETWEEN);
                                progressBarLabel.setWidth("100%");
                                simCardInfoLayout.add(progressBarLabel, progressBarMin);

                                // Mb progress bar
                                ProgressBar progressBarMb = new ProgressBar();
                                Integer availableMb = simCard.getTarifa().getAvailableMB();
                                Integer spentMb = simCard.getUsedMb();
                                double percentageMb = (((double)availableMb-(double)spentMb)/availableMb);
                                if (percentageMb < 0.0){percentageMb=0.0;}
                                progressBarMb.setValue(percentageMb);

                                NativeLabel progressBarLabelTextMb = new NativeLabel("Internet:");
                                progressBarLabelTextMb.setId("pblabel");
                                progressBarMb.getElement().setAttribute("aria-labelledby", "pblabel");
                                progressBarMb.getStyle().set("--lumo-primary-color", "#3ABB66 !important");

                                Span progressBarLabelValueMb = new Span(spentMb + "/" +availableMb + " mb.");
                                HorizontalLayout progressBarLabelMb = new HorizontalLayout(progressBarLabelTextMb, progressBarLabelValueMb);
                                progressBarLabelMb.setJustifyContentMode(JustifyContentMode.BETWEEN);
                                progressBarLabelMb.setWidth("100%");

                                simCardInfoLayout.add(progressBarLabelMb, progressBarMb);


                                // SMS progress bar
                                ProgressBar progressBarSMS = new ProgressBar();
                                Integer availableSMS = simCard.getTarifa().getAvailableSMS();
                                Integer spentSMS = simCard.getUsedSms();
                                double percentageSMS = (((double) availableSMS - (double) spentSMS) / availableSMS);
                                if (percentageSMS < 0.0){percentageSMS=0.0;}
                                progressBarSMS.setValue(percentageSMS);

                                NativeLabel progressBarLabelTextSMS = new NativeLabel("Mensajes:");
                                progressBarLabelTextSMS.setId("pblabel");
                                progressBarSMS.getElement().setAttribute("aria-labelledby", "pblabel");
                                progressBarSMS.getStyle().set("--lumo-primary-color", "#EBFC84 !important");

                                Span progressBarLabelValueSMS = new Span(spentSMS + "/" + availableSMS + " SMS");
                                HorizontalLayout progressBarLabelSMS = new HorizontalLayout(progressBarLabelTextSMS, progressBarLabelValueSMS);
                                progressBarLabelSMS.setJustifyContentMode(JustifyContentMode.BETWEEN);
                                progressBarLabelSMS.setWidth("100%");

                                simCardInfoLayout.add(progressBarLabelSMS, progressBarSMS);

                                // Tarifa card y recargar option
                                simCardInfoLayout.add(new H4("Tarifa:"));

                                FlexLayout contentLayout = new FlexLayout();
                                contentLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
                                contentLayout.getStyle().set("flex-wrap", "wrap");
                                contentLayout.getStyle().set("display", "flex");
                                contentLayout.getStyle().set("width", "100%");
                                contentLayout.getStyle().set("justify-content", "space-around");

                                CustomCardElement userTarifaCardElement = new CustomCardElement(simCard.getTarifa().getNombre(), simCard.getTarifa().getAvailableMB().toString(), simCard.getTarifa().getAvailableMin().toString(), simCard.getTarifa().getAvailableSMS().toString(), simCard.getTarifa().isPermiteRoaming(), "Cambiar");
                                userTarifaCardElement.setButtonClickListener(()->{
                                    UI.getCurrent().navigate(TarifasView.class);});
                                userTarifaCardElement.getStyle().set("width", "auto");

                                contentLayout.add(userTarifaCardElement);

                                simCardInfoLayout.add(contentLayout);

                                simCardInfoLayout.setVisible(true);
                            });
                            button.setIcon(new Icon(VaadinIcon.EYE));
                        })).setHeader("");



                select.addValueChangeListener(event -> {
                    // Update the text based on the selected value
                    simCardsContractH4.setText("Tarjetas relacionadas con contrato №" + event.getValue().getId() + ":");
                    gridSimCards.setItems(simCardService.getSimCardsByContrato(event.getValue()));
                    simCardInfoLayout.setVisible(false);


                    if (simCardService.getSimCardsByContrato(select.getValue()).isEmpty()){
                        simCardsVerticalLayout.remove(simCardsContractH4, gridSimCards);
                        simCardsVerticalLayout.setVisible(false);
                        noSimCardToContract.setVisible(true);
                    }
                    else {
                        simCardsVerticalLayout.add(simCardsContractH4);
                        simCardsVerticalLayout.add(gridSimCards);
                        simCardsVerticalLayout.setVisible(true);
                        noSimCardToContract.setVisible(false);
                    }
                });

                select.setValue(userContracts.get(0));








//                Div balanceDiv = new Div();
//                Image walletImage = new Image("icons/wallet_icon.png", "wallet icon");
//                walletImage.setWidth("80px");
//                walletImage.setHeight("80px");
//                H4 saldo = new H4(cliente.getContracts().get(0).getSimCard().getBalance() + "€");
//                saldo.getStyle().set("margin", "10px 0px 10px 0px");
//
//                Dialog recargarDialog = new Dialog();
//                recargarDialog.getElement().setAttribute("aria-label", "Recargar tarjeta SIM");

//                VerticalLayout dialogLayout = createDialogLayout(recargarDialog, cliente, simCardService);
//                recargarDialog.add(dialogLayout);

//                Button recargarButton = new Button("Recargar", e -> recargarDialog.open());
//                balanceDiv.add(walletImage, saldo, recargarButton);
//                balanceDiv.getStyle().set("flex-wrap", "wrap");
//                balanceDiv.getStyle().set("display", "flex");
//                balanceDiv.getStyle().set("flex-direction", "column");
//                balanceDiv.getStyle().set("justify-content", "center");
//                balanceDiv.getStyle().set("align-items", "center");
//                contentLayout.add(balanceDiv);
//
//                add(contentLayout, recargarDialog);
            }
            else {
                add(new H4("Todavia no tienes el contrato"));
            }
        });
    }

//    private static VerticalLayout createDialogLayout(Dialog dialog, Cliente cliente, SimCardService simCardService) {
//        H2 headline = new H2("Recargar tarjeta SIM");
//        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
//                .set("font-size", "1.5em").set("font-weight", "bold");
//
//        TextField amountOfMoneyField = new TextField("Cantidad");
//        VerticalLayout fieldLayout = new VerticalLayout(amountOfMoneyField);
//        fieldLayout.setSpacing(false);
//        fieldLayout.setPadding(false);
//        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
//
//        Button cancelButton = new Button("Cancel", e -> dialog.close());
//        Button saveButton = new Button("Pagar", e -> {
//            if (!amountOfMoneyField.isEmpty()){
//                simCardService.addMoney(cliente.getContracts().get(0).getSimCard().getNumber(), Float.parseFloat(amountOfMoneyField.getValue()));
//            }
//            dialog.close();
//        });
//        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
//                saveButton);
//        buttonLayout
//                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
//
//        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
//                buttonLayout);
//        dialogLayout.setPadding(false);
//        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
//        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
//
//        return dialogLayout;
//    }

}
