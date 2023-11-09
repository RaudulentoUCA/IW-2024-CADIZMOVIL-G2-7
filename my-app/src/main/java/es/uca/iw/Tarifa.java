package es.uca.iw;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 64, unique = true)
    private String nombre;
    @Column(name = "precio", nullable = false)
    private float precio;
    @Column(name = "permiteRoaming", nullable = false)
    private boolean permiteRoaming;

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
}
