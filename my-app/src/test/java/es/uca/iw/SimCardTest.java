package  es.uca.iw;

import es.uca.iw.backend.clases.SimCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimCardTest {

    private SimCard simCard;

    @BeforeEach
    public void setUp() {
        simCard = new SimCard();
    }

    @Test
    public void testGetSetNumber() {
        Integer number = 12345;
        simCard.setNumber(number);
        assertEquals(number, simCard.getNumber());
    }

    @Test
    public void testGetSetUsedMinutes() {
        Integer usedMinutes = 100;
        simCard.setUsedMinutes(usedMinutes);
        assertEquals(usedMinutes, simCard.getUsedMinutes());
    }

    @Test
    public void testGetSetUsedMb() {
        Integer usedMb = 1024;
        simCard.setUsedMb(usedMb);
        assertEquals(usedMb, simCard.getUsedMb());
    }

    @Test
    public void testGetSetUsedSms() {
        Integer usedSms = 50;
        simCard.setUsedSms(usedSms);
        assertEquals(usedSms, simCard.getUsedSms());
    }

    @Test
    public void testIsActive() {
        simCard.setActive(true);
        assertTrue(simCard.isActive());
        simCard.setActive(false);
        assertFalse(simCard.isActive());
    }

}

