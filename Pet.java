public class Pet {

    private int codigoTutor;
    private String nome;
    private int idade;
    private String especie;
    private String raca;
    private String sexo;

    public Pet(int codigoTutor, String nome, int idade, String especie, String raca, String sexo) {
        this.codigoTutor = codigoTutor;
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.raca = raca;
        this.sexo = sexo;
    }

    public int getCodigoTutor() {
        return codigoTutor;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public String getSexo() {
        return sexo;
    }

    @Override
    public String toString() {
        return codigoTutor + "\t" + nome + "\t" + idade + "\t" +
               especie + "\t" + raca + "\t" + sexo;
    }
}
