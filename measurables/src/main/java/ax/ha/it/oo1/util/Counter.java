package ax.ha.it.oo1.util;

/**
 * This is a Counter class that has a counter, upper and lower limit
 *
 * @author lucas mickelsson
 */
public abstract class Counter implements Measurable {
    private final int lowerLimit;
    private final int upperLimit;
    protected int count;

    protected Counter(int count, int lowerLimit, int upperLimit) {
        if (lowerLimit > upperLimit) {
            throw new IllegalArgumentException("Lower is bigger than upper: " + lowerLimit);
        } else if (count < lowerLimit) {
            throw new IllegalArgumentException("Count is smaller than lower: " + count);
        } else if (count > upperLimit) {
            throw new IllegalArgumentException("Count is bigger than upper: " + count);
        }
        this.count = count;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    /**
     * This method is a getter for field count
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * This method is returning true or false based on count and lowerlimit
     *
     * @return true if count is equal to lowerlimit, false otherwise
     */
    public boolean atLowerLimit() {
        return (count == lowerLimit);
    }

    /**
     * This method is returning true or false based on count and upperlimit
     *
     * @return true if count is equal to upperlimit, false otherwise
     */
    public boolean atUpperLimit() {
        return (count == upperLimit);
    }

    /**
     * This is a getter for lowerlimit
     *
     * @return the current lowerlimit
     */
    public int getLowerLimit() {
        return lowerLimit;
    }

    /**
     * This is a getter for upperlimit
     *
     * @return the current upperlimit
     */
    public int getUpperLimit() {
        return upperLimit;
    }

    /**
     * This is a getter for count
     *
     * @return the value of count
     */
    @Override
    public double getValue() {
        return count;
    }

    /**
     * This is an abstract method for increment of value count which will get implemented
     */
    public abstract void increment();

    /**
     * This is an abstract method for decrement of value count which will get implemented
     */
    public abstract void decrement();
}
