package es.uca.iw.atencion_cliente;

import es.uca.iw.cliente.Cliente;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Respuesta {
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

}
