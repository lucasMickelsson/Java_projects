package ax.ha.it.oo1.util;

import ax.ha.it.oo1.counters.BoundedCounter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests base functionality from Counter
 * Note that this code creates objects of type CounterImpl
 * (implementation at bottom of this file)
 *
 * @author joakim
 */
public class CounterTest {

    public CounterTest() {
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
    public void testGetCount() {
        // Starting count 0, lower -2, upper 2
        Counter instance = new CounterImpl(0, -2, 2);
        int expResult = 0;
        int result = instance.getCount();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLowerLimit() {
        // Starting count 0, lower -2, upper 2
        Counter instance = new CounterImpl(0, -2, 2);
        int expResult = -2;
        int result = instance.getLowerLimit();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetUpperLimit() {
        // Starting count 0, lower -2, upper 2
        Counter instance = new CounterImpl(0, -2, 2);
        int expResult = 2;
        int result = instance.getUpperLimit();
        assertEquals(expResult, result);
    }

    @Test
    public void testAtUpperLimit() {
        // Starting count 0, lower -2, upper 2
        Counter instance = new CounterImpl(0, -2, 2);
        assertFalse(instance.atUpperLimit());

        instance = new CounterImpl(0, -2, 0);
        assertTrue(instance.atUpperLimit());
    }

    @Test
    public void testAtLowerLimit() {
        // Starting count 0, lower -2, upper 2
        Counter instance = new CounterImpl(0, -2, 2);
        assertFalse(instance.atLowerLimit());

        instance = new CounterImpl(0, 0, 2);
        assertTrue(instance.atLowerLimit());
    }

    @Test
    public void testTooHighLowerLimit() {
        // Illegal arguments
        assertThrows(IllegalArgumentException.class, () -> {
            Counter counter = new BoundedCounter(0, 3, 2);
        });
        Counter instance = new CounterImpl(1, 0, 2);
        int expLower = 0, expUpper = 2;
        int result = instance.getLowerLimit();
        assertEquals(expLower, result);
        assertEquals(expUpper, instance.getUpperLimit());
    }

    @Test
    public void tooHighStartingValue() {
        // count > upperLimit
        assertThrows(IllegalArgumentException.class, () -> {
            Counter counter = new BoundedCounter(4, -2, 2);
        });
        Counter instance = new CounterImpl(1, -2, 2);
        int expResult = 1;
        int result = instance.getCount();
        assertEquals(expResult, result);
    }

    @Test
    public void tooLowStartingValue() {
        // count < lowerLimit
        assertThrows(IllegalArgumentException.class, () -> {
            Counter counter = new BoundedCounter(-4, -2, 2);
        });
        Counter instance = new CounterImpl(-1, -2, 2);
        int expResult = -1;
        int result = instance.getCount();
        // value should be equal to lower limit
        assertEquals(expResult, result);
    }

    // Dummy implementation to test base class functionality
    public class CounterImpl extends Counter {

        public CounterImpl(int count, int lower, int upper) {
            super(count, lower, upper);
        }

        @Override
        public void increment() {
        }

        @Override
        public void decrement() {
        }
    }

}
