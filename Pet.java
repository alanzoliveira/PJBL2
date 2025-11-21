import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Pet implements Serializable {

    private String nome;
    private LocalDate dataNascimento;
    private String especie;
    private String raca;
    private String sexo;

    public Pet(String nome, LocalDate dataNascimento, String especie, String raca, String sexo) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.especie = especie;
        this.raca = raca;
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
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
        return nome + "\t" + dataNascimento + "\t" + getIdade() + "\t" +
               especie + "\t" + raca + "\t" + sexo;
    }
}
