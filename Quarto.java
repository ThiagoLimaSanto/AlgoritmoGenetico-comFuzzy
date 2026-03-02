public class Quarto {
    private int id;
    private int capacidade;
    private Sexo sexoPermitido;
    private Tipo tipoDeUrgencia;
    private int nivelDeCuidado;
    private int quantidadeDePacientes;

    public Quarto(int id, int capacidade, Sexo sexoPermitido, Tipo tipoDeUrgencia, int nivelDeCuidado) {
        this.id = id;
        this.capacidade = capacidade;
        this.sexoPermitido = sexoPermitido;
        this.tipoDeUrgencia = tipoDeUrgencia;
        this.nivelDeCuidado = nivelDeCuidado;
        this.quantidadeDePacientes = 0;
    }

    public Quarto(int id, int capacidade, Sexo sexoPermitido, Tipo tipoDeUrgencia) {
        this.id = id;
        this.capacidade = capacidade;
        this.sexoPermitido = sexoPermitido;
        this.tipoDeUrgencia = tipoDeUrgencia;
        this.quantidadeDePacientes = 0;
    }

    public int getId() {
        return id;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public Sexo getSexoPermitido() {
        return sexoPermitido;
    }

    public Tipo getTipoDeUrgencia() {
        return tipoDeUrgencia;
    }

    public int getNivelDeCuidado() {
        return nivelDeCuidado;
    }

    public int getQuantidadeDePacientes() {
        return quantidadeDePacientes;
    }

    public void setQuantidadeDePacientes(int quantidadeDePacientes) {
        this.quantidadeDePacientes = quantidadeDePacientes;
    }
}