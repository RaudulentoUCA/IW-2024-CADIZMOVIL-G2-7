package es.uca.iw.frontend.vistas.cliente;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.componentes.AuthenticatedUser;
import es.uca.iw.backend.servicios.ServicioUsuario;
import es.uca.iw.frontend.vistas.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

@PageTitle("Confirmación de cuenta")
@Route(value = "confirmar-cuenta", layout = MainLayout.class)
@RolesAllowed("USER")
public class ConfirmarView extends VerticalLayout implements BeforeLeaveObserver {

    private final ServicioUsuario servicioUsuario;
    private final AuthenticatedUser authenticatedUser;
    private TextField codigoTextField;
    private Button enviarCorreoButton;
    private Button nuevaAccionButton;
    private Span aclaracion;
    private String codigo;
    private Span texto1;
    private Span texto2;

    boolean exitoCambio = false;

    public ConfirmarView(ServicioUsuario servicioUsuario, AuthenticatedUser authenticatedUser) {
        this.servicioUsuario = servicioUsuario;
        this.authenticatedUser = authenticatedUser;

        // Campo y botón para introducir el correo
        H2 texto = new H2("Confirmar Cuenta");
        texto1 = new Span("Se le enviará un correo con un código para confirmar la existencia de su email");
        texto2 = new Span("Por favor pulse el botón para comenzar");
        enviarCorreoButton = new Button("Empezar", e -> enviarCorreo());

        // Campos inicialmente ocultos
        aclaracion = new Span("Se ha envíado un código al correo introducido, por favor escriba el código y pulse el nuevo botón.");
        codigoTextField = new TextField("Código recibido");
        nuevaAccionButton = new Button("Confirmar cuenta", e -> nuevaAccion(codigoTextField.getValue()));
        nuevaAccionButton.setVisible(false);
        codigoTextField.setVisible(false);
        aclaracion.setVisible(false);

        // Diseño vertical para los elementos
        add(texto, texto1, texto2, enviarCorreoButton, aclaracion, codigoTextField, nuevaAccionButton);
    }

    private void enviarCorreo() {
        // Generar el código aleatorio
        codigo = generarCodigoAleatorio();

        // Ocultar el primer conjunto de elementos
        enviarCorreoButton.setVisible(false);
        texto1.setVisible(false);
        texto2.setVisible(false);

        // Mostrar el segundo conjunto de elementos
        aclaracion.setVisible(true);
        codigoTextField.setVisible(true);
        nuevaAccionButton.setVisible(true);

        Optional<Cliente> user = authenticatedUser.get();

        if(user.isPresent()){
            try {
                // Configuración del servidor de correo saliente (SMTP)
                String host = "smtp.gmail.com";
                String username = "raulero999z@gmail.com";
                String password = "rijr kwqy hudz xzqd";

                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                // Crear una sesión con autenticación
                Session session = Session.getInstance(properties, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

                // Crear un objeto Message
                Message message = new MimeMessage(session);
                // Configurar el remitente
                message.setFrom(new InternetAddress(username));
                // Configurar el destinatario
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.get().getEmail().toString()));
                // Asunto del correo
                message.setSubject("Confirmar Cuenta CadizMovil");

                // Establecer el contenido del mensaje
                String cuerpoMensaje = "Estimado usuario,\n\n"
                        + "Hemos recibido una solicitud para confirmar la cuenta de nuestra página CadizMovil.\n\n"
                        + "Tu código de verificación es: " + codigo + "\n\n"
                        + "Utiliza este código para poder confirmar que tu cuenta está asociada a este correo.\n\n"
                        + "Gracias, equipo de soporte CadizMovil.";
                message.setText(cuerpoMensaje);

                // Enviar el mensaje
                Transport.send(message);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void nuevaAccion(String nuevoValor) {
        // Realizar la nueva acción con el nuevo valor
        if (codigo.equals(nuevoValor)) {
            Optional<Cliente> user = authenticatedUser.get();
            if (user.isPresent()) {
                user.get().setActive(true);
                servicioUsuario.actualizar(user.get());
                exitoCambio = true;
                UI.getCurrent().navigate("login");
            } else {
                Notification.show("Ha ocurrido un error inesperado.", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } else {
            Notification.show("Error: Código incorrecto.", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private String generarCodigoAleatorio() {
        int numeroAleatorio = generarNumeroAleatorio(10000000, 99999999);
        return generarVocalAleatoria() + String.valueOf(numeroAleatorio) + generarVocalAleatoria();
    }

    private int generarNumeroAleatorio(int min, int max) {
        SecureRandom random = new SecureRandom();
        return random.nextInt((max - min) + 1) + min;
    }

    private char generarVocalAleatoria() {
        char[] vocales = {'a', 'e', 'i', 'o', 'u'};
        SecureRandom random = new SecureRandom();
        return vocales[random.nextInt(vocales.length)];
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
        if (exitoCambio)
            Notification.show("Confirmación de contraseña correcta", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

}
