public class GenericsComparable<T extends Comparable<T>> {

    public T prod1;
    public T prod2;

    public GenericsComparable(T p1, T p2) {
        this.prod1 = p1;
        this.prod2 = p2;
    }

    public T getMax() {
        if (prod1.compareTo(prod2) > 0) {
            return prod1;
        }

        return prod2;
    }
}
