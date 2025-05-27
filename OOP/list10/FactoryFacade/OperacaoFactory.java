package FactoryFacade;

public class OperacaoFactory {

    private Operacao op;

    public Operacao createOperation(TipoOperacao type)
        throws ClassNotFoundException {
        switch (type) {
            case SUM:
                op = new Sum();
                return op;
            case MULTI:
                op = new Multi();
                return op;
            case SUB:
                op = new Sub();
                return op;
            default:
                throw new ClassNotFoundException();
        }
    }
}
