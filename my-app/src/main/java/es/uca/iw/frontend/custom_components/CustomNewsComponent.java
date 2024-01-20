package es.uca.iw.frontend.custom_components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

public class CustomNewsComponent extends VerticalLayout {

    public CustomNewsComponent(String title, String description, StreamResource imageResoure) {

        Div newsDiv = new Div();
        newsDiv.setHeight("auto");
        newsDiv.setWidth("auto");
        newsDiv.getStyle().set("border-radius", "8px");
        newsDiv.getStyle().set("padding", "8px");
        newsDiv.getStyle().set("box-shadow", "rgba(0, 0, 0, 0.1) 0px 10px 18px");

        // Image Container
        Image image = new Image(imageResoure, imageResoure.getName());
        image.setHeight("200px");
        image.setWidth("-webkit-fill-available");
        image.getStyle().set("border-radius", "5px");
        newsDiv.add(image);

        // Bold Title Text
        if (title.length() > 65){
            title = title.substring(0, 151);
            title += "...";
        }
        Paragraph titleParagraph = new Paragraph(title);
        titleParagraph.getElement().getStyle().set("font-weight", "bold");
        titleParagraph.getElement().getStyle().set("font-size", "1.5em");
        titleParagraph.setMaxWidth("300px");
        newsDiv.add(titleParagraph);

        // Smaller Non-Bold Description
        if (description.length() > 150){
            description = description.substring(0, 151);
            description += "...";
        }
        Paragraph descriptionParagraph = new Paragraph(description);
        descriptionParagraph.getElement().getStyle().set("font-size", "1em");
        descriptionParagraph.setMaxWidth("300px");
        newsDiv.add(descriptionParagraph);

        add(newsDiv);
    }
}