package ax.ha.it.oo1.util;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Observation.
 *
 * @author joakim
 */
public class ObservationTest {

    public ObservationTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetValue() {
        Observation instance = new Observation(49.59, LocalDate.now());
        double expResult = 49.59;
        double result = instance.getValue();
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetDate() {
        Observation instance = new Observation(49.59,
                LocalDate.of(2022, Month.SEPTEMBER, 1));
        LocalDate expResult = LocalDate.of(2022, Month.SEPTEMBER, 1);
        LocalDate result = instance.getDate();
        assertEquals(expResult, result);
    }

}
