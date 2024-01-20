package es.uca.iw.frontend.vistas.formularios;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.*;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.backend.componentes.AuthenticatedUser;
import es.uca.iw.frontend.vistas.pantallas_iniciales.ProfileView;
import es.uca.iw.frontend.vistas.MainLayout;

@PageTitle("Login")
@Route(value = "login", layout = MainLayout.class)
@AnonymousAllowed
public class LoginView extends Composite<VerticalLayout> implements BeforeEnterObserver {
    private final AuthenticatedUser authenticatedUser;
    private final LoginForm login = new LoginForm();

    public LoginView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        login.setAction("login");

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();

        Image logoImage = new Image("icons/logo.png", "logo of the site");
        logoImage.setWidth("315px");
        login.setForgotPasswordButtonVisible(false);

        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Paragraph textLarge = new Paragraph();
        Button bregistrar = new Button();
        Button forgotPasswordButton = new Button("¿Has olvidado tu contraseña?", e -> navigateToRecuperarContraView());

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);

        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setAlignItems(Alignment.CENTER);

        layoutColumn2.add(logoImage);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        layoutColumn2.add(login);

        layoutRow2.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName("gap-m");
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);

        textLarge.setText("¿No tienes una cuenta todavía?");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");

        bregistrar.setText("Registrarse");
        bregistrar.addClickListener(event -> navigateToLoginView());
        bregistrar.setWidth("min-content");
        bregistrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layoutRow2.add(textLarge);
        layoutRow2.add(bregistrar);
        layoutRow2.add(forgotPasswordButton);
        layoutColumn2.add(new VerticalSpacer(), layoutRow2);
        getContent().add(layoutColumn2);
        UI.getCurrent().addBeforeLeaveListener(this::beforeLeave);
    }

    public void beforeLeave(BeforeLeaveEvent event){
        if (authenticatedUser.get().isPresent()) {
            UI.getCurrent().navigate(ProfileView.class);
        }
    }

    private void navigateToLoginView() {
        UI.getCurrent().navigate(RegistroView.class);
    }

    private void navigateToRecuperarContraView() {
        UI.getCurrent().navigate(RecuperarContraView.class);
    }

    public static class VerticalSpacer extends Div {
        public VerticalSpacer() {
            setHeight("1em");
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            UI.getCurrent().navigate(ProfileView.class);
        }

        if(event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}