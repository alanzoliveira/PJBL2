import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;
import java.io.IOException;

public class PetInterface {

    private Pet[] pets;
    private int qtdPets;
    private int proximoCodigoTutor;

    public PetInterface() {
        pets = new Pet[1000];
        qtdPets = 0;
        proximoCodigoTutor = 1;
    }

    public void recuperaDados() {
        try {
            File f = new File("cadastro_pet.dat");
            if (!f.exists()) return;

            Scanner arq = new Scanner(f);

            while (arq.hasNextLine()) {
                String linha = arq.nextLine();
                String[] partes = linha.split("\t");

                int codigoTutor = Integer.parseInt(partes[0]);
                String nome = partes[1];
                int idade = Integer.parseInt(partes[2]);
                String especie = partes[3];
                String raca = partes[4];
                String sexo = partes[5];

                pets[qtdPets] = new Pet(codigoTutor, nome, idade, especie, raca, sexo);
                qtdPets++;

                if (codigoTutor >= proximoCodigoTutor) {
                    proximoCodigoTutor = codigoTutor + 1;
                }
            }
            arq.close();

        } catch (Exception e) {
            System.out.println("Erro ao recuperar dados: " + e.getMessage());
        }
    }

    public void gravaDados() {
        try {
            PrintStream out = new PrintStream("cadastro_pet.dat");

            for (int i = 0; i < qtdPets; i++) {
                out.println(pets[i].toString());
            }

            out.close();

        } catch (IOException e) {
            System.out.println("Erro ao gravar dados: " + e.getMessage());
        }
    }

    public void cadastrar(Scanner entrada) {

        int codigoTutor = proximoCodigoTutor;
        proximoCodigoTutor++;

        System.out.print("Nome do tutor: ");
        String nomeTutor = entrada.nextLine();

        System.out.print("Telefone do tutor: ");
        String telefone = entrada.nextLine();

        System.out.print("Quantidade de pets do tutor: ");
        int qtd = entrada.nextInt();
        entrada.nextLine();

        for (int i = 0; i < qtd; i++) {
            System.out.println("\n--- Cadastro do Pet " + (i + 1) + " ---");

            System.out.print("Nome: ");
            String nome = entrada.nextLine();

            System.out.print("Idade: ");
            int idade = entrada.nextInt();
            entrada.nextLine();

            System.out.print("Especie: ");
            String especie = entrada.nextLine();

            System.out.print("Raca: ");
            String raca = entrada.nextLine();

            System.out.print("Sexo (M/F): ");
            String sexo = entrada.nextLine();

            pets[qtdPets] = new Pet(codigoTutor, nome, idade, especie, raca, sexo);
            qtdPets++;
        }

        gravaDados();
        System.out.println("\nCadastro realizado com sucesso! Codigo do tutor = " + codigoTutor);
    }

    public void imprimirCadastro() {
        if (qtdPets == 0) {
            System.out.println("Nao ha pets cadastrados.");
            return;
        }

        for (int i = 0; i < qtdPets; i++) {
            System.out.println(pets[i].toString());
        }
    }

    public void buscar(Scanner entrada) {
        System.out.print("Informe o codigo do tutor (numero): ");

        if (!entrada.hasNextInt()) {
            System.out.println("Codigo invalido, digite um numero.");
            entrada.nextLine();
            return;
        }

        int codigo = entrada.nextInt();
        entrada.nextLine();

        boolean achou = false;

        for (int i = 0; i < qtdPets; i++) {
            if (pets[i].getCodigoTutor() == codigo) {
                System.out.println(pets[i].toString());
                achou = true;
            }
        }

        if (!achou) System.out.println("Nenhum pet encontrado para este tutor.");
    }

    public void excluir(Scanner entrada) {
        System.out.print("Informe o codigo do tutor (numero): ");

        if (!entrada.hasNextInt()) {
            System.out.println("Codigo invalido.");
            entrada.nextLine();
            return;
        }

        int codigo = entrada.nextInt();
        entrada.nextLine();

        int novaQtd = 0;
        boolean excluiu = false;

        Pet[] novaLista = new Pet[1000];

        for (int i = 0; i < qtdPets; i++) {
            if (pets[i].getCodigoTutor() != codigo) {
                novaLista[novaQtd] = pets[i];
                novaQtd++;
            } else {
                excluiu = true;
            }
        }

        pets = novaLista;
        qtdPets = novaQtd;

        if (excluiu) {
            gravaDados();
            System.out.println("Pets excluidos com sucesso.");
        } else {
            System.out.println("Codigo nao encontrado.");
        }
    }
}
