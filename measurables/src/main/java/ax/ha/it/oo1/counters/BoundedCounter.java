package ax.ha.it.oo1.counters;

import ax.ha.it.oo1.util.Counter;

/**
 * This is a subclass to class Counter named BoundedCounter
 *
 * @author lucas mickelsson
 */
public class BoundedCounter extends Counter {
    /**
     * This is a constructor which uses the constructor from the baseclass Counter
     *
     * @param count      the value of count
     * @param lowerLimit the value of lower should be smaller than count and upper
     * @param upperLimit the value of upper should be bigger than lower and count
     */
    public BoundedCounter(int count, int lowerLimit, int upperLimit) {
        super(count, lowerLimit, upperLimit);
    }

    /**
     * This is an implementation of abstract method increment.
     * The method is increasing the count value as long it's not equal to upperlimit
     */
    @Override
    public void increment() {
        if (!atUpperLimit()) {
            count++;
        }
    }

    /**
     * This is an implementation of abstract method decrement.
     * The method is increasing the count value as long it's not equal to lowerlimit
     */
    @Override
    public void decrement() {
        if (!atLowerLimit()) {
            count--;
        }
    }
}
