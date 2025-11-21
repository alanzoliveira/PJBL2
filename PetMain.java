import java.util.Scanner;

public class PetMain {

    public static void main(String[] args) {

        PetInterface sistema = new PetInterface();
        sistema.recuperaDados();

        Scanner entrada = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n====== MENU ======");
            System.out.println("1 - Cadastrar tutor + pets");
            System.out.println("2 - Imprimir cadastro");
            System.out.println("3 - Buscar pets por codigo do tutor");
            System.out.println("4 - Excluir pets por codigo do tutor");
            System.out.println("5 - Encerrar");
            System.out.print("Opcao: ");

            opcao = entrada.nextInt();
            entrada.nextLine();

            if (opcao == 1) sistema.cadastrar(entrada);
            else if (opcao == 2) sistema.imprimirCadastro();
            else if (opcao == 3) sistema.buscar(entrada);
            else if (opcao == 4) sistema.excluir(entrada);

        } while (opcao != 5);

        sistema.gravaDados();
        System.out.println("Programa encerrado.");
    }
}
