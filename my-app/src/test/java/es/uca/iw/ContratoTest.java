package es.uca.iw;
import es.uca.iw.backend.clases.Contrato;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ContratoTest {

    private Contrato contrato;

    @BeforeEach
    public void setUp() {
        contrato = new Contrato();
        contrato.setId(1);
        contrato.setFechaInicio(LocalDate.of(2022, 1, 1));
        contrato.setFechaFin(LocalDate.of(2022, 12, 31));
        contrato.setDescuento(0.1f);
        contrato.setCompartirDatos(true);

        LocalDateTime now = LocalDateTime.now();
        contrato.setCreatedDate(now);
        contrato.setLastModifiedDate(now);

        Set<String> numerosBloqueados = new HashSet<>();
        numerosBloqueados.add("123456");
        contrato.setNumerosBloqueados(numerosBloqueados);
    }

    @Test
    public void testGetId() {
        Assertions.assertEquals(1, contrato.getId());
    }

    @Test
    public void testGetFechaInicio() {
        Assertions.assertEquals(LocalDate.of(2022, 1, 1), contrato.getFechaInicio());
    }

    @Test
    public void testSetFechaFin() {
        contrato.setFechaFin(LocalDate.of(2023, 12, 31));
        Assertions.assertEquals(LocalDate.of(2023, 12, 31), contrato.getFechaFin());
    }

    @Test
    public void testGetDescuento() {
        Assertions.assertEquals(0.1f, contrato.getDescuento());
    }

    @Test
    public void testSetCompartirDatos() {
        contrato.setCompartirDatos(false);
        Assertions.assertFalse(contrato.isCompartirDatos());
    }

    @Test
    public void testGetNumerosBloqueados() {
        Set<String> numerosBloqueados = contrato.getNumerosBloqueados();
        Assertions.assertTrue(numerosBloqueados.contains("123456"));
    }

}

