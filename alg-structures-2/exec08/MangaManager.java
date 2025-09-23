import java.io.File;
import java.io.IOException;
import java.util.*;

public class MangaManager {

    private static final Scanner sc = new Scanner(System.in);

    public MangaManager() {
        ensureFileExists(Consts.DATA);
        ensureFileExists(Consts.PRIMARY_INDEX);
        ensureFileExists(Consts.SECONDARY_INDEX);
        IndexManager.buildIndexesFromTxt();
    }

    private void ensureFileExists(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();
                System.out.println("Criado: " + path);
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar " + path + ": " + e.getMessage());
            System.exit(1);
        }
    }

    public void create() {
        try {
            Manga manga = new Manga();

            System.out.print("ISBN: ");
            manga.setIsbn(sc.nextLine());

            if (IndexManager.existsISBN(manga.getIsbn())) {
                System.out.println("ISBN já cadastrado.");
                return;
            }

            System.out.print("Título: ");
            manga.setTitulo(sc.nextLine());

            System.out.print("Autores: ");
            manga.setAutores(sc.nextLine());

            System.out.print("Ano de início: ");
            manga.setAnoInicio(sc.nextLine());

            System.out.print("Ano de fim (ou '-' se ainda em publicação): ");
            manga.setAnoFim(sc.nextLine());

            System.out.print("Gênero: ");
            manga.setGenero(sc.nextLine());

            System.out.print("Revista: ");
            manga.setRevista(sc.nextLine());

            System.out.print("Editora: ");
            manga.setEditora(sc.nextLine());

            System.out.print("Ano da edição: ");
            manga.setAnoEdicao(sc.nextLine());

            System.out.print("Quantidade de volumes: ");
            manga.setQtdVolumes(Integer.parseInt(sc.nextLine()));

            System.out.print("Quantidade de volumes adquiridos: ");
            manga.setQtdVolumesAdquiridos(Integer.parseInt(sc.nextLine()));

            System.out.print("Volumes adquiridos (ex: 1,2,3): ");
            String[] vols = sc.nextLine().split(",");
            List<Integer> volumes = new ArrayList<>();
            for (String v : vols) {
                volumes.add(Integer.parseInt(v.trim()));
            }
            manga.setVolumesAdquiridos(volumes);

            FileManager.appendManga(Consts.DATA, manga);

            IndexManager.rebuildIndexes(Consts.DATA);

            System.out.println("Mangá cadastrado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao criar mangá: " + e.getMessage());
        }
    }

    public void readByIsbnOrTitle() {
        System.out.print("Digite ISBN ou Título: ");
        String input = sc.nextLine();

        try {
            String isbn;
            if (IndexManager.existsISBN(input)) {
                isbn = input;
            } else {
                String caseInsentive = input.toLowerCase();
                List<String> isbns = IndexManager.getISBNsByTitle(
                    caseInsentive
                );
                if (isbns == null || isbns.isEmpty()) {
                    System.out.println("Registro não encontrado.");
                    return;
                }
                for (String i : isbns) {
                    Manga manga = FileManager.readMangaByISBN(Consts.DATA, i);
                    if (manga != null) {
                        System.out.println(manga.toLine());
                    }
                }
                return;
            }

            Manga manga = FileManager.readMangaByISBN(Consts.DATA, isbn);
            if (manga != null) {
                manga.toLine();
            } else {
                System.out.println("Registro não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro na leitura: " + e.getMessage());
        }
    }

    public void readAll() {
        try {
            List<Manga> allManga = FileManager.readAll(Consts.DATA);
            int i = 1;
            for (Manga m : allManga) {
                System.out.format(
                    "\n=============== MANGA NUMERO %d ===============\n",
                    i
                );
                System.out.println(m.toLine());
                i++;
            }
        } catch (Exception e) {
            System.out.println("Erro na leitura: " + e.getMessage());
        }
    }

    public void update() {
        System.out.print("Digite o ISBN do mangá a ser atualizado: ");
        String isbn = sc.nextLine();

        try {
            Manga oldManga = FileManager.readMangaByISBN(Consts.DATA, isbn);
            if (oldManga == null) {
                System.out.println("Mangá não encontrado.");
                return;
            }

            deleteByISBN(isbn, false); // remove sem confirmação
            System.out.println("Insira os novos dados:");
            create();
        } catch (Exception e) {
            System.out.println("Erro na atualização: " + e.getMessage());
        }
    }

    public void delete() {
        System.out.print("Digite o ISBN do mangá a ser excluído: ");
        String isbn = sc.nextLine();
        deleteByISBN(isbn, true);
    }

    private void deleteByISBN(String isbn, boolean confirm) {
        try {
            Manga manga = FileManager.readMangaByISBN(Consts.DATA, isbn);
            if (manga == null) {
                System.out.println("Mangá não encontrado.");
                return;
            }

            if (confirm) {
                System.out.print("Tem certeza que deseja excluir? (s/n): ");
                String resposta = sc.nextLine();
                if (!resposta.equalsIgnoreCase("s")) {
                    System.out.println("Operação cancelada.");
                    return;
                }
            }

            FileManager.removeManga(Consts.DATA, isbn);
            IndexManager.removePrimaryIndex(isbn);
            IndexManager.removeSecondaryIndex(manga.getTitulo(), isbn);

            System.out.println("Mangá removido com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro na exclusão: " + e.getMessage());
        }
    }
}
