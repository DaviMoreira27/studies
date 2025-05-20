/**
 *
 * @author Jose F Rodrigues-Jr
 */
import java.io.*;

public class Main {

    public static void main(String[] args) {
        String nomeArquivo = "exemplo.txt";
        try (
            FileInputStream fis = new FileInputStream(nomeArquivo);  
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");  
            BufferedReader br = new BufferedReader(isr);  
            MeuDecorador meuReader = new MeuDecorador(br))
        {           
            String linha;
            while ((linha = meuReader.readLine()) != null)
                System.out.println(linha);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}

