package es.uca.iw.frontend.vistas.pantallas_iniciales;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.frontend.vistas.MainLayout;
import es.uca.iw.frontend.vistas.formularios.RegistroView;

@PageTitle("Cádiz Móvil")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
@RouteAlias(value = "inicio", layout = MainLayout.class)
public class HelloWorldView extends VerticalLayout {

    public HelloWorldView() {
        H1 titulo = new H1("¡Bienvenidos a Cádiz Móvil!");
        Image img = new Image("images/inicio-pagina.jpg", "foto inicial");
        Button inisesion = new Button("Iniciar sesión");
        inisesion.addClickListener(event -> navigateToProfileView());
        Button registro = new Button("Registrarse");
        registro.addClickListener(event -> navigateToRegisterView());
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        img.addClassName("img");
        inisesion.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registro.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        setMargin(true);

        add(titulo, img, new HorizontalLayout(inisesion, registro));
    }

    private void navigateToProfileView(){
        UI.getCurrent().navigate(ProfileView.class);
    }

    private void navigateToRegisterView() {
        UI.getCurrent().navigate(RegistroView.class);
    }

}
