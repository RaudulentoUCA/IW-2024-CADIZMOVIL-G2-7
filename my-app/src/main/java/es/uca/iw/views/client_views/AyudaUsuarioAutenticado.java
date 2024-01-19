package es.uca.iw.views.client_views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.cliente.Role;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Cádiz Móvil")
@Route(value = "ayudaUsuario", layout = MainLayout.class)
@RolesAllowed("USER")
public class AyudaUsuarioAutenticado extends VerticalLayout {

        public AyudaUsuarioAutenticado() {
            H1 titulopag = new H1("Ayuda al usuario 🤗");
            Div cambio_tarifa = new Div();
            cambio_tarifa.addClassName("mensaje-ayuda");

            H3 titulo = new H3("Quiero cambiar de tarifa. ¿Cómo lo hago?");
            Paragraph texto1 = new Paragraph("Si desea cambiar de tarifa, en este breve tutorial, le enseñaremos como hacerlo \uD83D\uDE01.");
            Paragraph texto2 = new Paragraph("1. Será necesario, obviamente, que tengamos una tarjeta SIM activada con alguno de los contratos que tenemos en vigor. Si no es así, es tan fácil como dirigirnos a nuestra sección General, y activar una tarjeta SIM con tarifa básica.");
            Paragraph texto3 = new Paragraph("2. Ahora, desde esta página, podremos acceder a modificarlo directamente, si pulsamos sobre el símbolo del ojo de nuestra tarjeta SIM contratada que queramos moficiar la tarifa, podremos desplegar tantos los gastos que hemos realizado, como la tarifa asociada a la tarjeta SIM. Simplemente, pulsamos sobre el botón \"Cambiar\".");
            Paragraph texto4 = new Paragraph("3. Ahora, podremos elegir entre las tarifas que tenemos disponibles, y pulsar sobre el botón \"Cambiar\".");
            Image img = new Image("images/captura-cambio-tarifa.jpg", "foto en general");
            Paragraph texto5 = new Paragraph("3.1. O en su defecto, dirigirnos a la sección de \"Tarifas\", que es donde nos redirigiría la anterior acción, y seleccionar la SIM a la cual queremos cambiar de tarifa.");
            Paragraph texto6 = new Paragraph("4. Ahora, podremos elegir entre las tarifas que tenemos disponibles como se muestran abajo, y pulsar sobre el botón \"Elegir\" de aquella que estés interesada.");


            cambio_tarifa.add(titulo, new Hr(), texto1, texto2, texto3, texto4, img, texto5, texto6);
            add(titulopag, cambio_tarifa);
        }
}
