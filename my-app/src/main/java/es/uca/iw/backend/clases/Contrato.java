package es.uca.iw.backend.clases;

import es.uca.iw.backend.auditoria_tablas.CustomAuditingEntityListener;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(CustomAuditingEntityListener.class)
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

    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @Column(name = "modified_at")
    private LocalDateTime lastModifiedDate;
    @Column(name = "compartirDatos", nullable = false, columnDefinition = "boolean default false")
    private boolean compartirDatos;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> numerosBloqueados = new HashSet<>();

    public Cliente getUsuario() {
        return cliente;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setUsuario(Cliente cliente) {
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

    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate)  { this.lastModifiedDate = lastModifiedDate; }

    public boolean isCompartirDatos() {
        return compartirDatos;
    }

    public void setCompartirDatos(boolean compartirDatos) {
        this.compartirDatos = compartirDatos;
    }

    public Set<String> getNumerosBloqueados() {
        return numerosBloqueados;
    }

    public void setNumerosBloqueados(Set<String> numerosBloqueados) {
        this.numerosBloqueados = numerosBloqueados;
    }
}
