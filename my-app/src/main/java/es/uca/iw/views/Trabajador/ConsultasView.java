package es.uca.iw.views.Trabajador;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.atencion_cliente.Consulta;
import es.uca.iw.atencion_cliente.ServicioConsulta;
import es.uca.iw.views.MainLayout;
import es.uca.iw.views.profile.ProfileView;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@PageTitle("Cádiz Móvil")
@Route(value = "consultas", layout = MainLayout.class)
@RolesAllowed("ATTENTION")
public class ConsultasView extends VerticalLayout {
    private final ServicioConsulta servicioConsulta;

    public ConsultasView(ServicioConsulta servicioConsulta) {
        this.servicioConsulta = servicioConsulta;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        H1 titulo = new H1("Consultas pendientes de clientes");
        add(titulo);

        List<Consulta> consultas = servicioConsulta.obtenerTodasLasConsultas();

        if (consultas.isEmpty()) {
            add(new Paragraph("No hay consultas pendientes."));
        } else {
            for (Consulta consulta : consultas) {
                String correoCliente = consulta.getCliente().getEmail();
                Div consultaDiv = new Div();
                consultaDiv.getStyle().set("border", "1px solid #ccc");
                consultaDiv.getStyle().set("padding", "10px");
                consultaDiv.setWidth("1000px");

                consultaDiv.add(
                        new Paragraph("ID: " + consulta.getId()),
                        new Hr(),
                        new H5("Remitente: " + correoCliente),
                        new Hr(),
                        new H5("Asunto: " + consulta.getAsunto()),
                        new Hr(),
                        new Paragraph(consulta.getCuerpo())
                );
                add(consultaDiv);
            }

            Button responder = new Button("Responder consultas");
            responder.addClickListener(event -> UI.getCurrent().navigate(ResponderConsultaView.class));
            Button cerrar = new Button("Cerrar consultas");
            cerrar.addClickListener(event -> UI.getCurrent().navigate(EliminarConsultaView.class));
            add(new HorizontalLayout(responder, cerrar));
        }

        Button volver = new Button("Volver a tu página principal");
        volver.addClickListener(event -> UI.getCurrent().navigate(ProfileView.class));
        add(volver);
    }
}







