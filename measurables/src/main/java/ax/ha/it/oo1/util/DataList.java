package ax.ha.it.oo1.util;


public class DataList<E extends Measurable> {
    private Object[] elements;
    private final int allocationStrategy;
    private int noOfElements;

    public DataList(int allocationStrategy) {
        if (allocationStrategy > 0) {
            this.elements = new Object[allocationStrategy];
            this.allocationStrategy = allocationStrategy;
        } else {
            throw new RuntimeException("Illegal capacity: " + allocationStrategy);
        }
    }

    public void insert(E measurable, int index) {
        if (!(index < 0 || index > noOfElements)) {
            if (noOfElements >= elements.length) {
                elements = increaseArray();
            }
            for (int i = elements.length - 1; i > index; i--) {
                elements[i] = elements[i - 1];
            }
            elements[index] = measurable;
            noOfElements++;
        } else {
            throw new ArrayIndexOutOfBoundsException("Index is outside of array when inserting an element: " + index);
        }
    }

    private Object[] increaseArray() {
        Object[] newElements = new Object[elements.length + allocationStrategy];
        if (allocationStrategy >= 0) {
            System.arraycopy(elements, 0, newElements, 0, noOfElements);
        }
        return newElements;
    }

    private Object[] decreaseArray() {
        int freePos = 0;
        for (Object element : elements) {
            if (element == null) {
                freePos++;
            }
        }
        if (allocationStrategy * 2 == freePos) {
            Object[] newElements = new Object[elements.length - allocationStrategy];
            System.arraycopy(elements, 0, newElements, 0, allocationStrategy);
            return newElements;
        } else {
            return elements;
        }
    }

    public void append(E measurable) {
        if (noOfElements >= elements.length) {
            elements = increaseArray();
        }
        elements[noOfElements] = measurable;
        noOfElements++;
    }

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index < 0 || index >= elements.length) {
            throw new ArrayIndexOutOfBoundsException("Index outside of array when removing an element: " + index);
        }
        E measurableIndex = (E) elements[index];
        for (int i = index; i < elements.length - 1; i++) {
            elements[i] = elements[i + 1];
        }
        noOfElements--;

        elements = decreaseArray();

        return measurableIndex;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= noOfElements) {
            throw new ArrayIndexOutOfBoundsException("Index outside of array when getting element: " + index);
        }
        return (E) elements[index];
    }

    public int size() {
        return noOfElements;
    }

    public double average() {
        Measurable[] measurables = new Measurable[elements.length];
        for (int i = 0; i < elements.length; i++) {
            measurables[i] = (Measurable) elements[i];
        }
        return Measurable.getAverageOf(measurables);
    }

    public Measurable maximum() {
        Measurable[] measurables = new Measurable[elements.length];
        for (int i = 0; i < elements.length; i++) {
            measurables[i] = (Measurable) elements[i];
        }
        return Measurable.getMaximumOf(measurables);
    }

    public Measurable minimum() {
        Measurable[] measurables = new Measurable[elements.length];
        for (int i = 0; i < elements.length; i++) {
            measurables[i] = (Measurable) elements[i];
        }
        return Measurable.getMinimumOf(measurables);
    }


}
