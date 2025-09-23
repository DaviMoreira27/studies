package ChainOfResponsability;

public class TwoOperation extends MathHandle {

    public void handle(int num) {
        if (num % 2 == 0) {
            System.out.println("Is pair");
        } else {
            this.nextHanler.handle(num);
        }
    }
}
