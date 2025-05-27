package Decorator;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
    The decorator pattern allow us to add new functionality to an object dynamically without modifying the original code. This is achieved by creating decorator classes that wrap the original object and add new behaviors or attributes
*/

public class VogalCounterInputStream extends FilterInputStream {

    private int totalVogal;

    public VogalCounterInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int byteLido = super.read();

        if (byteLido != -1) {
            char caractere = (char) byteLido;
            if ("aeiouAEIOU".indexOf(caractere) != -1) {
                totalVogal++;
            }
        }

        return byteLido;
    }

    public int getTotalVogaisLidas() {
        return this.totalVogal;
    }
}
