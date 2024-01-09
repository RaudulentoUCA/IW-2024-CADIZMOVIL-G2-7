package es.uca.iw.views.Trabajador;

import com.itextpdf.text.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.ServiciosCliente;
import es.uca.iw.contrato.ServiciosContrato;
import es.uca.iw.simcard.SimCardService;
import es.uca.iw.tarifa.Tarifa;
import jakarta.annotation.security.RolesAllowed;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

@Route("factura-view")
@AnonymousAllowed
public class FacturacionView extends VerticalLayout {

    private final ComboBox<Cliente> clienteComboBox = new ComboBox<>("Seleccionar Cliente");
    private final FormLayout formLayout = new FormLayout();
    private final Button guardarButton = new Button("Enviar");

    private final Binder<Tarifa> binder = new Binder<>(Tarifa.class);

    // Aquí deberías inyectar tu servicio o repositorio de tarifas para obtener la lista de tarifas
    private final ServiciosContrato serviciosContrato;
    private final SimCardService servicioSimcard;

    private final ServiciosCliente serviciosCliente;
    private Cliente clienteSeleccionado = null;

    public FacturacionView(ServiciosContrato serviciosContrato, SimCardService servicioSimcard, ServiciosCliente serviciosCliente) {
        this.serviciosContrato = serviciosContrato;
        this.servicioSimcard = servicioSimcard;
        this.serviciosCliente = serviciosCliente;

        // ComboBox
        List<Cliente> clientes = serviciosCliente.getAllClientes();
        clienteComboBox.setItems(clientes);
        clienteComboBox.setItemLabelGenerator(Cliente::getEmail);

        // Formulario
        formLayout.addFormItem(clienteComboBox, "Cliente");


        guardarButton.addClickListener(event -> enviarFactura());
        clienteComboBox.addValueChangeListener(event -> seleccionCliente(event.getValue()));
        add(formLayout, guardarButton);
    }

    private void seleccionCliente(Cliente cliente) {
        if (cliente != null) {
            clienteSeleccionado = cliente;
        }
    }


    private void enviarFactura() {
        if (clienteSeleccionado != null) {
            // Configuración del servidor de correo saliente (SMTP)
            String host = "smtp.gmail.com";
            String username = "raulero999z@gmail.com";
            String password = "jbhy zmka jida huqk";

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

            try {
                // Crear un objeto Message
                Message message = new MimeMessage(session);
                // Configurar el remitente
                message.setFrom(new InternetAddress(username));
                // Configurar el destinatario
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(clienteSeleccionado.getEmail()));
                // Asunto del correo
                message.setSubject("Factura CadizMovil");        //Asunto

                // Crear el cuerpo del correo
                BodyPart mensajeParte = new MimeBodyPart();
                mensajeParte.setText("Buenas " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos() + ".\nLe enviamos la factura de este mes con nuestra compañía, esperemos que disfrute de los servicios.\nUn cordial saludo, si tiene alguna duda contáctenos.");

                // Crear el documento PDF
                String fecha = "Fecha de hoy";
                double subtotal = 100.0; // Hay que hacer aquí los cálculos
                double impuestos = subtotal * 0.20;
                double total = subtotal + impuestos;

                // Crear documento PDF
                Document document = new Document();

                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, pdfStream);
                document.open();

                // Configurar fuente y estilos
                Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
                Font fontCuerpo = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

                // Agregar contenido al documento
                Paragraph titulo = new Paragraph("Factura", fontTitulo);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                document.add(Chunk.NEWLINE);

                // Agregar detalles de la factura
                document.add(new Paragraph("Fecha: " + fecha, fontCuerpo));
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Subtotal: " + subtotal + "€", fontCuerpo));
                document.add(new Paragraph("Impuestos: " + impuestos + "€", fontCuerpo));
                document.add(new Paragraph("Total: " + total + "€", fontCuerpo));

                document.add(Chunk.NEWLINE);

                // Agradecimiento
                document.add(new Paragraph("Gracias por elegir nuestros servicios. Si tiene alguna pregunta o inquietud, no dude en ponerse en contacto con nosotros.", fontCuerpo));

                // Agregar información de la empresa
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Atentamente, CadizMovil.", fontCuerpo));

                document.close();

                // Guardar el PDF en un archivo
                FileOutputStream fileOutputStream = new FileOutputStream("factura.pdf");
                fileOutputStream.write(pdfStream.toByteArray());
                fileOutputStream.close();

                // Adjuntar el documento PDF al correo
                BodyPart adjuntoParte = new MimeBodyPart();
                adjuntoParte.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfStream.toByteArray(), "application/pdf")));
                adjuntoParte.setFileName("factura.pdf");

                // Combinar las partes del mensaje
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mensajeParte);
                multipart.addBodyPart(adjuntoParte);

                // Establecer el contenido del mensaje
                message.setContent(multipart);

                // Enviar el mensaje
                Transport.send(message);

                Notification.show("Factura enviada correctamente a " + clienteSeleccionado.getEmail());

            } catch (MessagingException | DocumentException e) {
                e.printStackTrace();
                Notification.show("Error al enviar la factura: " + e.getMessage());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Notification.show("Selecciona un cliente antes de enviar la factura.");
        }
    }
}
