package es.uca.iw.frontend.vistas.cliente;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import es.uca.iw.backend.componentes.AuthenticatedUser;
import es.uca.iw.backend.clases.Cliente;
import es.uca.iw.backend.clases.Factura;
import es.uca.iw.backend.servicios.ServicioFactura;
import es.uca.iw.frontend.vistas.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Route(value = "facturas", layout = MainLayout.class)
@RolesAllowed("USER")
@PageTitle("Facturas")
public class FacturasView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;
    private final ServicioFactura facturaService;
    private Grid<Factura> grid = new Grid<>(Factura.class, false);

    @Autowired
    public FacturasView(AuthenticatedUser authenticatedUser, ServicioFactura facturaService) {
        this.authenticatedUser = authenticatedUser;
        this.facturaService = facturaService;

        configureGrid();

        setAlignItems(FlexComponent.Alignment.CENTER);
    }

    private void configureGrid() {
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(Factura::getFecha).setHeader("Fecha");

        // Agregar una columna para el enlace de descarga
        grid.addComponentColumn(this::createDownloadLink).setHeader("Descargar PDF");

        Optional<Cliente> optionalCliente = authenticatedUser.get();
        if (optionalCliente.isPresent()) {
            List<Factura> facturas = facturaService.getFacturasByCliente(optionalCliente.get());
            grid.setItems(facturas);
            grid.getDataProvider().refreshAll();
        }

        add(grid);
    }

    private Anchor createDownloadLink(Factura factura) {
        byte[] pdfBytes = factura.getPdfContenido();

        // Crear un StreamResource para el enlace de descarga
        StreamResource resource = new StreamResource("facturaCadizMovil.pdf",
                () -> new ByteArrayInputStream(pdfBytes));

        // Crear un componente Anchor para el enlace de descarga
        Anchor downloadLink = new Anchor(resource, "Descargar");
        downloadLink.getElement().setAttribute("download", true);

        return downloadLink;
    }
}