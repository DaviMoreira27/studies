package Bridge;

import java.util.Vector;

public class MathVector {

    public Vector<Double> vector1;
    public Vector<Double> vector2;

    public MathVector(Vector<Double> v1, Vector<Double> v2) {
        this.vector1 = v1;
        this.vector2 = v2;
    }

    /**
       The only function that will be called to perform the operations.

       @param calcFunction - The operation that will be executed using this
       instance data. It is a class that represents an operation to be executed, being limited
       by the interface MathVectorCalcInterface. All the operations must implement this class. All the classes that implements this interface, must have a perfomVectorCalc function, that truly represents the operation. The functions receives the MathVector to access its data.



       @return All  the operations will return a Double value
    */
    public Double perfomVectorCalc(MathVectorCalcInterface calcFunction) {
        return calcFunction.perfomVectorCalc(this);
    }
}
