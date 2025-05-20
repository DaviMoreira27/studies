import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

            // new ListSeven().inputAndOutputClass();

            // new ListSeven().readingAndSubstitution();

            new ListSeven().orderDirectories(dir);

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

    public void readingAndSubstitution () throws IOException {
        try {
            // InputStream readFile = new BufferedInputStream(new FileInputStream("./exercise05.txt"));
            // int data = readFile.read();
            // String fullText = "";

            // while (data != -1) {
            //     System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));
            //     System.out.print((char) data);
            //     fullText += (char) data;

            //     data = readFile.read();
            // }
            // readFile.close();
            // System.out.println("\nREPLACE");
            
            // String resultedFirst = fullText.replace("muito", "pouco");
            // String resultedFinal = resultedFirst.replace("Muito", "Pouco");

            // System.out.print(resultedFinal);

            String content = new String(Files.readAllBytes(Paths.get("./exercise05.txt")), StandardCharsets.UTF_8);

            content = content.replace("muito", "pouco").replace("Muito", "Pouco");

            System.out.println(content);
        } catch (Exception e) {
            throw e;
        }
    }

    public void orderDirectories (String dir) throws Exception, IOException {
        try {
            boolean isDir = new File(dir).isDirectory();
            
            if (!isDir) {
                throw new Exception("Is not a directory");
            }

            File[] getAll = new File(dir).listFiles();
            List<File> comparable = new ArrayList<File>();
            // String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
            for (File f : getAll) {
                if (f.isDirectory()) {
                    continue;
                }

                if (f.getName().contains("-") || f.getName().matches(".*\\d.*")) {
                    f.delete();
                }

                comparable.add(f);

            }

            comparable.sort(Comparator.comparingLong(File::length));

            for (int i = 0; i < comparable.size(); i++) {
                System.out.printf("%04d-%s\n", i, comparable.get(i).getName());
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
