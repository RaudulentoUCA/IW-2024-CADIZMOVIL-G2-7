package es.uca.iw.Tarifa;

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

    @Column(name = "fijo", nullable = false)
    private boolean fijo;

    @Column(name = "fibra", nullable = false)
    private boolean fibra;

    @Column(name = "megasMovil", nullable = false, columnDefinition = "int default 0")
    private int megasMovil;

    public String getNombre() {
        return nombre;
    }

    public boolean getfijo() {
        return fijo;
    }

    public boolean getfibra() {
        return fibra;
    }

    public boolean getpermiteRoaming() {
        return permiteRoaming;
    }
    public String getDescripcion() { return  descripcion; }

    public int getmegasMovil() {
        return megasMovil;
    }

    public void setNombre(String n) {
        nombre = n;
    }
    public void setpermiteRoaming(boolean b) {
        permiteRoaming = b;
    }

    public float getPrecio() {
        return precio;
    }
    public void setDescripcion(String s){ descripcion = s;}

    public void setPrecio(float p) {
        precio = p;
    }

    public void setfijo(boolean b) {
        fijo = b;
    }

    public void setfibra(boolean b) {
        fibra = b;
    }

    public void setmegasMovil(int iNum) {
        megasMovil = iNum;
    }
}
