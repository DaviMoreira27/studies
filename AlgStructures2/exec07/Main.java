package exec07;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        RegisterManager gerenciador = new RegisterManager("registers.txt");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String linha;
            while ((linha = reader.readLine()) != null && !linha.isEmpty()) {
                String[] partes = linha.split(" ");
                switch (partes[0]) {
                    case "ADD":
                        gerenciador.addIndex(partes[1], partes[2], partes[3]);
                        break;
                    case "DEL":
                        gerenciador.registerRemover(Integer.parseInt(partes[1]));
                        break;
                    case "LIST":
                        gerenciador.listAll();
                        break;
                    case "COMPACT":
                        gerenciador.compact();
                        break;
                    default:
                        System.out.println("Unknown command.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            gerenciador.deleteFile();
        }
    }
}