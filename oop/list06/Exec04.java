class NumeroComplexo {
    double parteReal;
    double parteImaginaria;
    
    
    NumeroComplexo(double pr, double pi) {
        this.parteReal = pr;
        this.parteImaginaria = pi;
    }
    
    public double modulo() {
        System.out.println("Modulo");
        return 0.0;
    }
    
    public String toString() {
        String result = "z = " + this.parteReal + " + " + this.parteImaginaria + "i";
        return result;
    }
    
    public NumeroComplexo adicionar(NumeroComplexo num) {
        this.parteReal += num.parteReal;
        this.parteImaginaria += num.parteImaginaria;
        
        return this;
    }
    
    public NumeroComplexo multiplicar(NumeroComplexo num) {
        System.out.println("Multiplificacao Numero Complexo");
        
        return this;
    }
}

interface OperacaoComplexa {
    public NumeroComplexo executar(NumeroComplexo a, NumeroComplexo b);
}

class SomaComplexa implements OperacaoComplexa {
    public NumeroComplexo executar(NumeroComplexo a, NumeroComplexo b) {
        return a.adicionar(b);
    }
}

class MultiplicacaoComplexa implements OperacaoComplexa {
    public NumeroComplexo executar(NumeroComplexo a, NumeroComplexo b) {
        return a.multiplicar(b);
    }
}

class CalculadoraComplexa {
    private OperacaoComplexa op;
    
    public void setOperacao(OperacaoComplexa opp) {
        this.op = opp;
    }
    
    public String calcular(NumeroComplexo a, NumeroComplexo b) {
        NumeroComplexo nc = this.op.executar(a, b);
        
        return nc.toString();
    }
}

class Exec04 {
    public static void main(String[] args) {
        NumeroComplexo c1 = new NumeroComplexo(3, 4);
        NumeroComplexo c2 = new NumeroComplexo(1, -2);
        System.out.println(c1);
        System.out.println(c1.modulo());
        System.out.println(c2);
        System.out.println(c2.modulo());
        CalculadoraComplexa calculadora = new CalculadoraComplexa();
        calculadora.setOperacao(new SomaComplexa());
        String resultadoSoma = calculadora.calcular(c1, c2);
        System.out.println("Soma: " + resultadoSoma);
        calculadora.setOperacao(new MultiplicacaoComplexa());
        String resultadoMultiplicacao = calculadora.calcular(c1, c2);
        System.out.println("Multiplicação: " + resultadoMultiplicacao);
    }
}