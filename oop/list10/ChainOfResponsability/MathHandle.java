package ChainOfResponsability;

public abstract class MathHandle {

    protected MathHandle nextHanler;

    public void setNext(MathHandle next) {
        this.nextHanler = next;
    }

    public abstract void handle(int number);
}
