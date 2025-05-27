import Bridge.*;
import BridgeFactory.BridgeFactory;
import BridgeFactory.MyVectors;
import BridgeFactory.VectorIntersection;
import BridgeFactory.VectorOperationsEnum;
import BridgeFactory.VectorUnion;
import ChainOfResponsability.MathHandle;
import ChainOfResponsability.ThreeOperation;
import ChainOfResponsability.TwoOperation;
import Decorator.VogalCounterInputStream;
import FactoryFacade.CalculadoraFacade;
import FactoryFacade.OperationNotSupportedException;
import Observer.Newsletter;
import Observer.User;
import Singleton.Login;
import Singleton.Payment;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        // Main.exec01();
        // Main.exec05();
        Main.exec06();
    }

    public static void exec01() {
        Vector<Double> v = new Vector<Double>(3, 10);

        v.add(0, 2.9);
        v.add(1, 3.0);

        Vector<Double> v1 = new Vector<Double>(3, 10);

        v1.add(0, 1.5);
        v1.add(1, 3.2);
        MathVector mv = new MathVector(v, v1);

        System.out.println(mv.perfomVectorCalc(new AddVector()));
        System.out.println(mv.perfomVectorCalc(new MultiplyVector()));
    }

    public static void exec02() throws FileNotFoundException, IOException {
        FileInputStream f = new FileInputStream("zipfile.zip");
        ZipInputStream zip = new ZipInputStream(f);
        zip.getNextEntry();
        VogalCounterInputStream vc = new VogalCounterInputStream(zip);

        System.out.println(vc.read());
        System.out.println("Zip File");
        int d;
        while ((d = vc.read()) != -1) {
            char caractere = (char) d;
            System.out.print(caractere);

            if (caractere == 'x') {
                break;
            }
        }

        System.out.println("Digite caracteres. Digite 'x' para sair.");

        VogalCounterInputStream in = new VogalCounterInputStream(System.in);

        int c;
        while ((c = in.read()) != -1) {
            char caractere = (char) c;
            System.out.print(caractere);

            if (caractere == 'x') {
                break;
            }
        }

        System.out.println(
            "\nTotal de vogais digitadas: " + in.getTotalVogaisLidas()
        );
        vc.close();
    }

    public static void exec04()
        throws ClassNotFoundException, OperationNotSupportedException {
        CalculadoraFacade c = new CalculadoraFacade();

        System.out.format("O resultado é: %2.f", c.calcularExpressao("8 + 9"));
    }

    public static void exec05() {
        Payment p = new Payment();
        Login l = new Login();

        p.payProduct();
        l.loginUser();
        p.refundProduct();
        l.userError();
        p.paymentError();
    }

    public static void exec06() {
        Newsletter newsletter = new Newsletter();
        User u1 = new User("Davi");
        User u2 = new User("João");

        newsletter.addObserver(u1);
        newsletter.addObserver(u2);
        newsletter.sendSpecificContent(u2);
        newsletter.publishContent();
        newsletter.removeObserver(u2);
        newsletter.publishContent();
    }

    public static void exec07() throws ClassNotFoundException {
        Vector<Integer> vo1 = new Vector<>(List.of(1, 2, 3, 4));
        Vector<Integer> vo2 = new Vector<>(List.of(2, 4, 6, 8));

        MyVectors nv = new MyVectors(vo1, vo2);

        // Without Factory

        nv.executeOperation(new VectorUnion());
        nv.executeOperation(new VectorIntersection());

        // With Factory

        nv.executeOperation(
            new BridgeFactory().create(VectorOperationsEnum.UNION)
        );
    }

    // 08

    // 10

    public static void exec11() {
        TwoOperation tw = new TwoOperation();
        ThreeOperation te = new ThreeOperation();

        tw.setNext(te);
    }
}
