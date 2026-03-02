public class Paciente {
    private int id;
    private String nome;
    private Sexo sexo;
    private int idade;
    private int grauDeUrgencia;
    private boolean precisaDeIsolamento;
    private boolean precisaDeUti;

    public Paciente(int id, String nome, Sexo sexo, int idade, int grauDeUrgencia, boolean precisaDeIsolamento,
            boolean precisaDeUti) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.grauDeUrgencia = grauDeUrgencia;
        this.precisaDeIsolamento = precisaDeIsolamento;
        this.precisaDeUti = precisaDeUti;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public int getIdade() {
        return idade;
    }

    public int getGrauDeUrgencia() {
        return grauDeUrgencia;
    }

    public boolean isPrecisaDeIsolamento() {
        return precisaDeIsolamento;
    }

    public boolean isPrecisaDeUti() {
        return precisaDeUti;
    }

    @Override
    public String toString() {
        return "Paciente{" + "id=" + id + ", nome='" + nome + '\'' + ", sexo='" + sexo + '\'' + ", idade=" + idade
                + ", grauDeUrgencia=" + grauDeUrgencia + ", precisaDeIsolamento=" + precisaDeIsolamento
                + ", precisaDeUti=" + precisaDeUti + '}';
    }
}