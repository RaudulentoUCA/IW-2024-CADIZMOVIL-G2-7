package es.uca.iw;

import jakarta.persistence.*;

@Entity
public class Trabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum TipoTrabajador {
        MARKETING, ATENCION, FINANZA
    }
    @Column(name = "puesto", nullable = false)
    private TipoTrabajador puesto;
    @Column(name = "nombre", nullable = false, length = 64)
    private String nombre;
    @Column(name = "apellidos", nullable = false, length = 128)
    private String apellidos;
    @Column(name = "dni", nullable = false, length = 9, unique = true)
    private String dni;
    @Column(name = "email", nullable = false, length = 64)
    private String email;
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    public TipoTrabajador getPuesto() { return puesto; }

    public void setPuesto(TipoTrabajador pt) {
        puesto = pt;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        nombre = n;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String a) {
        apellidos = a;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String d) {
        dni = d;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        email = e;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        password = p;
    }
}
