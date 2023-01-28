package ax.ha.it.oo1.math;

import ax.ha.it.oo1.util.Measurable;

public class MeasurableFraction implements Measurable {

    private Fraction fraction;

    public MeasurableFraction(int numerator, int denominator) {
        fraction = new Fraction(numerator, denominator);
    }

    public MeasurableFraction(int numb) {
        fraction = new Fraction(numb);
    }

    @Override
    public double getValue() {
        return fraction.approximation();
    }
}
