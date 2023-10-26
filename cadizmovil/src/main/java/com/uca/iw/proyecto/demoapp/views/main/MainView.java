package com.example.application.views.main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "")
public class MainView extends HorizontalLayout {

    private TextField name;
    private TextField ages;
    private Button sayHello;

    public MainView() {
        name = new TextField("Your name");
        name.setRequired(true);
        ages = new TextField("Your age");
        sayHello = new Button("Say hello");

        sayHello.addThemeVariants(ButtonVariant.LUMO_ERROR);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue() + " de " + ages.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, ages, sayHello);    //esto añade las cosas que esten cargadas en el layout a pantalla
    }

}
