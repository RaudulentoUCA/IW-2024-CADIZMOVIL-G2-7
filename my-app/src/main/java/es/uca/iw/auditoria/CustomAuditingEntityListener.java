package es.uca.iw.auditoria;

import es.uca.iw.atencion_cliente.Consulta;
import es.uca.iw.cliente.Cliente;
import es.uca.iw.contrato.Contrato;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

public class CustomAuditingEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Cliente cliente) {
            cliente.setCreatedDate(LocalDateTime.now());
        } else if (entity instanceof Contrato contrato) {
            contrato.setCreatedDate(LocalDateTime.now());
        } else if (entity instanceof Consulta consulta) {
            consulta.setCreatedDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Cliente cliente) {
            cliente.setLastModifiedDate(LocalDateTime.now());
        } else if (entity instanceof Contrato contrato) {
            contrato.setLastModifiedDate(LocalDateTime.now());
        } else if (entity instanceof Consulta consulta) {
            consulta.setLastModifiedDate(LocalDateTime.now());
        }
    }
}


