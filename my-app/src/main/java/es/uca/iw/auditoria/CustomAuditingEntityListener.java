package es.uca.iw.auditoria;

import es.uca.iw.cliente.Cliente;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

public class CustomAuditingEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Cliente cliente) {
            cliente.setCreatedDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Cliente cliente) {
            cliente.setLastModifiedDate(LocalDateTime.now());
        }
    }
}

