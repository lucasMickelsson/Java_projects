package ax.ha.it.oo1.counters;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CircularCounterTest {
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
    public void testCreatingCounters() {
        //Illegal arguments
        assertThrows(IllegalArgumentException.class, () -> {
            CircularCounter circularCounter = new CircularCounter(1, 3, 2);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            CircularCounter circularCounter = new CircularCounter(1, 2, 0);
        });
    }

    @Test
    public void testIncrement() {
        CircularCounter circularCounter = new CircularCounter(0, -2, 3);
        circularCounter.increment();
        assertEquals(1, circularCounter.getCount());
    }

    @Test
    public void testDecrement() {
        CircularCounter circularCounter = new CircularCounter(1, -2, 3);
        circularCounter.decrement();
        assertEquals(0, circularCounter.getCount());
    }

    @Test
    public void testIncrementToLimit() {
        CircularCounter circularCounter = new CircularCounter(3, -2, 3);
        circularCounter.increment();
        assertEquals(-2, circularCounter.getCount());
    }

    @Test
    public void testDecrementToLimit() {
        CircularCounter circularCounter = new CircularCounter(-2, -2, 3);
        circularCounter.decrement();
        assertEquals(3, circularCounter.getCount());
    }
}
