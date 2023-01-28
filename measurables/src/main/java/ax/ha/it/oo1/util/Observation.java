package ax.ha.it.oo1.util;

import java.time.LocalDate;

public class Observation implements Chronological, Measurable {
    private final double amount;
    private final LocalDate date;

    public Observation(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public double getValue() {
        return amount;
    }

    public double getAmount() {
        return amount;
    }

}
