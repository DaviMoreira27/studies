package Bridge;

public class AddVector implements MathVectorCalcInterface {

    public Double perfomVectorCalc(MathVector v) {
        return v.vector1.firstElement() + v.vector2.firstElement();
    }
}
