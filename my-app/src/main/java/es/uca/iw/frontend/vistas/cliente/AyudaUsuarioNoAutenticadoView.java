package es.uca.iw.frontend.vistas.cliente;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.frontend.vistas.MainLayout;

@PageTitle("Cádiz Móvil")
@Route(value = "ayuda_usuario", layout = MainLayout.class)
@AnonymousAllowed
public class AyudaUsuarioNoAutenticadoView extends VerticalLayout {

    public AyudaUsuarioNoAutenticadoView() {
        H1 titulopag = new H1("Ayuda al usuario 🤗");
        Div mensaje = new Div();
        mensaje.addClassName("mensaje-ayuda");

        // Añadir un título H2 a la carta
        H3 titulo = new H3("Estoy registrado, pero no puedo iniciar sesión. ¿Qué hago?");

        // Añadir un texto a la carta
        Paragraph texto1 = new Paragraph("Si deseas volver a iniciar sesión en su cuenta y sin embargo, no se acuerda de su contraseña, no se preocupe, en este breve tutorial, le enseñaremos como recuperarla \uD83D\uDE01.");

        Paragraph texto2 = new Paragraph("1. En la página de inicio de sesión, haga click en el botón \"¿Has olvidado tu contraseña?\".");
        Paragraph texto3 = new Paragraph("2. Introduzca su correo electrónico y haga click en el botón \"Enviar\".");
        Paragraph texto4 = new Paragraph("3. Recibirá un correo electrónico con un código de verificación único, no lo comparta con nadie.");
        Paragraph texto5 = new Paragraph("4. En la página que usted se encuentre ahora mismo, introduzca su nueva contraseña y el código que le hemos enviado anteriomente. Tras esto, podrás volver a disfrutar de nuestros servicios sin problemas. \uD83D\uDE0E");

        Image img = new Image("images/captura-codigo-verificacion.jpg", "foto código");

        mensaje.add(titulo, new Hr(), texto1, texto2, texto3, texto4, img, texto5);

        // Añadir la carta centrada a la página
        add(titulopag, mensaje);
        setAlignItems(Alignment.CENTER);
    }
}

