public class Quarto {
    private int id;
    private int capacidade;
    private Sexo sexoPermitido;
    private double nivelDeCuidado;
    private double nivelSuporte;
    private double nivelIsolamento;

    public Quarto(int id, int capacidade, Sexo sexoPermitido, double nivelDeCuidado, double nivelSuporte,
            double nivelIsolamento) {
        this.id = id;
        this.capacidade = capacidade;
        this.sexoPermitido = sexoPermitido;
        this.nivelDeCuidado = nivelDeCuidado;
        this.nivelSuporte = nivelSuporte;
        this.nivelIsolamento = nivelIsolamento;
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

    public double getNivelDeCuidado() {
        return nivelDeCuidado;
    }

    public double getNivelSuporte() {
        return nivelSuporte;
    }

    public double getNivelIsolamento() {
        return nivelIsolamento;
    }
}