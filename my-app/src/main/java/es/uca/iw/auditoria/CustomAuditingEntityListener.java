package es.uca.iw.auditoria;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import es.uca.iw.atencion_cliente.Consulta;

public class CustomAuditingEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Consulta consulta) {
            consulta.setCreatedDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Consulta consulta) {
            consulta.setLastModifiedDate(LocalDateTime.now());
        }
    }
}

