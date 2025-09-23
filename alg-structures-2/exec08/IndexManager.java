import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class IndexManager {

    public static void buildIndexesFromTxt() {
        if (!shouldBuildIndexes()) return;
        rebuildIndexes(Consts.DATA);
    }

    public static void rebuildIndexes(String txtPath) {
        Map<String, Long> primaryIndex = new LinkedHashMap<>();
        Map<String, List<String>> secondaryIndex = new LinkedHashMap<>();

        try (RandomAccessFile raf = new RandomAccessFile(txtPath, "r")) {
            String line;
            while (raf.getFilePointer() < raf.length()) {
                long pos = raf.getFilePointer();
                line = raf.readLine();

                if (line == null || line.trim().isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length < 2) continue;

                String isbn = parts[0].trim();
                String titulo = parts[1].trim();

                primaryIndex.put(isbn, pos);
                secondaryIndex
                    .computeIfAbsent(titulo, k -> new ArrayList<>())
                    .add(isbn);
            }

            savePrimaryIndex(primaryIndex);
            saveSecondaryIndex(secondaryIndex);

            System.out.println("Índices reconstruídos com sucesso.");
        } catch (IOException e) {
            System.err.println(
                "Erro ao reconstruir índices: " + e.getMessage()
            );
        }
    }

    private static boolean shouldBuildIndexes() {
        File primary = new File(Consts.PRIMARY_INDEX);
        File secondary = new File(Consts.SECONDARY_INDEX);
        try {
            return (
                !primary.exists() ||
                !secondary.exists() ||
                Files.size(primary.toPath()) == 0 ||
                Files.size(secondary.toPath()) == 0
            );
        } catch (IOException e) {
            return true;
        }
    }

    // ======= ÍNDICE PRIMÁRIO =======

    public static Map<String, Long> loadPrimaryIndex() {
        Map<String, Long> index = new LinkedHashMap<>();
        try (
            BufferedReader br = new BufferedReader(
                new FileReader(Consts.PRIMARY_INDEX)
            )
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length == 2) {
                    index.put(parts[0], Long.parseLong(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println(
                "Erro ao carregar índice primário: " + e.getMessage()
            );
        }
        return index;
    }

    public static void savePrimaryIndex(Map<String, Long> index) {
        try (
            BufferedWriter bw = new BufferedWriter(
                new FileWriter(Consts.PRIMARY_INDEX)
            )
        ) {
            for (Map.Entry<String, Long> entry : index.entrySet()) {
                bw.write(entry.getKey() + "::" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println(
                "Erro ao salvar índice primário: " + e.getMessage()
            );
        }
    }

    public static void addPrimaryIndex(String isbn, long pos) {
        Map<String, Long> index = loadPrimaryIndex();
        index.put(isbn, pos);
        savePrimaryIndex(index);
    }

    public static void removePrimaryIndex(String isbn) {
        Map<String, Long> index = loadPrimaryIndex();
        index.remove(isbn);
        savePrimaryIndex(index);
    }

    public static boolean existsISBN(String isbn) {
        return loadPrimaryIndex().containsKey(isbn);
    }

    // ======= ÍNDICE SECUNDÁRIO =======

    public static Map<String, List<String>> loadSecondaryIndex() {
        Map<String, List<String>> index = new LinkedHashMap<>();
        try (
            BufferedReader br = new BufferedReader(
                new FileReader(Consts.SECONDARY_INDEX)
            )
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length == 2) {
                    String titulo = parts[0].toLowerCase();
                    String[] isbns = parts[1].split(",");
                    index.put(titulo, Arrays.asList(isbns));
                }
            }
        } catch (IOException e) {
            System.err.println(
                "Erro ao carregar índice secundário: " + e.getMessage()
            );
        }
        return index;
    }

    public static void saveSecondaryIndex(Map<String, List<String>> index) {
        try (
            BufferedWriter bw = new BufferedWriter(
                new FileWriter(Consts.SECONDARY_INDEX)
            )
        ) {
            for (Map.Entry<String, List<String>> entry : index.entrySet()) {
                bw.write(
                    entry.getKey() + "::" + String.join(",", entry.getValue())
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println(
                "Erro ao salvar índice secundário: " + e.getMessage()
            );
        }
    }

    public static void addSecondaryIndex(String titulo, String isbn) {
        Map<String, List<String>> index = loadSecondaryIndex();
        index.computeIfAbsent(titulo, k -> new ArrayList<>()).add(isbn);
        saveSecondaryIndex(index);
    }

    public static void removeSecondaryIndex(String titulo, String isbn) {
        Map<String, List<String>> index = loadSecondaryIndex();
        List<String> isbns = index.get(titulo);
        if (isbns != null) {
            isbns.remove(isbn);
            if (isbns.isEmpty()) {
                index.remove(titulo);
            }
            saveSecondaryIndex(index);
        }
    }

    public static List<String> getISBNsByTitle(String titulo) {
        return loadSecondaryIndex().getOrDefault(titulo, new ArrayList<>());
    }
}
