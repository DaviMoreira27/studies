package ChainOfResponsability;

public class ThreeOperation extends MathHandle {

    public void handle(int num) {
        if (num % 2 != 0) {
            System.out.println("Is odd");
        } else {
            this.nextHanler.handle(num);
        }
    }
}
