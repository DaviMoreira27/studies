package BridgeFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class VectorUnion implements VectorOperation {

    public Vector<Integer> calcOperation(MyVectors v) {
        Set<Integer> unionSet = new HashSet<>(v.v1);
        unionSet.addAll(v.v2);

        Vector<Integer> unionVector = new Vector<>(unionSet);

        System.out.println("União: " + unionVector);

        return unionVector;
    }
}
