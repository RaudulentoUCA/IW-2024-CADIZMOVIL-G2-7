package es.uca.iw;

import jakarta.persistence.*;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 64)
    private String nombre;
    @Column(name = "apellidos", nullable = false, length = 128)
    private String apellidos;
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;
    @Column(name = "email", nullable = false, length = 64)
    private String email;
    @Column(name = "password", nullable = false, length = 64)
    private String password;
}