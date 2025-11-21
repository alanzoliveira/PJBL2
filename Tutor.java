public class Tutor {

    private int codigo;
    private String nome;
    private String telefone;

    public Tutor(int codigo, String nome, String telefone) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return codigo + "\t" + nome + "\t" + telefone;
    }
}
