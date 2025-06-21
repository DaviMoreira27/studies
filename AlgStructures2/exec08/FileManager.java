import java.io.*;
import java.util.*;

public class FileManager {

    /**
     * Adiciona um novo mangá ao final do arquivo.
     */
    public static void appendManga(String path, Manga manga)
        throws IOException {
        try (
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))
        ) {
            bw.write(formatMangaAsLine(manga));
            bw.newLine();
        }
    }

    /**
     * Lê todos os mangás do arquivo.
     */
    public static List<Manga> readAll(String path) throws IOException {
        List<Manga> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                Manga m = parseMangaFromLine(linha);
                if (m != null) lista.add(m);
            }
        }
        return lista;
    }

    /**
     * Lê um mangá a partir do ISBN, usando índice primário.
     */
    public static Manga readMangaByISBN(String path, String isbn)
        throws IOException {
        Map<String, Long> index = IndexManager.loadPrimaryIndex();
        Long pos = index.get(isbn);
        if (pos == null) return null;

        try (RandomAccessFile raf = new RandomAccessFile(path, "r")) {
            raf.seek(pos);
            String linha = raf.readLine();
            return parseMangaFromLine(linha);
        }
    }

    /**
     * Remove um mangá logicamente sobrescrevendo a linha com marcador especial.
     */
    public static void removeManga(String path, String isbn)
        throws IOException {
        List<Manga> todos = readAll(path);
        boolean removed = false;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Manga m : todos) {
                if (m.getIsbn().equals(isbn)) {
                    removed = true;
                    continue; // pula a linha
                }
                bw.write(formatMangaAsLine(m));
                bw.newLine();
            }
        }

        if (removed) {
            System.out.println("Mangá removido.");
            IndexManager.rebuildIndexes(path); // reconstruir após remoção
        } else {
            System.out.println("ISBN não encontrado.");
        }
    }

    /**
     * Atualiza um mangá sobrescrevendo a linha correspondente.
     */
    public static void updateManga(String path, Manga atualizado)
        throws IOException {
        List<Manga> todos = readAll(path);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Manga m : todos) {
                if (m.getIsbn().equals(atualizado.getIsbn())) {
                    bw.write(formatMangaAsLine(atualizado));
                } else {
                    bw.write(formatMangaAsLine(m));
                }
                bw.newLine();
            }
        }

        IndexManager.rebuildIndexes(path); // reconstruir após edição
    }

    /**
     * Formata um mangá como linha
     */
    private static String formatMangaAsLine(Manga m) {
        return String.join(
            "; ",
            m.getIsbn(),
            m.getTitulo(),
            m.getAutores(),
            m.getAnoInicio(),
            m.getAnoFim(),
            m.getGenero(),
            m.getRevista(),
            m.getEditora(),
            String.valueOf(m.getAnoEdicao()),
            String.valueOf(m.getQtdVolumes()),
            String.valueOf(m.getQtdVolumesAdquiridos()),
            m.getVolumesAdquiridos().toString()
        );
    }

    /**
     * Converte uma linha de texto para um objeto Manga.
     */
    private static Manga parseMangaFromLine(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 12) return null;

        Manga manga = new Manga();

        manga.setIsbn(partes[0].trim());
        manga.setTitulo(partes[1].trim());
        manga.setAutores(partes[2].trim());
        manga.setAnoInicio(partes[3].trim());
        manga.setAnoFim(partes[4].trim());
        manga.setGenero(partes[5].trim());
        manga.setRevista(partes[6].trim());
        manga.setEditora(partes[7].trim());
        manga.setAnoEdicao(partes[8].trim());
        manga.setQtdVolumes(Integer.parseInt(partes[9].trim()));
        manga.setQtdVolumesAdquiridos(Integer.parseInt(partes[10].trim()));

        String listaStr = partes[11].replaceAll("[\\[\\]]", "").trim();
        List<Integer> volumes = new ArrayList<>();
        if (!listaStr.isEmpty()) {
            for (String s : listaStr.split(",")) {
                volumes.add(Integer.parseInt(s.trim()));
            }
        }
        manga.setVolumesAdquiridos(volumes);

        return manga;
    }
}
