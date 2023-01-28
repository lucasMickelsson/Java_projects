package ax.ha.it.oo1.math;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the MeasurableFraction class
 *
 * @author joakim
 */
class MeasurableFractionTest {

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
        MeasurableFraction instance = new MeasurableFraction(3, 4);
        double result = instance.getValue();
        assertEquals(0.75, result, 0.0);

        instance = new MeasurableFraction(5);
        result = instance.getValue();
        assertEquals(5, result, 0.0);
    }

}