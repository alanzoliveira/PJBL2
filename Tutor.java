import java.io.Serializable;
import java.util.ArrayList;

public class Tutor implements Serializable {

    private int codigo;
    private String nome;
    private String telefone;
    private ArrayList<Pet> pets;

    public Tutor(int codigo, String nome, String telefone) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
        this.pets = new ArrayList<>();
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

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(codigo).append("\t").append(nome).append("\t").append(telefone).append("\n");
        for (Pet pet : pets) {
            sb.append("   - ").append(pet.toString()).append("\n");
        }
        return sb.toString();
    }
}
