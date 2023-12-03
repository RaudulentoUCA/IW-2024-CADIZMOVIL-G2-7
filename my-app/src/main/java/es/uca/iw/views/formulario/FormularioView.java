package es.uca.iw.views.formulario;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import es.uca.iw.Cliente.Cliente;
import es.uca.iw.Cliente.ServiciosCliente;
import es.uca.iw.views.MainLayout;

@PageTitle("Formulario")
@Route(value = "formulario", layout = MainLayout.class)
@Uses(Icon.class)
public class FormularioView extends Composite<VerticalLayout> {


    private final TextField nombre;
    private final TextField apellidos;
    private final DatePicker fechaNacimiento;
    private final TextField movil;
    private final EmailField email;
    private final TextField dni;
    private final PasswordField contra;
    private final PasswordField repcontra;
    private final BeanValidationBinder<Cliente> binder;
    private final ServiciosCliente servicios;


    public FormularioView(ServiciosCliente servicios) {
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

        apellidos = new TextField();
        apellidos.setRequiredIndicatorVisible(true);

        fechaNacimiento = new DatePicker();
        fechaNacimiento.setRequiredIndicatorVisible(true);

        movil = new TextField();
        movil.setRequiredIndicatorVisible(false);

        email = new EmailField();
        email.setRequiredIndicatorVisible(true);

        dni = new TextField();
        dni.setRequiredIndicatorVisible(true);

        HorizontalLayout layoutRow = new HorizontalLayout();
        contra = new PasswordField();
        contra.setRequiredIndicatorVisible(true);
        repcontra = new PasswordField();
        repcontra.setRequiredIndicatorVisible(true);

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

        binder.setBean(new Cliente());
    }

    private void navigateToLoginView() {
        UI.getCurrent().navigate(LoginView.class);
    }

    private void onRegisterButtonClick() {
        if (binder.validate().isOk() & contra.getValue().equals(repcontra.getValue())) {
            if (servicios.registrarCliente(binder.getBean())) {
                binder.setBean(new Cliente());
            } else {
                Notification.show("Ha ocurrido un fallo inesperado");

            }
        } else {
            Notification.show("Revisa los datos incluidos");
        }
    }
}