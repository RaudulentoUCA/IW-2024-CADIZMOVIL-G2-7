//package es.uca.iw.views.formulario;
//
//
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.login.LoginI18n;
//import com.vaadin.flow.component.login.LoginOverlay;
//import com.vaadin.flow.router.BeforeEnterEvent;
//import com.vaadin.flow.router.BeforeEnterObserver;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.router.internal.RouteUtil;
//import com.vaadin.flow.server.VaadinService;
//import com.vaadin.flow.server.auth.AnonymousAllowed;
//import es.uca.iw.AuthenticatedUser;
//import es.uca.iw.views.profile.ProfileView;
//
//@AnonymousAllowed
//@PageTitle("Login")
//@Route(value = "login")
//public class VaadinLoginComponent extends LoginOverlay implements BeforeEnterObserver {
//
//    private final AuthenticatedUser authenticatedUser;
//
//    public VaadinLoginComponent(AuthenticatedUser authenticatedUser) {
//        this.authenticatedUser = authenticatedUser;
//        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));
//
//        LoginI18n i18n = LoginI18n.createDefault();
//        i18n.setHeader(new LoginI18n.Header());
//        i18n.getHeader().setTitle("My App");
//        i18n.getHeader().setDescription("Login using admin/admin or with any other credentials");
//        i18n.setAdditionalInformation(null);
//        setI18n(i18n);
//
//        setForgotPasswordButtonVisible(false);
//        setOpened(true);
//
////        addLoginListener(loginEvent -> UI.getCurrent().navigate(ProfileView.class));
//    }
//
//
//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        if (authenticatedUser.get().isPresent()) {
//            setOpened(false);
//            event.forwardTo(ProfileView.class);
//        }
//
//        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
//    }
//}
