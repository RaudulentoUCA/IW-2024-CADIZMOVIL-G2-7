package es.uca.iw.Cliente;

import es.uca.iw.SimCard.SimCard;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class Cliente implements UserDetails {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;
    @Column(name = "nombre", nullable = false, length = 64)
    private String nombre;
    @Column(name = "apellidos", nullable = false, length = 128)
    private String apellidos;
    @Column(name = "dni", nullable = false, length = 9, unique = true)
    private String dni;
    @Column(name = "email", nullable = false, length = 64)
    private String email;
    @Column(name = "numero_contacto", nullable = true)
    private String numeroContacto;
    @Column(name = "fecha_de_nacimiento", nullable = false)
    private LocalDate fechaDeNacimiento;
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    private boolean isActive;

    public enum Role {
        ADMIN, USER
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<SimCard> simCards;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String p) {
        password = p;
    }

    public boolean isActive() {
        return isActive();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public List<SimCard> getSimCards() {
        return simCards;
    }

    public void setSimCards(List<SimCard> simCards) {
        this.simCards = simCards;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }
}