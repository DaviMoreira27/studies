public class Product implements Comparable<Product> {

    public String nome;
    public double preco;

    public Product(String n, double p) {
        this.nome = n;
        this.preco = p;
    }

    @Override
    public int compareTo(Product p) {
        if (this.preco > p.preco) {
            return 1;
        }

        if (this.preco == p.preco) {
            return 0;
        }

        return -1;
        /*
            return Double.compare(this.preco, p.preco);
        */
    }
}
