package ax.ha.it.oo1.util;

import ax.ha.it.oo1.counters.BoundedCounter;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DataList and (indirectly) Measurable
 *
 * @author joakim
 */
public class ArrayListTest {

    private ArrayList<Measurable> list;
    private Measurable m1;
    private Measurable m2;
    private Measurable m3;
    private static final int ALLOCATION_AMOUNT = 3;

    public ArrayListTest() {
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
        list = new ArrayList<>(ALLOCATION_AMOUNT);

        // Starting value 1, lower -10, upper 10
        m1 = new BoundedCounter(1, -10, 10);

        // Starting value -2, lower -10, upper 10
        m2 = new BoundedCounter(-2, -10, 10);

        // Value = 5
        m3 = new Observation(5, LocalDate.now());

        list.add(m1);
        list.add(m2);
        list.add(m3);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGet() {
        assertEquals(m1, list.get(0));
        assertEquals(m2, list.get(1));
        assertEquals(m3, list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    public void testMaximum() {
        // m3 should have largest value (5)
        Measurable maxMeasurable = list
                .parallelStream()
                .max(Comparator.comparingDouble(Measurable::getValue))
                .orElse(null);

        assertEquals(m3, maxMeasurable);

        // append a new large observation
        Observation m4 = new Observation(99.5, LocalDate.now());
        list.add(m4);

        maxMeasurable = list
                .parallelStream()
                .max(Comparator.comparingDouble(Measurable::getValue))
                .orElse(null);
        // now m4 should have largest value (99)
        assertEquals(m4, maxMeasurable);

    }

    @Test
    public void testMinimum() {
        // m2 should have smallest value (-2)
        Measurable minMeasurable = list
                .parallelStream()
                .min(Comparator.comparingDouble(Measurable::getValue))
                .orElse(null);
        assertEquals(m2, minMeasurable);

        // remove m2
        list.remove(1);
        // now m1 should have smallest value (1)
        minMeasurable = list
                .parallelStream()
                .min(Comparator.comparingDouble(Measurable::getValue))
                .orElse(null);
        assertEquals(m1, minMeasurable);

    }

    @Test
    public void testAverage() {
        // With current values the average should be 4/3 (about 1.3333333333):
        // (1-2+5)/3 = 4/3
        double result = list.parallelStream().collect(Collectors.averagingDouble(Measurable::getValue));
        assertEquals(4 / 3.0, result, 0.00001);


    }

    @Test
    public void testAppend() {
        Observation m4 = new Observation(23.5, LocalDate.now());
        //assertArraySize(list, ALLOCATION_AMOUNT);

        // First append should trigger growth of the array (3=>6)
        list.add(m4);
        //assertArraySize(list, ALLOCATION_AMOUNT * 2);
        assertEquals(m4, list.get(ALLOCATION_AMOUNT));
        assertEquals(ALLOCATION_AMOUNT + 1, list.size());

        list.add(m4);
        list.add(m4);
        //assertArraySize(list, ALLOCATION_AMOUNT * 2);

        // New growth trigger, 6=>9
        list.add(m4);
        //assertArraySize(list, ALLOCATION_AMOUNT * 3);

        list.add(m4);
        list.add(m4);

        // Sanity check, we should now have 9 objects in the array => size=9
        assertEquals(ALLOCATION_AMOUNT * 3, list.size());

    }

    @Test
    public void testInsert() {
        Observation m4 = new Observation(999, LocalDate.now());

        // Too small index => exception
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, m4));
        // assertArraySize(list, ALLOCATION_AMOUNT);
        assertEquals(m1, list.get(0));

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(4, m4));
        // Too large index => exception
        // assertArraySize(list, ALLOCATION_AMOUNT);
        assertEquals(m1, list.get(0));
        assertEquals(m3, list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));

        // First insertion should trigger growth of the array (3=>6)
        //assertArraySize(list, ALLOCATION_AMOUNT);
        list.add(0, m4);
        //assertArraySize(list, ALLOCATION_AMOUNT * 2);
        assertEquals(m4, list.get(0));
        assertEquals(m1, list.get(1));
        assertEquals(ALLOCATION_AMOUNT + 1, list.size());

        list.add(4, m4);
        //assertArraySize(list, ALLOCATION_AMOUNT * 2);
        assertEquals(m4, list.get(4));
    }

    @Test
    public void testRemove() {
        // Too small index
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertEquals(3, list.size());

        // Too large index
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
        assertEquals(3, list.size());

        Observation m4 = new Observation(999, LocalDate.now());
        // Growth 3=>6
        list.add(m4);

        Measurable removed = list.remove(1);
        assertEquals(m2, removed);

        removed = list.remove(0);
        assertEquals(m1, removed);

        removed = list.remove(0);
        assertEquals(m3, removed);

        // Final removal should trigger shrinking of the array (6=>3)
        list.remove(0);
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));

    }


}
