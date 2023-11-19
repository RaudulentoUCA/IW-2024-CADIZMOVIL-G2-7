package es.uca.iw;

import jakarta.persistence.*;

@Entity
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

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;


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
