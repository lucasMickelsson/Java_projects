package ax.ha.it.oo1.counters;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoundedCounterTest {
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
            BoundedCounter boundedCounter = new BoundedCounter(1, 3, 2);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            BoundedCounter boundedCounter = new BoundedCounter(1, 2, 3);
        });
    }

    @Test
    public void testIncrement() {
        BoundedCounter boundedCounter = new BoundedCounter(0, -2, 3);
        boundedCounter.increment();
        assertEquals(1, boundedCounter.getCount());
    }

    @Test
    public void testDecrement() {
        BoundedCounter boundedCounter = new BoundedCounter(1, -2, 3);
        boundedCounter.decrement();
        assertEquals(0, boundedCounter.getCount());
    }

    @Test
    public void testIncrementToLimit() {
        BoundedCounter boundedCounter = new BoundedCounter(3, -2, 3);
        boundedCounter.increment();
        assertEquals(3, boundedCounter.getCount());
    }

    @Test
    public void testDecrementToLimit() {
        BoundedCounter boundedCounter = new BoundedCounter(-2, -2, 3);
        boundedCounter.decrement();
        assertEquals(-2, boundedCounter.getCount());
    }
}
