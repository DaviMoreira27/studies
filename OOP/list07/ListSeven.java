import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

            // new ListSeven().byteStringOutput("Davi");

            new ListSeven().inputAndOutputClass();

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

    public void inputAndOutputClass () throws IOException, SecurityException, ClassNotFoundException {
        try {
            Endereco addr = new Endereco("Rua Tal", "Numero Tal", "Bairro Tal");
            DadosPessoais dp = new DadosPessoais(addr, 125, "Davi");

            /*
             * Cria um FileOutputStream que armazenará os dados
             */
            FileOutputStream newFile = new FileOutputStream("exercise04.gz");
            /*
             * Crio uma stream entre o newFile e o zipFile, tudo que for escrito
             * no newFile, será automaticamente compactado para Gzip
             */
            GZIPOutputStream zipFile = new GZIPOutputStream(newFile);
            /*
             * Envolve o GZIPOutputStream com um ObjectOutputStream que permite escrever
             * objetos Java (desde que sejam Serializable) em um fluxo de bytes. Neste caso,
             * os bytes gerados são comprimidos e salvos no arquivo.
             */
            ObjectOutputStream objOut = new ObjectOutputStream(zipFile);

            /*
             * O objeto dp é convertido em um array de bytes serializados que passo por
             * ObjectOutputStream, transformando o objeto em bytes -> GZIPOutputStream,
             * comprime os bytes gerados -> FileOutputStream, grava os bytes comprimidos no
             * arquivo exercise04.gz
             */
            objOut.writeObject(dp);;

            objOut.close();

            // Caminho inverso
            FileInputStream fileIn = new FileInputStream("exercise04.gz");
            GZIPInputStream gzipIn = new GZIPInputStream(fileIn);
            ObjectInputStream objIn = new ObjectInputStream(gzipIn);

            DadosPessoais dpLido = (DadosPessoais) objIn.readObject();

            System.out.printf("IDADE: %d", dpLido.idade);

            objIn.close();
        } catch (Exception e) {
            throw e;
        }
    }
}
