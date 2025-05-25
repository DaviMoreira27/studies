public class Main {

    public static void main(String args[]) {
        // Main.exec01();
        // Main.exec02();
        Main.exec03();
    }

    private static void exec01() {
        Patient p1 = new Patient("Nome 1");
        Patient p2 = new Patient("Nome 2");
        Patient p3 = new Patient("Nome 3");
        Patient p4 = new Patient("Nome 4");
        Patient p5 = new Patient("Nome 5");
        Patient p6 = new Patient("Nome 6");
        Patient p7 = new Patient("Nome 7");
        Patient p8 = new Patient("Nome 8");
        Patient p9 = new Patient("Nome 9");
        Patient p10 = new Patient("Nome 10");

        Consultancy c = new Consultancy();
        c.addPatient(p1);
        c.addPatient(p2);
        c.addPatient(p3);
        c.addPatient(p4);
        c.addPatient(p5);
        c.addPatient(p6);
        c.addPatient(p7);
        c.addPatient(p8);
        c.addPatient(p9);
        c.addPatient(p10);

        c.getAllPatients();

        c.callPatient();

        c.getAllPatients();
    }

    private static void exec02() {
        BalancedParenthesis b = new BalancedParenthesis();

        System.out.print(b.isUnbalanced("()[]{{}}((()))(){{}}"));
    }

    private static void exec03() {
        Call p1 = new Call(CallType.COMMON, "Nome 1");
        Call p2 = new Call(CallType.COMMON, "Nome 2");
        Call p3 = new Call(CallType.COMMON, "Nome 3");
        Call p4 = new Call(CallType.URGENT, "Nome 4");
        Call p5 = new Call(CallType.COMMON, "Nome 5");
        Call p6 = new Call(CallType.URGENT, "Nome 6");
        Call p7 = new Call(CallType.URGENT, "Nome 7");
        Call p8 = new Call(CallType.COMMON, "Nome 8");
        Call p9 = new Call(CallType.COMMON, "Nome 9");
        Call p10 = new Call(CallType.COMMON, "Nome 10");

        DeckCollection c = new DeckCollection();
        c.addCall(p1);
        c.addCall(p2);
        c.addCall(p3);
        c.addCall(p4);
        c.addCall(p5);
        c.addCall(p6);
        c.addCall(p7);
        c.addCall(p8);
        c.addCall(p9);
        c.addCall(p10);

        c.getList();

        c.callPatient();

        c.getList();
    }
}
