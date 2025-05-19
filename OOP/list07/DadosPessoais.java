import java.io.Serializable;

public class DadosPessoais implements Serializable{
    private Endereco address;
    public int idade;
    public String nome;

    public DadosPessoais(Endereco address, int idade, String nome) {
        this.address = address;
        this.idade = idade;
        this.nome = nome;
    }

    public Endereco getAddress() {
        return address;
    }
    public void setAddress(Endereco address) {
        this.address = address;
    }
}

class Endereco implements Serializable {
    private String rua;
    private String numero;
    private String bairro;

    public Endereco(String rua, String numero, String bairro) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
    }
    
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}