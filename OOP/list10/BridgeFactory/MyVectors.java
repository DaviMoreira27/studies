package BridgeFactory;

import java.util.Vector;

public class MyVectors {

    public Vector<Integer> v1;
    public Vector<Integer> v2;

    public MyVectors(Vector<Integer> vo1, Vector<Integer> vo2) {
        this.v1 = vo1;
        this.v2 = vo2;
    }

    public Vector<Integer> executeOperation(VectorOperation vo) {
        return vo.calcOperation(this);
    }
}
