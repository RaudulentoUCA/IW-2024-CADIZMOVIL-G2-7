package es.uca.iw.atencion_cliente;

import es.uca.iw.cliente.Cliente;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
public class Consulta {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @Column(name = "asunto", nullable = false, length = 128)
    private String asunto;
    @Column(name = "cuerpo", nullable = false, length = 1024)
    private String cuerpo;

    public UUID getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public String getAsunto() {
        return asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }
}
