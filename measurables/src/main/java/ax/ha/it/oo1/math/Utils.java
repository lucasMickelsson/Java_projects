package ax.ha.it.oo1.math;

/**
 * Utilities.
 *
 * @author joakim
 */
public class Utils {

    public static int gcd(int first, int second) {

        // Recursive implementation of the Euclidean algorithm
        // https://www.khanacademy.org/computing/computer-science/cryptography/modarithmetic/a/the-euclidean-algorithm
        if (first == 0) {
            return second;
        }
        if (second == 0) {
            return first;
        }

        return Math.abs(gcd(second, first % second));
    }
}
