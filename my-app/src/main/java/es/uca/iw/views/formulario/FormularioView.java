package es.uca.iw.views.formulario;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import es.uca.iw.views.MainLayout;

@PageTitle("Formulario")
@Route(value = "formulario", layout = MainLayout.class)
@Uses(Icon.class)
public class FormularioView extends Composite<VerticalLayout> {


    private final TextField nombre;
    private final TextField apellidos;
    private final DatePicker fechaNacimiento;
    private final TextField movil;
    private final EmailField emailField;
    private final PasswordField contra;
    private final PasswordField repcontra;


    public FormularioView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
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

        emailField = new EmailField();
        emailField.setRequiredIndicatorVisible(true);

        HorizontalLayout layoutRow = new HorizontalLayout();
        contra = new PasswordField();
        contra.setRequiredIndicatorVisible(true);
        repcontra = new PasswordField();
        repcontra.setRequiredIndicatorVisible(true);

        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button baceptar = new Button();
        Button bcancelar = new Button();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Información Personal");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        nombre.setLabel("Nombre");
        apellidos.setLabel("Apellidos");
        fechaNacimiento.setLabel("Nacimiento");
        movil.setLabel("Móvil");
        emailField.setLabel("Email");
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
        baceptar.setText("Save");
        baceptar.setWidth("min-content");
        baceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        baceptar.addClickListener(e -> onRegisterButtonClick());

        bcancelar.setText("Cancel");
        bcancelar.setWidth("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(nombre);
        formLayout2Col.add(apellidos);
        formLayout2Col.add(fechaNacimiento);
        formLayout2Col.add(movil);
        formLayout2Col.add(emailField);
        layoutColumn2.add(layoutRow);
        layoutRow.add(contra);
        layoutRow.add(repcontra);

        layoutColumn2.add(layoutRow2);
        layoutRow2.add(baceptar);
        layoutRow2.add(bcancelar);
    }

    private void onRegisterButtonClick() {
        if (contra.getValue().equals(repcontra.getValue())) {
            Notification.show("Las contraseñas son iguales");
        } else {
            Notification.show("Revisa los datos incluidos");
        }
    }
}