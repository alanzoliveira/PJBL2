import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PetInterface extends JFrame {

    private ArrayList<Tutor> tutores;
    private static final String ARQUIVO = "tutores.dat";

    private JButton btnCadastrar, btnImprimir, btnBuscar, btnExcluir, btnEncerrar;
    private JTextArea logArea;
    private static Font fontePadrao = new Font("Consolas", Font.PLAIN, 13);

    private int proximoCodigoTutor = 1;

    public PetInterface() {
        super("Cadastro de Tutores e Pets");
        setSize(600, 400);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tutores = new ArrayList<>();
        recuperaDados();

        btnCadastrar = new JButton("Cadastrar Tutor + Pets");
        btnImprimir = new JButton("Imprimir Cadastro");
        btnBuscar = new JButton("Buscar por Código");
        btnExcluir = new JButton("Excluir por Código");
        btnEncerrar = new JButton("Encerrar");

        add(btnCadastrar);
        add(btnImprimir);
        add(btnBuscar);
        add(btnExcluir);
        add(btnEncerrar);

        logArea = new JTextArea(10, 50);
        logArea.setEditable(false);
        logArea.setFont(fontePadrao);
        add(new JScrollPane(logArea));

        btnCadastrar.addActionListener(e -> cadastrarTutor());
        btnImprimir.addActionListener(e -> imprimirCadastro());
        btnBuscar.addActionListener(e -> buscarTutor());
        btnExcluir.addActionListener(e -> excluirTutor());
        btnEncerrar.addActionListener(e -> {
            gravaDados();
            dispose();
            System.exit(0);
        });

        setVisible(true);
    }

    // ===== MÉTODOS PÚBLICOS =====
    public void cadastrarTutor() {
        JFrame frame = new JFrame("Cadastro de Tutor + Pets");
        frame.setLayout(new FlowLayout());
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        JTextField nomeTutor = new JTextField(20);
        JTextField codTutor = new JTextField(5);
        codTutor.setText("" + geraCodigo());
        codTutor.setEditable(false);
        frame.add(new JLabel("Nome do Tutor:"));
        frame.add(nomeTutor);

        JTextField nomePet = new JTextField(15);
        JTextField dataPet = new JTextField(10);
        frame.add(new JLabel("Nome do Pet:"));
        frame.add(nomePet);
        frame.add(new JLabel("Data de Nascimento (aaaa-mm-dd):"));
        frame.add(dataPet);

        JButton btnSalvar = new JButton("Salvar Tutor + Pet");
        frame.add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            String nomeT = nomeTutor.getText();
            String nomeP = nomePet.getText();
            String data = dataPet.getText();

            if (nomeT.isEmpty() || nomeP.isEmpty() || data.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Todos os campos devem ser preenchidos!");
                return;
            }

            LocalDate dataNascPet;
            try {
                dataNascPet = LocalDate.parse(data);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Data inválida!");
                return;
            }

            Pet pet = new Pet(nomeP, dataNascPet, "Desconhecida", "Desconhecida", "Desconhecido");
            Tutor tutor = new Tutor(geraCodigo(), nomeT, "");
            tutor.addPet(pet);
            tutores.add(tutor);
            gravaDados();

            log("Cadastrado Tutor: " + nomeT + " com Pet: " + nomeP);
            JOptionPane.showMessageDialog(frame, "Cadastro realizado com sucesso!");
            frame.dispose();
        });

        frame.setVisible(true);
    }

    public void imprimirCadastro() {
        if (tutores.isEmpty()) {
            log("<Cadastro vazio>");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Código | Tutor | Pets (Nome - Idade)\n");
        sb.append("---------------------------------------------\n");
        for (Tutor t : tutores) {
            sb.append(t.getCodigo()).append(" | ").append(t.getNome()).append(" | ");
            for (Pet p : t.getPets()) {
                sb.append(p.getNome()).append(" (").append(p.getIdade()).append(" anos), ");
            }
            sb.append("\n");
        }
        log(sb.toString());
    }

    public void buscarTutor() {
        String input = JOptionPane.showInputDialog("Digite o código do Tutor:");
        int codigo;
        try {
            codigo = Integer.parseInt(input);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Código inválido!");
            return;
        }
        Tutor encontrado = null;
        for (Tutor t : tutores) {
            if (t.getCodigo() == codigo) {
                encontrado = t;
                break;
            }
        }
        if (encontrado != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Tutor: ").append(encontrado.getNome()).append("\n");
            for (Pet p : encontrado.getPets()) {
                sb.append("Pet: ").append(p.getNome())
                  .append(" - Idade: ").append(p.getIdade()).append(" anos\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
            log("Busca realizada para Tutor: " + encontrado.getNome());
        } else {
            JOptionPane.showMessageDialog(this, "Tutor não encontrado!");
            log("Busca: Tutor código " + codigo + " inexistente.");
        }
    }

    public void excluirTutor() {
        String input = JOptionPane.showInputDialog("Digite o código do Tutor a ser excluído:");
        int codigo;
        try {
            codigo = Integer.parseInt(input);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Código inválido!");
            return;
        }
        Tutor alvo = null;
        for (Tutor t : tutores) {
            if (t.getCodigo() == codigo) {
                alvo = t;
                break;
            }
        }
        if (alvo != null) {
            tutores.remove(alvo);
            gravaDados();
            JOptionPane.showMessageDialog(this, "Tutor excluído com sucesso!");
            log("Excluído Tutor: " + alvo.getNome());
        } else {
            JOptionPane.showMessageDialog(this, "Tutor não encontrado!");
            log("Exclusão: Tutor código " + codigo + " inexistente.");
        }
    }

    // ===== AUXILIARES =====
    private void log(String msg) {
        logArea.append(msg + "\n");
    }

    @SuppressWarnings("unchecked")
    private void recuperaDados() {
        File f = new File(ARQUIVO);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            tutores = (ArrayList<Tutor>) ois.readObject();
            if (!tutores.isEmpty()) {
                proximoCodigoTutor = tutores.get(tutores.size() - 1).getCodigo() + 1;
            }
        } catch (Exception e) {
            log("Erro ao carregar dados: " + e.getMessage());
        }
    }

    public void gravaDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(tutores);
        } catch (IOException e) {
            log("Erro ao gravar dados: " + e.getMessage());
        }
    }

    private int geraCodigo() {
        return proximoCodigoTutor++;
    }

    public static void main(String[] args) {
        new PetInterface();
    }
}
