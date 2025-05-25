public class TimeFutebol implements Comparable<TimeFutebol> {

    public String nome;
    public double points;

    public TimeFutebol(String n, double p) {
        this.nome = n;
        this.points = p;
    }

    public int compareTo(TimeFutebol t) {
        return this.nome.compareTo(t.nome);
    }

    public static TimeFutebol[] championshipTimes(TimeFutebol[] t) {
        InsertionSort<TimeFutebol> algo = new InsertionSort<TimeFutebol>();

        return algo.insertionSort(t);
    }
}
