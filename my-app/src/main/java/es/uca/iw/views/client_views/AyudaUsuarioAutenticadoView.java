package es.uca.iw.views.client_views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Cádiz Móvil")
@Route(value = "ayudaUsuario", layout = MainLayout.class)
@RolesAllowed("USER")
public class AyudaUsuarioAutenticadoView extends VerticalLayout {

        public AyudaUsuarioAutenticadoView() {
            H1 titulopag = new H1("Ayuda al usuario 🤗");
            Div cambio_tarifa = new Div();
            Div consulta = new Div();
            cambio_tarifa.addClassName("mensaje-ayuda");
            consulta.addClassName("mensaje-ayuda");

            H3 tituloCambio = new H3("Quiero cambiar de tarifa. ¿Cómo lo hago?");
            Paragraph texto1T = new Paragraph("Si desea cambiar de tarifa, en este breve tutorial, le enseñaremos como hacerlo \uD83D\uDE01.");
            Paragraph texto2T = new Paragraph("1. Será necesario, obviamente, que tengamos una tarjeta SIM activada con alguno de los contratos que tenemos en vigor. Si no es así, es tan fácil como dirigirnos a nuestra sección General, y activar una tarjeta SIM con tarifa básica.");
            Paragraph texto3T = new Paragraph("2. Ahora, desde esta página, podremos acceder a modificarlo directamente, si pulsamos sobre el símbolo del ojo de nuestra tarjeta SIM contratada que queramos moficiar la tarifa, podremos desplegar tantos los gastos que hemos realizado, como la tarifa asociada a la tarjeta SIM. Simplemente, pulsamos sobre el botón \"Cambiar\".");
            Paragraph texto4T = new Paragraph("3. Ahora, podremos elegir entre las tarifas que tenemos disponibles, y pulsar sobre el botón \"Cambiar\".");
            Image img1T = new Image("images/captura-pantalla-general.jpg", "foto en general");
            Paragraph texto5T = new Paragraph("3.1. O en su defecto, dirigirnos a la sección de \"Tarifas\", que es donde nos redirigiría la anterior acción, y seleccionar la SIM a la cual queremos cambiar de tarifa.");
            Paragraph texto6T = new Paragraph("4. Ahora, podremos elegir entre las tarifas que tenemos disponibles como se muestran abajo, y pulsar sobre el botón \"Elegir\" de aquella que estés interesada. Listo, ya hemos cambiado de tarifa.");
            Image img2T = new Image("images/captura-pantalla-tarifas.jpg", "foto en pantalla tarifas");

            cambio_tarifa.add(tituloCambio, new Hr(), texto1T, texto2T, texto3T, texto4T, img1T, texto5T, texto6T, img2T);

            H3 tituloConsulta = new H3("Quiero realizar una consulta. ¿Cómo lo hago?");
            Paragraph texto1C = new Paragraph("Si desea realizar una consulta, en este breve tutorial, le enseñaremos como hacerlo \uD83D\uDE01.");
            Paragraph texto2C = new Paragraph("1. Nos dirigimos a la sección de \"Consultas/Reclamaciones\".");
            Paragraph texto3C = new Paragraph("2. Ahora, pulsamos sobre el botón \"Nueva Reclamación/Consulta\".");
            Paragraph texto4C = new Paragraph("3. Rellenamos el Asunto y Cuerpo de la consulta, y pulsamos sobre el botón \"Enviar\".");
            Paragraph texto5C = new Paragraph("4. Ahora en el desglose podremos ver que se encuentra la consulta recien realizada. ");
            Paragraph texto6C = new Paragraph("5. El siguiente paso, solo será esperar la respuesta por parte de nuestro departamento de Atención al Cliente, el cual, le aseguramos que en un plazo de 1 hora máximo le atenderán.");
            Paragraph texto7C = new Paragraph("Para consultar la respuesta, simplemente, tendremos que pulsar sobre el botón \"Ver Respuesta\" sobre la consulta realizada y se nos mostrará.");
            Image img1C = new Image("images/captura-consulta.jpg", "foto de consultas");
            Paragraph texto8C = new Paragraph("Le daremos un plazo de 24 horas por si desea objetar algo más, si no, daremos por cerrada la consulta. Eso sería todo, esperamos que le haya sido de ayuda \uD83D\uDE0A.");

            consulta.add(tituloConsulta, new Hr(), texto1C, texto2C, texto3C, texto4C, texto5C, texto6C, texto7C, img1C, texto8C);

            add(titulopag, new HorizontalLayout(cambio_tarifa, consulta));
            setJustifyContentMode(JustifyContentMode.CENTER);
            setAlignItems(Alignment.CENTER);

        }
}
