package es.uca.iw.frontend.vistas.cliente;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;


//TODO Add opportunity to choose contract to show information
@Route(value = "general", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("General")
public class ClientOverview extends VerticalLayout {
    private final ServicioSimCard servicioSimCard;

    private final ServicioContrato contratoService;
    private final ServicioTarifa servicioTarifa;

    private final AuthenticatedUser authenticatedUser;

    public ClientOverview(ServicioSimCard servicioSimCard, ServicioContrato contratoService, ServicioTarifa servicioTarifa, AuthenticatedUser authenticatedUser) {
        this.servicioSimCard = servicioSimCard;
        this.contratoService = contratoService;
        this.servicioTarifa = servicioTarifa;
        this.authenticatedUser = authenticatedUser;

        Optional<Cliente> optionalCliente = authenticatedUser.get();
        setWidth("100%");
        getStyle().set("flex-grow", "1");

        Random random = new Random();
        Button activateSimCardConTarifaBasica = new Button("Activar tarjeta sim con Tárifa básica");


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

                H5 noSimCardToContract = new H5("No hay ninguna tarjeta relacionada con este contrato :(");
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

                                CustomCardElement userTarifaCardElement = new CustomCardElement(simCard.getTarifa().getNombre(), simCard.getTarifa().getAvailableMB().toString(), simCard.getTarifa().getAvailableMin().toString(), simCard.getTarifa().getAvailableSMS().toString(), simCard.getTarifa().isPermiteRoaming(), "Cambiar", String.format("%.2f", simCard.getTarifa().getPrecio()));
                                userTarifaCardElement.setButtonClickListener(()->{
                                    UI.getCurrent().navigate(TarifasView.class);});
                                userTarifaCardElement.getStyle().set("width", "auto");

                                contentLayout.add(userTarifaCardElement);

                                simCardInfoLayout.add(contentLayout);

                                simCardInfoLayout.setVisible(true);
                            });
                            button.setIcon(new Icon(VaadinIcon.EYE));
                        })).setHeader("");

                add(activateSimCardConTarifaBasica);
                activateSimCardConTarifaBasica.setVisible(false);
                activateSimCardConTarifaBasica.addClickListener(buttonClickEvent -> {
                    // getting user first contract
                    Contrato clientContract = contratoService.getContratosByCliente(cliente).get(0);
                    Optional<Tarifa> basicTarifa = servicioTarifa.getTarifaByNombre("Tarifa básica");
                    if (basicTarifa.isPresent()){
                    basicTarifa.ifPresent(tarifa -> servicioSimCard.createSimCard(100000000 + random.nextInt(900000000), tarifa, clientContract));
                    UI.getCurrent().getPage().reload();
                    }
                    else {
                        Tarifa tarifa = new Tarifa();
                        tarifa.setNombre("Tarifa básica");
                        tarifa.setAvailable(true);
                        tarifa.setPrecio(10);
                        tarifa.setFibra(true);
                        tarifa.setFijo(false);
                        tarifa.setPermiteRoaming(false);
                        tarifa.setDescripcion("Tarifa básica para tarjeta sim");
                        tarifa.setAvailableMB(10000);
                        tarifa.setAvailableMin(100);
                        tarifa.setAvailableSMS(10);
                        servicioTarifa.guardarTarifa(tarifa);
                        Optional<Tarifa> newTarifaBasica = servicioTarifa.getTarifaByNombre("Tarifa básica");
                        newTarifaBasica.ifPresent(tarifa1 -> {
                            servicioSimCard.createSimCard(100000000 + random.nextInt(900000000), tarifa1, clientContract);
                        });
                        UI.getCurrent().getPage().reload();
                    }


                });


                select.addValueChangeListener(event -> {
                    // Update the text based on the selected value
                    simCardsContractH4.setText("Tarjetas relacionadas con contrato №" + event.getValue().getId() + ":");
                    gridSimCards.setItems(servicioSimCard.getSimCardsByContrato(event.getValue()));
                    simCardInfoLayout.setVisible(false);


                    if (servicioSimCard.getSimCardsByContrato(select.getValue()).isEmpty()){
                        simCardsVerticalLayout.remove(simCardsContractH4, gridSimCards);
                        simCardsVerticalLayout.setVisible(false);
                        noSimCardToContract.setVisible(true);
                        activateSimCardConTarifaBasica.setVisible(true);
                    }
                    else {
                        simCardsVerticalLayout.add(simCardsContractH4);
                        simCardsVerticalLayout.add(gridSimCards);
                        simCardsVerticalLayout.setVisible(true);
                        noSimCardToContract.setVisible(false);
                    }
                });

                select.setValue(userContracts.get(0));
            }
            else {
                add(new H4("Todavia no tienes el contrato"));
                add(new Paragraph("Hay opción de contratarse por 1 año con descuento de 0%"));
                Button buttonNewContract = new Button("Contratarse");
                buttonNewContract.addClickListener(buttonClickEvent -> {
                    Contrato newBasicContract = new Contrato();
                    newBasicContract.setUsuario(cliente);
                    newBasicContract.setDescuento(0);
                    newBasicContract.setFechaInicio(LocalDate.now());
                    newBasicContract.setFechaFin(LocalDate.now().plusYears(1));
                    newBasicContract.setNumerosBloqueados(null);
                    newBasicContract.setCompartirDatos(false);
                    contratoService.guardarContrato(newBasicContract);
                    Notification notification = new Notification("Contratado correctamente");
                    notification.setDuration(3000);
                    notification.setPosition(Notification.Position.TOP_CENTER);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    UI.getCurrent().getPage().reload();
                });
                add(buttonNewContract);
            }
        });
    }
}
