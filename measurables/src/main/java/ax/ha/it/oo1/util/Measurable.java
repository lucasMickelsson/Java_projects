package ax.ha.it.oo1.util;

import java.util.Comparator;

public interface Measurable {
    double getValue();

    static int countNumOfElements(Measurable[] measurable) {
        int counter = 0;
        for (Measurable value : measurable) {
            if (value != null) {
                counter++;
            }
        }
        return counter;
    }

    static double getAverageOf(Measurable[] list) {
        if (list == null) {
            throw new StatisticsException("The array is empty!");
        }
        double average, sum = 0;
        int num = countNumOfElements(list);
        for (int i = 0; i < num; i++) {
            sum += list[i].getValue();
        }
        average = sum / list.length;
        return average;
    }

    static Measurable getMinimumOf(Measurable[] list) {
        if (list == null) {
            throw new StatisticsException("The array is empty!");
        }
        int num = countNumOfElements(list);
        Measurable min = list[0];
        for (int i = 0; i < num; i++) {
            if (list[i].getValue() < min.getValue()) {
                min = list[i];
            }
        }
        return min;
    }

    static Measurable getMaximumOf(Measurable[] list) {
        if (list == null) {
            throw new StatisticsException("The array is empty!");
        }
        int num = countNumOfElements(list);
        Measurable max = list[0];
        for (int i = 0; i < num; i++) {
            if (list[i].getValue() > max.getValue()) {
                max = list[i];
            }
        }
        return max;
    }
}
