package BridgeFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class VectorIntersection implements VectorOperation {

    public Vector<Integer> calcOperation(MyVectors v) {
        Set<Integer> set1 = new HashSet<Integer>(v.v1);
        Set<Integer> set2 = new HashSet<Integer>(v.v2);

        set1.retainAll(set2);

        System.out.println("Interseção: " + set1);
        return new Vector<Integer>(set1);
    }
}
