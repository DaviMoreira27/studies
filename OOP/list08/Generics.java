public class Generics<T extends Number> {

    private T data;
    private T value;

    public Generics(T data, T value) {
        this.data = data;
        this.value = value;
    }

    public T getMax() {
        return data.doubleValue() > value.doubleValue() ? data : value;
    }

    @Override
    public String toString() {
        if (getMax() == this.data) {
            return this.data.toString() + " " + this.value.toString();
        }

        return this.value.toString() + " " + this.data.toString();
    }
}
