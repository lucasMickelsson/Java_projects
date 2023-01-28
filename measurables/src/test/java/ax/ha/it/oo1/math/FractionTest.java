package ax.ha.it.oo1.math;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test cases for Fractions.
 *
 * @author joakim
 */
public class FractionTest {

    public FractionTest() {
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


    /**
     * Test of constructor Fraction(numerator, denominator)
     */
    @ParameterizedTest
    @CsvSource({
            "-1, 2, -1, 2",   // Negative sign on numerator => no change
            "1, -2, -1, 2",   // Negative sign on denominator => move to numerator
            "-1, -2, 1, 2",   // Negative signs on both numerator and
            // denominator cancel each other out
            "9, 27, 1, 3",    // Simplification test, 9/27 => 1/3
            "-10, 5, -2, 1"   // Simplification test, -10/5 => -2/1
    })
    public void testCreateFraction(int numerator, int denominator,
                                   int expectedNumerator, int expectedDenominator) {
        System.out.println("constructor(numerator, denominator)");
        Fraction instance = new Fraction(numerator, denominator);
        assertEquals(expectedNumerator, instance.getNumerator());
        assertEquals(expectedDenominator, instance.getDenominator());
    }


    /**
     * Test of constructor Fraction(integer)
     */
    @ParameterizedTest
    @CsvSource({"2, 2, 1",  // 2 => 2/1
            "-2, -2, 1"     // -2 => -2/1
    })
    public void testCreateFractionInteger(int integer,
                                          int expectedNumerator, int expectedDenominator) {
        System.out.println("constructor(integer)");
        Fraction instance = new Fraction(integer);
        assertEquals(expectedNumerator, instance.getNumerator());
        assertEquals(expectedDenominator, instance.getDenominator());
    }


    /**
     * Test of add method
     */
    @ParameterizedTest
    @CsvSource({"1, 2, 1, 2, 1, 1",  // 1/2 + 1/2 = 1/1
            "3, 4, 1, 2, 5, 4",      // 3/4 + 1/2 = 5/4
            "7, 12, 9, 26, 145, 156",   // 7/12 + 9/26 = 145/156
            "-3, 4, 1, 2, -1, 4"        // -3/4 + 1/2 = -1/4
    })
    public void testAdd(int firstNumerator, int firstDenominator,
                        int secondNumerator, int secondDenominator,
                        int expectedNumerator, int expectedDenominator) {
        System.out.println("add");
        Fraction first = new Fraction(firstNumerator, firstDenominator);
        Fraction second = new Fraction(secondNumerator, secondDenominator);

        Fraction result = first.add(second);
        assertEquals(expectedNumerator, result.getNumerator());
        assertEquals(expectedDenominator, result.getDenominator());
    }

    /**
     * Test addition resulting in zero as result.
     * Only check that denominator != zero, don't care about the exact value
     */
    @ParameterizedTest
    @CsvSource({"0, 1, 0, 2",   // 0/1 + 0/2
            "2, 3, -2, 3"       // 2/3 + (-2/3)

    })
    public void testAddWithZeroResult(int firstNumerator, int firstDenominator,
                                      int secondNumerator, int secondDenominator) {
        System.out.println("add with zero as result");
        Fraction first = new Fraction(firstNumerator, firstDenominator);
        Fraction second = new Fraction(secondNumerator, secondDenominator);

        Fraction result = first.add(second);
        assertEquals(0, result.getNumerator());
        assertNotEquals(0, result.getDenominator());
    }

    /**
     * Test of subtract method
     */
    @ParameterizedTest
    @CsvSource({"3, 4, 1, 2, 1, 4",  // 3/4 - 1/2 = 1/4
            "7, 12, 9, 26, 37, 156", // 7/12 - 9/26 = 37/156
            "-3, 4, 1, 2, -5, 4"     // -3/4 - 1/2 = -5/4
    })
    public void testSubtract(int firstNumerator, int firstDenominator,
                             int secondNumerator, int secondDenominator,
                             int expectedNumerator, int expectedDenominator) {
        System.out.println("subtract");
        Fraction first = new Fraction(firstNumerator, firstDenominator);
        Fraction second = new Fraction(secondNumerator, secondDenominator);

        Fraction result = first.subtract(second);
        assertEquals(expectedNumerator, result.getNumerator());
        assertEquals(expectedDenominator, result.getDenominator());
    }

    /**
     * Test subtraction resulting in zero as result.
     * Only check that denominator != zero, don't care about the exact value
     */
    @ParameterizedTest
    @CsvSource({"0, 1, 0, 2",  // 0/1 - 0/2
            "2, 3, 2, 3"       // 2/3 - 2/3
    })
    public void testSubtractWithZeroResult(int firstNumerator, int firstDenominator,
                                           int secondNumerator, int secondDenominator) {
        System.out.println("subtract with zero as result");
        Fraction first = new Fraction(firstNumerator, firstDenominator);
        Fraction second = new Fraction(secondNumerator, secondDenominator);

        Fraction result = first.subtract(second);
        assertEquals(0, result.getNumerator());
        assertNotEquals(0, result.getDenominator());
    }

    /**
     * Test of multiply method, of class Fraction.
     */
    @ParameterizedTest
    @CsvSource({"1, 2, 1, 2, 1, 4",   // 1/2 * 1/2 = 1/4
            "3, 4, 1, 2, 3, 8",       // 3/4 * 1/2 = 3/8
            "7, 12, 9, 26, 21, 104",  // 7/12 * 9/26 = 21/104
            "-3, 4, 1, 2, -3, 8"      // -3/4 * 1/2 = -3/8
    })
    public void testMultiply(int firstNumerator, int firstDenominator,
                             int secondNumerator, int secondDenominator,
                             int expectedNumerator, int expectedDenominator) {
        System.out.println("multiply");
        Fraction first = new Fraction(firstNumerator, firstDenominator);
        Fraction second = new Fraction(secondNumerator, secondDenominator);

        Fraction result = first.multiply(second);
        assertEquals(expectedNumerator, result.getNumerator());
        assertEquals(expectedDenominator, result.getDenominator());
    }


    /**
     * Test multiplication resulting in zero as result.
     * Only check that denominator != zero, don't care about the exact value
     */
    @ParameterizedTest
    @CsvSource({"0, 1, 0, 2",  // 0/1 * 0/2
            "2, 3, 0, 2"       // 2/3 * 0/2
    })
    public void testMultiplyWithZeroResult(int firstNumerator, int firstDenominator,
                                           int secondNumerator, int secondDenominator) {
        System.out.println("multiply with zero as result");
        Fraction first = new Fraction(firstNumerator, firstDenominator);
        Fraction second = new Fraction(secondNumerator, secondDenominator);

        Fraction result = first.multiply(second);
        assertEquals(0, result.getNumerator());
        assertNotEquals(0, result.getDenominator());
    }


    /**
     * Test of divide method, of class Fraction.
     */
    @ParameterizedTest
    @CsvSource({"1, 2, 1, 2, 1, 1", // 1/2 / 1/2 = 1/1
            "3, 4, 1, 2, 3, 2",     // 3/4 / 1/2 = 3/2
            "7, 12, 9, 26, 91, 54", // 7/12 / 9/26 = 91/54
            "-3, 4, 1, 2, -3, 2"    // -3/4 / 1/2 = -3/2
    })
    public void testDivide(int firstNumerator, int firstDenominator,
                           int secondNumerator, int secondDenominator,
                           int expectedNumerator, int expectedDenominator) {
        System.out.println("divide");
        Fraction first = new Fraction(firstNumerator, firstDenominator);
        Fraction second = new Fraction(secondNumerator, secondDenominator);

        Fraction result = first.divide(second);
        assertEquals(expectedNumerator, result.getNumerator());
        assertEquals(expectedDenominator, result.getDenominator());
    }

    @ParameterizedTest
    @CsvSource({"1, 2, 0.5", "5, 4, 1.25"})
    public void testApproximation(int numerator, int denominator, double expected) {
        System.out.println("getApproximation");
        Fraction fraction = new Fraction(numerator, denominator);
        assertEquals(expected, fraction.approximation());

    }

}