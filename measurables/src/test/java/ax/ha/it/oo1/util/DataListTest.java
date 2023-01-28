package ax.ha.it.oo1.util;

import ax.ha.it.oo1.counters.BoundedCounter;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DataList and (indirectly) Measurable
 *
 * @author joakim
 */
public class DataListTest {

    private DataList<Measurable> list;
    private Measurable m1;
    private Measurable m2;
    private Measurable m3;
    private static final int ALLOCATION_AMOUNT = 3;

    public DataListTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        // Initial array size = 3
        list = new DataList<>(ALLOCATION_AMOUNT);

        // Starting value 1, lower -10, upper 10
        m1 = new BoundedCounter(1, -10, 10);

        // Starting value -2, lower -10, upper 10
        m2 = new BoundedCounter(-2, -10, 10);

        // Value = 5
        m3 = new Observation(5, LocalDate.now());

        list.append(m1);
        list.append(m2);
        list.append(m3);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGet() {
        assertEquals(m1, list.get(0));
        assertEquals(m2, list.get(1));
        assertEquals(m3, list.get(2));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    public void testCreatingDifferentObjects() {
        //Some of the cases will result in compile errors

        DataList<Counter> counters = new DataList<>(3);   // OK
        counters.append(new BoundedCounter(0, 0, 10));     // OK
        counters.append(() -> 42);                     // NOT OK
        counters.append(new Observation(10, LocalDate.now())); // NOT OK

        DataList<Measurable> measurables = new DataList<>(3); // OK
        measurables.append(new BoundedCounter(0, 0, 10));  // OK
        measurables.append(() -> 42);                  // OK
        measurables.append(
                new Observation(10, LocalDate.now()));  // OK

        DataList<String> strings = new DataList<>(3);      // NOT OK

    }

    @Test
    public void testSize() {
        assertEquals(3, list.size());

        DataList<Measurable> empty = new DataList<>(3);
        assertEquals(0, empty.size());
    }

    @Test
    public void testMaximum() {
        // m3 should have largest value (5)
        assertEquals(m3, list.maximum());

        // append a new large observation
        Observation m4 = new Observation(99.5, LocalDate.now());
        list.append(m4);

        // now m4 should have largest value (99)
        assertEquals(m4, list.maximum());

        DataList<Measurable> empty = new DataList<>(3);
        assertNull(empty.maximum());
    }

    @Test
    public void testMinimum() {
        // m2 should have smallest value (-2)
        assertEquals(m2, list.minimum());

        // remove m2
        list.remove(1);
        // now m1 should have smallest value (1)
        assertEquals(m1, list.minimum());

        DataList<Measurable> empty = new DataList<>(3);
        assertNull(empty.minimum());
    }

    @Test
    public void testAverage() {
        // With current values the average should be 4/3 (about 1.3333333333):
        // (1-2+5)/3 = 4/3
        assertEquals(4 / 3.0, list.average(), 0.00001);

        DataList<Measurable> empty = new DataList<>(3);
        assertEquals(0.0, empty.average(), 0.00001);

    }

    @Test
    public void testAppend() {
        Observation m4 = new Observation(23.5, LocalDate.now());
        assertArraySize(list, ALLOCATION_AMOUNT);

        // First append should trigger growth of the array (3=>6)
        list.append(m4);
        assertArraySize(list, ALLOCATION_AMOUNT * 2);
        assertEquals(m4, list.get(ALLOCATION_AMOUNT));
        assertEquals(ALLOCATION_AMOUNT + 1, list.size());

        list.append(m4);
        list.append(m4);
        assertArraySize(list, ALLOCATION_AMOUNT * 2);

        // New growth trigger, 6=>9
        list.append(m4);
        assertArraySize(list, ALLOCATION_AMOUNT * 3);

        list.append(m4);
        list.append(m4);

        // Sanity check, we should now have 9 objects in the array => size=9
        assertEquals(ALLOCATION_AMOUNT * 3, list.size());

    }

    @Test
    public void testInsert() {
        Observation m4 = new Observation(999, LocalDate.now());

        // Too small index => exception
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.insert(m4, -1));
        assertArraySize(list, ALLOCATION_AMOUNT);
        assertEquals(m1, list.get(0));

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.insert(m4, 4));
        // Too large index => exception
        assertArraySize(list, ALLOCATION_AMOUNT);
        assertEquals(m1, list.get(0));
        assertEquals(m3, list.get(2));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.get(3));

        // First insertion should trigger growth of the array (3=>6)
        assertArraySize(list, ALLOCATION_AMOUNT);
        list.insert(m4, 0);
        assertArraySize(list, ALLOCATION_AMOUNT * 2);
        assertEquals(m4, list.get(0));
        assertEquals(m1, list.get(1));
        assertEquals(ALLOCATION_AMOUNT + 1, list.size());

        list.insert(m4, 4);
        assertArraySize(list, ALLOCATION_AMOUNT * 2);
        assertEquals(m4, list.get(4));
    }

    @Test
    public void testRemove() {
        // Too small index
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.remove(-1));
        assertEquals(3, list.size());

        // Too large index
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.remove(3));
        assertEquals(3, list.size());

        Observation m4 = new Observation(999, LocalDate.now());
        // Growth 3=>6
        list.append(m4);
        assertArraySize(list, ALLOCATION_AMOUNT * 2);

        Measurable removed = list.remove(1);
        assertEquals(m2, removed);

        removed = list.remove(0);
        assertEquals(m1, removed);

        removed = list.remove(0);
        assertEquals(m3, removed);

        // Final removal should trigger shrinking of the array (6=>3)
        assertArraySize(list, ALLOCATION_AMOUNT * 2);
        list.remove(0);
        assertArraySize(list, ALLOCATION_AMOUNT);

        assertNull(list.remove(0));

    }

    // Helper method, use reflection to bypass encapsulation and check size of
    // the internal array
    private static void assertArraySize(DataList<Measurable> ds, int size) {
        try {
            Field f = ds.getClass().getDeclaredField("elements");
            f.setAccessible(true);
            Object[] list = (Object[]) f.get(ds);
            assertEquals(size, list.length);
        } catch (NoSuchFieldException | SecurityException |
                 IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(DataList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
