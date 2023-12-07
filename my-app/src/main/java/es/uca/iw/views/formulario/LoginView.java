//package es.uca.iw.views.formulario;
//
//import com.vaadin.flow.component.Composite;
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.router.BeforeEnterEvent;
//import com.vaadin.flow.router.BeforeEnterObserver;
//
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.html.H3;
//import com.vaadin.flow.component.html.Image;
//import com.vaadin.flow.component.html.Paragraph;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
//import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.EmailField;
//import com.vaadin.flow.component.textfield.PasswordField;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.router.internal.RouteUtil;
//import com.vaadin.flow.server.VaadinService;
//import com.vaadin.flow.server.auth.AnonymousAllowed;
//import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
//import es.uca.iw.AuthenticatedUser;
//import es.uca.iw.views.MainLayout;
//import es.uca.iw.views.helloworld.HelloWorldView;
//import es.uca.iw.views.profile.ProfileView;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//
//@PageTitle("Login")
//@Route(value = "login", layout = MainLayout.class)
//@AnonymousAllowed
//
//
//public class LoginView extends Composite<VerticalLayout> implements BeforeEnterObserver {
//    private final AuthenticatedUser authenticatedUser;
//
//    public LoginView(AuthenticatedUser authenticatedUser) {
//
//        this.authenticatedUser = authenticatedUser;
//
//        VerticalLayout layoutColumn2 = new VerticalLayout();
//        H3 h3 = new H3();
//        FormLayout formLayout2Col = new FormLayout();
//        EmailField emailField = new EmailField();
//        emailField.setLabel("Email");
//        emailField.setRequiredIndicatorVisible(true);
//
//        PasswordField contra = new PasswordField();
//        contra.setLabel("Contraseña");
//        contra.setRequiredIndicatorVisible(true);
//
//        Image logoImage = new Image("icons/logo.png", "logo of the site");
//        logoImage.setWidth("315px");
//
//        HorizontalLayout layoutRow = new HorizontalLayout();
//        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
//        Button baceptar = new Button();
//        Button bcancelar = new Button();
//        HorizontalLayout layoutRow2 = new HorizontalLayout();
//        Paragraph textLarge = new Paragraph();
//        Button bregistrar = new Button();
//
//        getContent().setWidth("100%");
//        getContent().getStyle().set("flex-grow", "1");
//        getContent().setJustifyContentMode(JustifyContentMode.START);
//        getContent().setAlignItems(Alignment.CENTER);
//
//        layoutColumn2.setWidth("100%");
//        layoutColumn2.setMaxWidth("800px");
//        layoutColumn2.setHeight("min-content");
//        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
//        layoutColumn2.setAlignItems(Alignment.CENTER);
//
//        logoImage.getStyle().set("margin-bottom", "20px"); // Add margin to the bottom of the logo
//        layoutColumn2.add(logoImage);
//
//        h3.setText("Iniciar Sesión");
//
//        layoutColumn2.add(h3);
//        layoutColumn2.add(formLayout2Col);
//        formLayout2Col.add(emailField);
//        formLayout2Col.add(contra);
//
//        layoutRow.addClassName(Gap.MEDIUM);
//        layoutRow.setWidth("100%");
//        layoutRow.getStyle().set("flex-grow", "1");
//        baceptar.setText("Iniciar");
//        baceptar.setWidth("min-content");
//        baceptar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        bcancelar.setText("Cancelar");
//        bcancelar.setWidth("min-content");
//        bcancelar.addClickListener(event -> navigateToHelloWorldView());
//
//        layoutRow.add(baceptar);
//        layoutRow.add(bcancelar);
//
//        layoutColumn2.add(layoutRow);
//
//        layoutRow2.setWidthFull();
//        layoutColumn2.setFlexGrow(1.0, layoutRow2);
//        layoutRow2.addClassName(Gap.MEDIUM);
//        layoutRow2.setWidth("100%");
//        layoutRow2.getStyle().set("flex-grow", "1");
//        layoutRow2.setAlignItems(Alignment.CENTER);
//        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);
//
//        textLarge.setText("¿No tienes una cuenta todavía?");
//        textLarge.setWidth("100%");
//        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");
//
//        bregistrar.setText("Registrarse");
//        bregistrar.addClickListener(event -> navigateToLoginView());
//        bregistrar.setWidth("min-content");
//        bregistrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//
//        layoutRow2.add(textLarge);
//        layoutRow2.add(bregistrar);
//
//        layoutColumn2.add(new VerticalSpacer(), layoutRow2);
//
//        getContent().add(layoutColumn2);
//
//        baceptar.addClickListener(event -> {
////            setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));
//        });
//    }
//
//    private void navigateToLoginView() {
//        UI.getCurrent().navigate(FormularioView.class);
//    }
//
//
//    private void navigateToHelloWorldView() {
//        UI.getCurrent().navigate(HelloWorldView.class);
//    }
//
//    public static class VerticalSpacer extends Div {
//        public VerticalSpacer() {
//            setHeight("1em");
//        }
//    }
//
//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        if (authenticatedUser.get().isPresent()) {
//            event.forwardTo("profile");
//        }
//    }
//}
