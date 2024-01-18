package es.uca.iw.custom_components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class CustomNewsComponent extends VerticalLayout {

    public CustomNewsComponent(String title, String description, String imageURL) {

        Div newsDiv = new Div();
        newsDiv.setHeight("auto");
        newsDiv.setWidth("auto");
        newsDiv.getStyle().set("border-radius", "8px");
        newsDiv.getStyle().set("padding", "8px");
        newsDiv.getStyle().set("box-shadow", "rgba(0, 0, 0, 0.1) 0px 10px 18px");

        // Image Container
        Image image = new Image(imageURL, imageURL);
        image.setHeight("200px");
        image.setWidth("-webkit-fill-available");
        image.getStyle().set("border-radius", "5px");
        newsDiv.add(image);

        // Bold Title Text
        Paragraph titleParagraph = new Paragraph(title);
        titleParagraph.getElement().getStyle().set("font-weight", "bold");
        titleParagraph.getElement().getStyle().set("font-size", "1.5em");
        newsDiv.add(titleParagraph);

        // Smaller Non-Bold Description
        Paragraph descriptionParagraph = new Paragraph(description);
        descriptionParagraph.getElement().getStyle().set("font-size", "1em");
        newsDiv.add(descriptionParagraph);

        add(newsDiv);
    }
}