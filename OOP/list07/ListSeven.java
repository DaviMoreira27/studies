import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ListSeven {
    public static void main (String[] args) {
        String dir = "./";

        try {

            FilenameFilter filterExecutable = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".exe");
                }
            };

            System.out.println(filterExecutable.getClass() + " " + dir);
            
            // new ListSeven().listFileRecursively(dir);
            
            // new ListSeven().listFileRecursivelyWithFilter(dir, filterExecutable);

            new ListSeven().byteStringOutput("Davi");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void listFileRecursively(String directory) throws Exception {
        try {
            Boolean isDir = new File(directory).isDirectory();

            if (!isDir) {
                throw new Exception("Is not a directory!");
            }
            
            File[] getAllFiles = new File(directory).listFiles();

            for (File file : getAllFiles) {

                if (file.isDirectory()) {
                    listFileRecursively(file.getPath());
                }

                System.out.format("Nome do Arquivo: %s \n", file.getPath());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void listFileRecursivelyWithFilter(String directory, FilenameFilter filter) throws Exception {
        try {   
            Boolean isDir = new File(directory).isDirectory();

            if (!isDir) {
                throw new Exception("Is not a directory!");
            }
            
            File[] getAllFiles = new File(directory).listFiles(filter);

            for (File file : getAllFiles) {

                if (file.isDirectory()) {
                    listFileRecursively(file.getPath());
                }

                System.out.format("Nome do Arquivo: %s \n", file.getPath());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void byteStringOutput (String text) throws IOException {
        try {
            ByteArrayInputStream string = new ByteArrayInputStream(text.getBytes());

            File newFile = new File("./exercise03.txt");
            FileOutputStream newStream = new FileOutputStream(newFile);

            string.transferTo(newStream);
            newStream.close();
            string.close();
        } catch (IOException e) {
            throw e;
        }
    }

    public void inputAndOutputClass () throws IOException, SecurityException {
        try {
            Endereco addr = new Endereco("Rua Tal", "Numero Tal", "Bairro Tal");
            DadosPessoais dp = new DadosPessoais(addr, 125, "Davi");

            ObjectOutputStream obj = new ObjectOutputStream();
        } catch (Exception e) {
            throw e;
        }
    }
}
