package es.uca.iw.backend.clases;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Lob
    @Column(name = "pdf_contenido", columnDefinition = "BLOB")  //Para almacenar PDF
    private byte[] pdfContenido;

    @Column(name = "fecha")
    private LocalDate fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public byte[] getPdfContenido() {
        return pdfContenido;
    }

    public void setPdfContenido(byte[] pdfContenido) {
        this.pdfContenido = pdfContenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
