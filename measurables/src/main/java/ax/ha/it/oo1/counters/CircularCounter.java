package ax.ha.it.oo1.counters;

import ax.ha.it.oo1.util.Counter;

/**
 * This is a subclass to Counter named CircularCounter
 *
 * @author lucas mickelsson
 */
public class CircularCounter extends Counter {

    /**
     * This is a constructor which uses the constructor from the baseclass Counter
     *
     * @param count      the value of count
     * @param lowerLimit the value of lower should be smaller than count and upper
     * @param upperLimit the value of upper should be bigger than lower and count
     */
    public CircularCounter(int count, int lowerLimit, int upperLimit) {
        super(count, lowerLimit, upperLimit);
    }

    /**
     * This is an implementation of abstract method increment.
     * It increases count as long its not equal to upper.
     * it will set the count value to lowerlimit value if its equal to upper
     */
    @Override
    public void increment() {
        if (!atUpperLimit()) {
            count++;
        } else {
            count = getLowerLimit();
        }
    }

    /**
     * This is an implementation of abstract method decrement
     * It increases count as long its not equal to lower
     * it will set the count value to upperlimit value if its equal to lower
     */
    @Override
    public void decrement() {
        if (!atLowerLimit()) {
            count--;
        } else {
            count = getUpperLimit();
        }
    }

}
