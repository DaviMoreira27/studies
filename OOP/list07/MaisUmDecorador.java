import java.io.IOException;
import java.io.Reader;

public class MaisUmDecorador extends MeuDecorador{
    private int linhaAtual = 1;

    public MaisUmDecorador(Reader reader) {
        super(reader);
    }

    @Override
    public String readLine() throws IOException {
        String linhaDecorada = super.readLine();
        if (linhaDecorada == null) return null;
        return linhaDecorada + " [" + (linhaAtual++) + "]";
    }
}
