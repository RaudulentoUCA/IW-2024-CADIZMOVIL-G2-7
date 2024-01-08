package es.uca.iw.cliente;

import es.uca.iw.contract.Contract;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    @Column(name = "is_active")
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contract> contracts;

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


    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public List<GrantedAuthority> getAuthorities() {
        return this.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

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
        return isActive;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }


    public void setActive(boolean isActive) {
        this.isActive = isActive;
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