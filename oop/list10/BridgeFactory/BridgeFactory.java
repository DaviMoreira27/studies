package BridgeFactory;

public class BridgeFactory {

    public VectorOperation create(VectorOperationsEnum oe)
        throws ClassNotFoundException {
        switch (oe) {
            case UNION:
                return new VectorUnion();
            case INTERSECTION:
                return new VectorIntersection();
            default:
                throw new ClassNotFoundException();
        }
    }
}
