package es.uca.iw.contrato;

import es.uca.iw.cliente.Cliente;
import es.uca.iw.cliente.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name="fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fechaFin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "descuento", nullable = false, columnDefinition = "float default 0")
    private float descuento;

    @Column(name = "compartirDatos", nullable = false, columnDefinition = "boolean default false")
    private boolean compartirDatos;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> numerosBloqueados = new HashSet<>();

    public Cliente getCliente() {
        return cliente;
    }
    public int getId() {
        return id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public boolean isCompartirDatos() {
        return compartirDatos;
    }

    public void setCompartirDatos(boolean compartirDatos) {
        this.compartirDatos = compartirDatos;
    }
}
