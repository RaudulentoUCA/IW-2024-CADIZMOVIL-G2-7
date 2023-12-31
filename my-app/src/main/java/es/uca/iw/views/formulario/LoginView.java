package es.uca.iw.views.formulario;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.profile.ProfileView;

@PageTitle("Login")
@Route(value = "login", layout = MainLayout.class)
@AnonymousAllowed


public class LoginView extends Composite<VerticalLayout> implements BeforeEnterObserver {
    private final AuthenticatedUser authenticatedUser;
    private final LoginForm login = new LoginForm();

    public LoginView(AuthenticatedUser authenticatedUser) {

        this.authenticatedUser = authenticatedUser;

        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Log In");
        i18nForm.setUsername("Email");
        login.setI18n(i18n);

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();

        Image logoImage = new Image("icons/logo.png", "logo of the site");
        logoImage.setWidth("315px");
        login.setForgotPasswordButtonVisible(false);

        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Paragraph textLarge = new Paragraph();
        Button bregistrar = new Button();

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
        layoutRow2.addClassName(Gap.MEDIUM);
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
        layoutColumn2.add(new VerticalSpacer(), layoutRow2);
        getContent().add(layoutColumn2);
    }

    private void navigateToLoginView() {
        UI.getCurrent().navigate(FormularioView.class);
    }

    public static class VerticalSpacer extends Div {
        public VerticalSpacer() {
            setHeight("1em");
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            event.forwardTo(ProfileView.class);
        }

        if(event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }

    }
}
