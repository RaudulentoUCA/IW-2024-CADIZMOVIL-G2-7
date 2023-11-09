package es.uca.iw;

import jakarta.persistence.*;

@Entity
public class TipoTrabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 64, unique = true)
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        nombre = n;
    }
}
