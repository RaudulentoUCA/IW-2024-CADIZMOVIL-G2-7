package es.uca.iw.frontend.vistas.trabajadores.finanzas;

import com.itextpdf.text.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.servicios.ServicioUsuario;
import es.uca.iw.backend.clases.Contrato;
import es.uca.iw.backend.servicios.ServicioContrato;
import es.uca.iw.backend.clases.Factura;
import es.uca.iw.backend.servicios.ServicioFactura;
import es.uca.iw.backend.clases.SimCard;
import es.uca.iw.backend.servicios.ServicioSimCard;
import es.uca.iw.backend.clases.Tarifa;
import es.uca.iw.frontend.vistas.MainLayout;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

@Route(value = "factura-view", layout = MainLayout.class)
@RolesAllowed("FINANCE")
//@AnonymousAllowed
public class FacturacionView extends VerticalLayout {

    private final ComboBox<Cliente> clienteComboBox = new ComboBox<>("Seleccionar Cliente");
    private final FormLayout formLayout = new FormLayout();
    private final Button guardarButton = new Button("Enviar");

    private final Binder<Tarifa> binder = new Binder<>(Tarifa.class);
    private final ServicioContrato servicioContrato;
    private final ServicioSimCard servicioSimcard;

    private final ServicioUsuario servicioUsuario;
    private final ServicioFactura servicioFactura;
    private Cliente clienteSeleccionado = null;

    public FacturacionView(ServicioContrato servicioContrato, ServicioSimCard servicioSimcard, ServicioUsuario servicioUsuario, ServicioFactura servicioFactura) {
        this.servicioContrato = servicioContrato;
        this.servicioSimcard = servicioSimcard;
        this.servicioUsuario = servicioUsuario;
        this.servicioFactura = servicioFactura;

        // ComboBox
        List<Cliente> clientes = servicioUsuario.getAllClientes();
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

            try {
                // Crear el documento PDF en un CompletableFuture
                CompletableFuture<byte[]> pdfCompletableFuture = CompletableFuture.supplyAsync(() -> {
                    try {
                        return crearContenidoPDF();
                    } catch (DocumentException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });

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

                // Crear un objeto Message
                Message message = new MimeMessage(session);
                // Configurar el remitente
                message.setFrom(new InternetAddress(username));
                // Configurar el destinatario
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(clienteSeleccionado.getEmail()));
                // Asunto del correo
                message.setSubject("Factura CadizMovil"); // Asunto

                // Crear el cuerpo del correo
                BodyPart mensajeParte = new MimeBodyPart();
                mensajeParte.setText("Buenas " + clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos() + ".\nLe enviamos la factura de este mes con nuestra compañía, esperemos que disfrute de los servicios.\nUn cordial saludo, si tiene alguna duda contáctenos.");

                // Obtener el contenido del PDF desde el CompletableFuture
                byte[] pdfContent = pdfCompletableFuture.get();

                // Adjuntar el documento PDF al correo
                BodyPart adjuntoParte = new MimeBodyPart();
                adjuntoParte.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfContent, "application/pdf")));
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
                Factura factura = new Factura();
                factura.setCliente(clienteSeleccionado);
                factura.setFecha(LocalDate.now());
                factura.setPdfContenido(pdfContent);
                servicioFactura.registrarFactura(factura);

            } catch (MessagingException | InterruptedException | ExecutionException e) {
                Notification.show("Error al enviar la factura: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        } else {
            Notification.show("Selecciona un cliente antes de enviar la factura.");
        }
    }

    private byte[] crearContenidoPDF() throws DocumentException, IOException {
        // Crear documento PDF
        Document document = new Document();
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, pdfStream);
        document.open();

        // Cabecera de factura
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Font fontCuerpo = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Paragraph titulo = new Paragraph("Factura", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(Chunk.NEWLINE);

        // Obtener fecha actual
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'del año' yyyy   HH:mm");
        String fechaaponer = fechaActual.format(formato);

        // Calcular factura cliente
        List<Contrato> contratos = servicioContrato.getContratosByCliente(clienteSeleccionado);
        double precioTotal = 0.0;
        double precioContrato;
        double megasextra;
        double minextra;
        double smsextra;
        for (Contrato contrato : contratos) {
            List<SimCard> simCards = servicioSimcard.getSimCardsByContrato(contrato);
            precioContrato = 0.0;
            megasextra = 0.0;
            minextra = 0.0;
            smsextra = 0.0;

            for (SimCard simCard : simCards) {
                precioContrato += simCard.getTarifa().getPrecio();
                if(!contrato.isCompartirDatos()){
                    if(simCard.getTarifa().getAvailableMB() - simCard.getUsedMb() < 0)  // Megas
                        megasextra = megasextra - (simCard.getTarifa().getAvailableMB() - simCard.getUsedMb());
                }
                else {
                    megasextra = megasextra - (simCard.getTarifa().getAvailableMB() - simCard.getUsedMb()); //Si no ha consumido todos sera negativo
                }
                if(simCard.getTarifa().getAvailableMin() - simCard.getUsedMinutes() < 0)  // Minutos
                    minextra = minextra - (simCard.getTarifa().getAvailableMin() - simCard.getUsedMinutes());
                if(simCard.getTarifa().getAvailableSMS() - simCard.getUsedSms() < 0)  // SMS
                    smsextra = smsextra - (simCard.getTarifa().getAvailableSMS() - simCard.getUsedSms());
            }
            precioTotal = precioContrato * contrato.getDescuento() + precioTotal;
            if(megasextra > 0) precioTotal = precioTotal + megasextra*0.004;   // Precio de mega extra - if está por si hay compartir datos
            precioTotal = precioTotal + minextra*0.11;   // Precio de minuto extra
            precioTotal = precioTotal + smsextra*0.2;   // Precio de sms extra
        }

        // Detalles de la factura
        document.add(new Paragraph("Fecha: " + fechaaponer, fontCuerpo));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Subtotal: " + precioTotal + "€", fontCuerpo));
        document.add(new Paragraph("Impuestos: " + (precioTotal * 0.20) + "€", fontCuerpo));
        document.add(new Paragraph("Total: " + (precioTotal + (precioTotal * 0.20)) + "€", fontCuerpo));

        document.add(Chunk.NEWLINE);

        // Pie de factura
        document.add(new Paragraph("Gracias por elegir nuestros servicios. Si tiene alguna pregunta o inquietud, no dude en ponerse en contacto con nosotros.", fontCuerpo));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Atentamente, CadizMovil.", fontCuerpo));

        document.close();
        return pdfStream.toByteArray();
    }
}
