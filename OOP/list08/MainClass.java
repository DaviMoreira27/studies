public class MainClass {

    public static void main(String args[]) {
        // MainClass.exec01();
        // MainClass.exec02();
        MainClass.exec03();
    }

    public static void exec01() {
        System.out.println("Exec 01\n");
        Generics<Integer> gs = new Generics<Integer>(2, 8);

        System.out.format("Maximum Value: %d\n", gs.getMax());
        System.out.format("String Value: %s\n", gs.toString());
        System.out.println("");
    }

    public static void exec02() {
        System.out.println("Exec 02\n");
        Product p1 = new Product("Product 01", 50.69);
        Product p2 = new Product("Product 02", 85.34);
        GenericsComparable<Product> gc = new GenericsComparable<Product>(
            p1,
            p2
        );

        System.out.format("Most Expensive Product: %s\n", gc.getMax().nome);
        System.out.println("");
    }

    public static void exec03() {
        System.out.println("Exec 03\n");
        TimeFutebol t1 = new TimeFutebol("Time A", 50);
        TimeFutebol t2 = new TimeFutebol("Time Y", 23);
        TimeFutebol t3 = new TimeFutebol("Time D", 13);
        TimeFutebol t4 = new TimeFutebol("Time K", 53);
        TimeFutebol t5 = new TimeFutebol("Time E", 52);
        TimeFutebol t6 = new TimeFutebol("Time L", 120);
        TimeFutebol[] list = { t1, t2, t3, t4, t5, t6 };
        System.out.println("Before ordering");
        for (TimeFutebol t : list) {
            System.out.println(t.nome);
        }

        list = TimeFutebol.championshipTimes(list);

        System.out.println("After ordering");
        for (TimeFutebol t : list) {
            System.out.println(t.nome);
        }
        System.out.println("");
    }
}
