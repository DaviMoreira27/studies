package FactoryFacade;

public class CalculadoraFacade {

    private double calcular(String operacao, double a, double b)
        throws ClassNotFoundException, OperationNotSupportedException {
        switch (operacao) {
            case "+":
                return new OperacaoFactory()
                    .createOperation(TipoOperacao.SUM)
                    .calcular(a, b);
            case "-":
                return new OperacaoFactory()
                    .createOperation(TipoOperacao.SUB)
                    .calcular(a, b);
            case "*":
                return new OperacaoFactory()
                    .createOperation(TipoOperacao.MULTI)
                    .calcular(a, b);
            default:
                throw new OperationNotSupportedException();
        }
    }

    public double calcularExpressao(String expressao)
        throws ClassNotFoundException, OperationNotSupportedException {
        String[] elementos = expressao.trim().split(" ");

        return this.calcular(
                elementos[1],
                Double.parseDouble(elementos[0]),
                Double.parseDouble(elementos[2])
            );
    }
}
