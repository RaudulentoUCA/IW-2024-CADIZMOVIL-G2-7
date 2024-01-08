package es.uca.iw.custom_components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
import java.util.Random;

public class CustomCardElement extends VerticalLayout {
    private static final List<String> EMOJIS = List.of("⭐", "\uD83D\uDE80", "\uD83D\uDEA9", "⌚", "☕", "⚡","\uD83C\uDF08", "\uD83C\uDF1F", "\uD83C\uDF6D", "\uD83C\uDF81", "\uD83C\uDF89", "\uD83D\uDCAB");


    public CustomCardElement(String title, String internet, String calls, String sms, boolean isRoamingIncluded, String buttonText) {
        Div titleDiv = new Div();
        titleDiv.setHeight("auto");
        titleDiv.setWidth("380px");
        titleDiv.getStyle().set("border-radius", "8px");
        titleDiv.getStyle().set("box-shadow", "rgba(0, 0, 0, 0.1) 0px 10px 18px");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Span emojiSpan = new Span(getRandomEmoji());
        emojiSpan.getStyle().set("font-size", "40px");
        emojiSpan.getStyle().set("margin-right", "25px");


        Paragraph titleParagraph = new Paragraph(title);
        titleParagraph.getStyle().set("font-weight", "bold");
        titleParagraph.getStyle().set("font-size", "24px");
        titleParagraph.getStyle().set("padding", "10px 5px 0px 20px");
        titleParagraph.getStyle().set("margin", "0px");
        horizontalLayout.add(titleParagraph);
        horizontalLayout.add(emojiSpan);

        titleDiv.add(horizontalLayout);

        Paragraph callsParagraph = new Paragraph("Lllamadas:");
        callsParagraph.getStyle().set("padding", "0px 0px 0px 20px");
        callsParagraph.getStyle().set("margin", "0px");
        callsParagraph.getStyle().set("font-size", "17px");
        titleDiv.add(callsParagraph);

        Paragraph callsValueParagraph = new Paragraph(calls + " min");
        callsValueParagraph.getStyle().set("padding", "0px 0px 5px 20px");
        callsValueParagraph.getStyle().set("margin", "0px");
        callsValueParagraph.getStyle().set("font-weight", "800");
        callsValueParagraph.getStyle().set("color", "#5AC2F7");
        titleDiv.add(callsValueParagraph);

        Paragraph internetParagraph = new Paragraph("Internet:");
        internetParagraph.getStyle().set("padding", "0px 0px 0px 20px");
        internetParagraph.getStyle().set("margin", "0px");
        internetParagraph.getStyle().set("font-size", "17px");
        titleDiv.add(internetParagraph);

        Paragraph internetValueParagraph = new Paragraph(internet + " MB");
        internetValueParagraph.getStyle().set("padding", "0px 0px 5px 20px");
        internetValueParagraph.getStyle().set("margin", "0px");
        internetValueParagraph.getStyle().set("font-weight", "800");
        internetValueParagraph.getStyle().set("color", "#3ABB66");
        titleDiv.add(internetValueParagraph);

        Paragraph smsParagraph = new Paragraph("Mensajes:");
        smsParagraph.getStyle().set("padding", "0px 0px 0px 20px");
        smsParagraph.getStyle().set("margin", "0px");
        smsParagraph.getStyle().set("font-size", "17px");
        titleDiv.add(smsParagraph);

        Paragraph smsValueParagraph = new Paragraph(sms + " SMS");
        smsValueParagraph.getStyle().set("padding", "0px 0px 5px 20px");
        smsValueParagraph.getStyle().set("margin", "0px");
        smsValueParagraph.getStyle().set("font-weight", "800");
        smsValueParagraph.getStyle().set("color", "#B8C277");
        titleDiv.add(smsValueParagraph);

        Paragraph roamingParagraph = new Paragraph("Roaming:");
        roamingParagraph.getStyle().set("padding", "0px 0px 0px 20px");
        roamingParagraph.getStyle().set("margin", "0px");
        roamingParagraph.getStyle().set("font-size", "17px");
        titleDiv.add(roamingParagraph);

        if (isRoamingIncluded){
            Paragraph roamingValueParagraph = new Paragraph("✅");
            roamingValueParagraph.getStyle().set("padding", "0px 0px 5px 20px");
            roamingValueParagraph.getStyle().set("margin", "0px");
            roamingValueParagraph.getStyle().set("font-size", "20px");
            titleDiv.add(roamingValueParagraph);
        }
        else {
            Paragraph roamingValueParagraph = new Paragraph("❌");
            roamingValueParagraph.getStyle().set("padding", "0px 0px 5px 20px");
            roamingValueParagraph.getStyle().set("margin", "0px");
            roamingValueParagraph.getStyle().set("font-size", "20px");
            titleDiv.add(roamingValueParagraph);
        }


        Button changeButton = new Button(buttonText);
        changeButton.getStyle().set("margin-right", "15px");
        changeButton.getStyle().set("margin-bottom", "10px");
        changeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        changeButton.getStyle().set("float", "right");
        titleDiv.add(changeButton);

        add(titleDiv);

    }

    private String getRandomEmoji() {
        Random random = new Random();
        return EMOJIS.get(random.nextInt(EMOJIS.size()));
    }
}