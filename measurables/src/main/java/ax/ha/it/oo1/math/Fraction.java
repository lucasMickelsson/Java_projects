package ax.ha.it.oo1.math;

/**
 * Represents a fraction
 *
 * @author Joakim
 */
public final class Fraction {

    private final int numerator;
    private final int denominator;

    public Fraction(int numerator, int denominator) {

        if (denominator == 0) {
            // Need to be handled through an Exception, we will get
            // back to this later on and simply ignore the problem for now
            // => We will crash and burn trying to set denominator = 0
        }

        if (denominator < 0) {
            // Always put minus sign on numerator
            // Also takes care of the case when both numerator and
            // denominator are negative => make both positive
            numerator = -numerator;
            denominator = -denominator;
        }

        int gcd = Utils.gcd(numerator, denominator);
        this.numerator = numerator/gcd;
        this.denominator = denominator/gcd;

    }

    public Fraction(int integer) {
        this.numerator = integer;
        this.denominator = 1;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public Fraction add(Fraction other) {
        int sumNumerator = numerator * other.denominator + denominator * other.numerator;
        int sumDenominator = denominator * other.denominator;

        return new Fraction(sumNumerator, sumDenominator);
    }

    public Fraction subtract(Fraction other) {
        int diffNumerator = numerator * other.denominator - denominator * other.numerator;
        int diffDenominator = denominator * other.denominator;

        return new Fraction(diffNumerator, diffDenominator);
    }

    public Fraction multiply(Fraction other) {
        int productNumerator = numerator * other.numerator;
        int productDenominator = denominator * other.denominator;

        return new Fraction(productNumerator, productDenominator);
    }

    public Fraction divide(Fraction other) {
        Fraction reciprocal = new Fraction(other.denominator, other.numerator);
        return this.multiply(reciprocal);
    }

    public double approximation() {
        return (double) numerator / denominator;
    }

}