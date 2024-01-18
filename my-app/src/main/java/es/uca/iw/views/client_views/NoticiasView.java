package es.uca.iw.views.client_views;

import com.github.appreciated.card.Card;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.AuthenticatedUser;
import es.uca.iw.custom_components.CustomNewsComponent;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.Collection;

@Route(value = "noticias", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Noticias y promociónes")

public class NoticiasView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;

    public NoticiasView(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;


        FlexLayout contentLayout = new FlexLayout();
        contentLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        contentLayout.getStyle().set("flex-wrap", "wrap");
        contentLayout.getStyle().set("display", "flex");
        contentLayout.getStyle().set("width", "100%");
        contentLayout.getStyle().set("justify-content", "space-around");

        CustomNewsComponent customNewsComponent = new CustomNewsComponent("News title", "Lorem ipsum asit dolor amet its ater amet boom dices lan brecht", "images/inicio-pagina.jpg");
        CustomNewsComponent customNewsComponent2 = new CustomNewsComponent("News title", "Lorem ipsum asit dolor amet its ater amet boom dices lan brecht", "images/inicio-pagina.jpg");

        contentLayout.add(customNewsComponent, customNewsComponent2);
        add(contentLayout);
    }
}