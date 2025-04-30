import java.io.File;
import java.io.FilenameFilter;

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
            
            // new ListSeven().listFileRecursively(dir);
            
            new ListSeven().listFileRecursivelyWithFilter(dir, filterExecutable);

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
}
