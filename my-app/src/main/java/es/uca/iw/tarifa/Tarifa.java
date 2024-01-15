package es.uca.iw.tarifa;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

@Entity
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 64, unique = true)
    private String nombre;
    @Column(name = "precio", nullable = false)
    private float precio;

    @Column(name = "available_MB")
    private Integer availableMB;

    @Column(name = "available_Min")
    private Integer availableMin;

    @Column(name = "available_SMS")
    private Integer availableSMS;

    @Column(name = "permiteRoaming", nullable = false)
    private boolean permiteRoaming;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fijo", nullable = false)
    private boolean fijo;

    @Column(name = "fibra", nullable = false)
    private boolean fibra;

    @Column(name = "disponible", nullable = false)
    private boolean isAvailable;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        nombre = n;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float p) {
        precio = p;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAvailableMB() {
        return availableMB;
    }

    public void setAvailableMB(Integer availableMB) {
        this.availableMB = availableMB;
    }

    public Integer getAvailableMin() {
        return availableMin;
    }

    public void setAvailableMin(Integer availableMin) {
        this.availableMin = availableMin;
    }

    public Integer getAvailableSMS() {
        return availableSMS;
    }

    public void setAvailableSMS(Integer availableSMS) {
        this.availableSMS = availableSMS;
    }

    public boolean isPermiteRoaming() {
        return permiteRoaming;
    }

    public void setPermiteRoaming(boolean permiteRoaming) {
        this.permiteRoaming = permiteRoaming;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isFijo() {
        return fijo;
    }

    public void setFijo(boolean fijo) {
        this.fijo = fijo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tarifa tarifa)) return false;
        return Float.compare(precio, tarifa.precio) == 0 && permiteRoaming == tarifa.permiteRoaming && Objects.equals(id, tarifa.id) && Objects.equals(nombre, tarifa.nombre) && Objects.equals(availableMB, tarifa.availableMB) && Objects.equals(availableMin, tarifa.availableMin) && Objects.equals(availableSMS, tarifa.availableSMS) && Objects.equals(descripcion, tarifa.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, precio, availableMB, availableMin, availableSMS, permiteRoaming, descripcion);
    }

    public boolean isFibra() {
        return fibra;
    }

    public void setFibra(boolean fibra) {
        this.fibra = fibra;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
