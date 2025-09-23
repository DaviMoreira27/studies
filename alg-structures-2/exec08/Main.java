import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MangaManager manager = new MangaManager();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n====== GERENCIADOR DE MANGÁS ======");
            System.out.println("1 - Cadastrar novo mangá");
            System.out.println("2 - Consultar mangá (por ISBN ou título)");
            System.out.println("3 - Liste todos os mangás");
            System.out.println("4 - Atualizar dados de um mangá");
            System.out.println("5 - Remover mangá");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                opcao = 0;
                continue;
            }

            switch (opcao) {
                case 1:
                    manager.create();
                    break;
                case 2:
                    manager.readByIsbnOrTitle();
                    break;
                case 3:
                    manager.readAll();
                    break;
                case 4:
                    manager.update();
                    break;
                case 5:
                    manager.delete();
                    break;
                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 6);

        sc.close();
    }
}
