/**
 *
 * @author Jose F Rodrigues-Jr
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
public class MeuDecorador extends Reader {
    protected BufferedReader br;

    public MeuDecorador(Reader reader) {
        this.br = new BufferedReader(reader);
    }
    // Implementação personalizada de readLine
    public String readLine() throws IOException {
        String linha = br.readLine();
        if (linha == null) return null;
        return ">> " + linha.toUpperCase();  // Exemplo: prefixa e deixa em MAIÚSCULAS
    }

    // Métodos obrigatórios do Reader (não decorados neste caso)
    public int read(char[] cbuf, int off, int len) throws IOException {
        return br.read(cbuf, off, len);
    }
    public void close() throws IOException {
        br.close();
    }
}
