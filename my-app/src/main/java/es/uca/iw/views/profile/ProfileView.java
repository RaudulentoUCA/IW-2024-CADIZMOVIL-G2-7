package es.uca.iw.views.profile;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@PageTitle("Profile")
@Route(value = "profile", layout = MainLayout.class)
@PermitAll
public class ProfileView extends VerticalLayout {
    public ProfileView() {
        H1 h1 = new H1();
        setWidth("100%");
        getStyle().set("flex-grow", "1");

        h1.setText("¡Hola!, + {userName}");
        h1.setWidth("max-content");
        add(h1);
    }
}