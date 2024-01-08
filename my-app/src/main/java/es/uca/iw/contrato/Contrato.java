package es.uca.iw.contrato;

import es.uca.iw.cliente.Cliente;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Cliente cliente;

    @Column(name="fechaInicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "fechaFin", nullable = false)
    private Date fechaFin;

    @Column(name = "descuento", nullable = false, columnDefinition = "float default 0")
    private float descuento;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }
}
