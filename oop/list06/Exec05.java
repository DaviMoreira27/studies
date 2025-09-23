interface Comparavel {
    public int comparaCom(Object ot);
}

class Algoritmo {
    public static void bubbleSort(Object[] array) {
        int n = array.length;
        boolean trocou;
        do {
            trocou = false;
            for (int i = 0; i < n - 1; i++) {
                Comparavel atual = (Comparavel) array[i];
                Comparavel proximo = (Comparavel) array[i + 1];
                if (atual.comparaCom(proximo) > 0) {
                    Object temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    trocou = true;
                }
            }
            n--;
        } while (trocou);
    }
}

class TimeFutebol implements Comparavel {
    public int numeroVitorias;
    public int numeroDerrotas;
    public int numeroEmpates;

    public int pontosGanhos;

    TimeFutebol(int nv, int nd, int ne) {
        this.numeroVitorias = nv;
        this.numeroDerrotas = nd;
        this.numeroEmpates = ne;

        this.pontosGanhos = this.numeroVitorias * 3 + this.numeroEmpates;
    }

    public int comparaCom(Object tf) {
        TimeFutebol tfa = (TimeFutebol) tf;
        if (this.pontosGanhos > tfa.pontosGanhos) {
            return 1;
        } else if (this.pontosGanhos < tfa.pontosGanhos) {
            return -1;
        } else {
            return 0;
        }
    }
}

public class Exec05 {
    public static void main(String[] args) {
        TimeFutebol[] times = new TimeFutebol[5];

        times[0] = new TimeFutebol(10, 2, 3);
        times[1] = new TimeFutebol(8, 4, 3);
        times[2] = new TimeFutebol(12, 1, 2);
        times[3] = new TimeFutebol(6, 5, 4);
        times[4] = new TimeFutebol(9, 3, 3);

        Algoritmo.bubbleSort(times);

        for (int i = 0; i < times.length; i++) {
            System.out.println("Time " + (i + 1) + ": " + times[i].pontosGanhos + " pontos");
        }
    }
}
