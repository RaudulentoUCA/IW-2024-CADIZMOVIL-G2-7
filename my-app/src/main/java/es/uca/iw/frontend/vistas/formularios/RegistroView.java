package es.uca.iw.frontend.vistas.formularios;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.servicios.ServicioUsuario;
import es.uca.iw.frontend.vistas.MainLayout;
import com.vaadin.flow.component.dialog.Dialog;


@PageTitle("Formulario")
@Route(value = "formulario", layout = MainLayout.class)
@Uses(Icon.class)
@AnonymousAllowed
public class RegistroView extends Composite<VerticalLayout> {


    private final TextField nombre;
    private final TextField apellidos;
    private final DatePicker fechaNacimiento;
    private final TextField movil;
    private final EmailField email;
    private final TextField dni;
    private final PasswordField contra;
    private final PasswordField repcontra;
    private final BeanValidationBinder<Cliente> binder;
    private boolean usuarioDioRetroceso = false;
    private final ServicioUsuario servicios;

    private boolean iniciosesion = false;


    public RegistroView(ServicioUsuario servicios) {
        this.servicios = servicios;
        VerticalLayout layoutColumn2 = new VerticalLayout();
        layoutColumn2.setAlignItems(FlexComponent.Alignment.CENTER);

        Image logoImage = new Image("icons/logo.png", "logo of the site");
        logoImage.setWidth("315px");

        layoutColumn2.add(logoImage);

        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        nombre = new TextField();
        nombre.setRequiredIndicatorVisible(true);
        nombre.setPlaceholder("Ejemplo: Juan");

        apellidos = new TextField();
        apellidos.setRequiredIndicatorVisible(true);
        apellidos.setPlaceholder("Ejemplo: Pérez Gómez");

        fechaNacimiento = new DatePicker();
        fechaNacimiento.setRequiredIndicatorVisible(true);
        fechaNacimiento.setPlaceholder("Selecciona tu fecha de nacimiento aquí");

        movil = new TextField();
        movil.setRequiredIndicatorVisible(false);
        movil.setPlaceholder("Ejemplo: 602986754");

        email = new EmailField();
        email.setRequiredIndicatorVisible(true);
        email.setPlaceholder("Ejemplo: tu_correo@example.com");

        dni = new TextField();
        dni.setRequiredIndicatorVisible(true);
        dni.setPlaceholder("Ejemplo: 12345678A");

        HorizontalLayout layoutRow = new HorizontalLayout();
        contra = new PasswordField();
        contra.setRequiredIndicatorVisible(true);
        contra.setPlaceholder("Mínimo 4 caracteres");
        repcontra = new PasswordField();
        repcontra.setRequiredIndicatorVisible(true);
        repcontra.setPlaceholder("Mínimo 4 caracteres");

        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button baceptar = new Button();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Información Personal");
        formLayout2Col.setWidth("100%");
        nombre.setLabel("Nombre");
        apellidos.setLabel("Apellidos");
        fechaNacimiento.setLabel("Nacimiento");
        movil.setLabel("Móvil");
        email.setLabel("Email");
        dni.setLabel("DNI");
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        contra.setLabel("Contraseña");
        contra.setWidth("min-content");
        repcontra.setLabel("Repetir Contraseña");
        repcontra.setWidth("min-content");
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        baceptar.setText("Registrarse");
        baceptar.setWidth("min-content");
        baceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        baceptar.addClickListener(e -> onRegisterButtonClick());

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nombre);
        formLayout2Col.add(apellidos);
        formLayout2Col.add(fechaNacimiento);
        formLayout2Col.add(movil);
        formLayout2Col.add(email);
        formLayout2Col.add(dni);
        layoutColumn2.add(layoutRow);
        layoutRow.add(contra);
        layoutRow.add(repcontra);

        layoutColumn2.add(layoutRow2);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutRow2.add(baceptar);

        HorizontalLayout hasAccountLayout = new HorizontalLayout();
        hasAccountLayout.setWidthFull();
        hasAccountLayout.addClassName(Gap.MEDIUM);
        hasAccountLayout.getStyle().set("flex-grow", "1");
        hasAccountLayout.setAlignItems(Alignment.CENTER);
        hasAccountLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Paragraph hasAccountParagraph = new Paragraph();

        hasAccountParagraph.setText("¿Tienes la cuenta ya?");
        hasAccountParagraph.setWidth("100%");
        hasAccountParagraph.getStyle().set("font-size", "var(--lumo-font-size-xl)");

        Button loginButton = new Button();

        loginButton.setText("Iniciar sesion");
        loginButton.addClickListener(event -> navigateToLoginView());
        loginButton.setWidth("min-content");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        hasAccountLayout.add(hasAccountParagraph);
        hasAccountLayout.add(loginButton);

        layoutColumn2.add(hasAccountLayout);

        binder = new BeanValidationBinder<>(Cliente.class);
        binder.bindInstanceFields(this);

        // TODO Implement validation of all fields according to the requisites

        binder.forField(email)
                .withValidator(new EmailValidator("Ingrese una dirección de correo electrónico válida"))
                .bind(Cliente::getEmail, Cliente::setEmail);

        binder.forField(fechaNacimiento)
                .bind(Cliente::getFechaDeNacimiento, Cliente::setFechaDeNacimiento);

        binder.forField(movil).
                bind(Cliente::getNumeroContacto, Cliente::setNumeroContacto);

        binder.forField(contra).
                bind(Cliente::getPassword, Cliente::setPassword);

        email.addValueChangeListener(
                event -> binder.validate());

        Button infoButton = new Button("Por qué necesitamos tus datos");
        infoButton.addClickListener(e -> showInfoDialog());
        layoutColumn2.add(infoButton);

        binder.setBean(new Cliente());
        UI.getCurrent().addBeforeLeaveListener(this::beforeLeave);
    }

    public void beforeLeave(BeforeLeaveEvent event) {
        if (!usuarioDioRetroceso) {
            // El usuario intenta retroceder, mostrar mensaje de advertencia
            Div message = new Div();
            message.setText("¿Estás seguro de que deseas abandonar el formulario? Se perderán los datos no guardados.");

            Button backButton = new Button("Salir", e -> {
                usuarioDioRetroceso = true;
                if(!iniciosesion)
                    UI.getCurrent().navigate("");
                else
                    UI.getCurrent().navigate(LoginView.class);
            });

            Button stayButton = new Button("Permanecer", e -> {
                usuarioDioRetroceso = false;
                iniciosesion = false;
            });

            // Mostrar mensaje y botones
            Notification notification = new Notification(message, backButton, stayButton);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.setDuration(0);
            notification.open();

            backButton.addClickListener(closeEvent -> notification.close());
            stayButton.addClickListener(closeEvent -> notification.close());

            event.postpone();
        }
    }

    private void navigateToLoginView() {
        iniciosesion = true;
        UI.getCurrent().navigate(LoginView.class);
    }

    private void onRegisterButtonClick() {
        if (binder.validate().isOk() && contra.getValue().equals(repcontra.getValue())) {
            String condiciones = confirmarCampos();
            if (servicios.registrarCliente(binder.getBean()) && condiciones.equals("bien")) {
                binder.setBean(new Cliente());
                Notification.show("Te has registrado correctamente");
                usuarioDioRetroceso = true;
                navigateToLoginView();
            } else {
                Notification.show("Ha ocurrido un fallo inesperado:" + condiciones);
            }
        } else {
            Notification.show("Revisa los datos incluidos");
        }
    }

    private String confirmarCampos() {
        StringBuilder devolver = new StringBuilder();

        // Validar longitud del DNI y contraseña
        if (dni.getValue().length() != 9 && contra.getValue().length() < 4) {
            devolver.append(" \n DNI debe tener exactamente 9 caracteres y contraseña debe tener al menos 4 caracteres.");
        }
        else{
            if (contra.getValue().length() < 4) {
                devolver.append(" \n Contraseña debe tener al menos 4 caracteres.");
            }

            if (dni.getValue().length() != 9) {
                devolver.append(" \n DNI debe tener exactamente 9 caracteres.");
            }
        }

        return devolver.isEmpty() ? "bien" : devolver.toString();
    }

    private void showInfoDialog() {
        // Crear texto
        Dialog infoDialog = new Dialog();

        Paragraph infoParagraph1 = new Paragraph("Necesitamos sus datos para proporcionarle " +
                "una experiencia personalizada y garantizar la seguridad " +
                "de su cuenta. Además, con ellos podemos identificarle y comunicarnos con usted de forma sencilla.");

        Paragraph infoParagraph2 = new Paragraph("No compartiremos su información con terceros.");

        // Crear botón para cerrar el diálogo
        Button closeButton = new Button("Cerrar", event -> infoDialog.close());

        infoDialog.add(infoParagraph1, infoParagraph2, closeButton);

        // Mostrar el diálogo
        infoDialog.open();
    }
}