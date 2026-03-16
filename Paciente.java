public class Paciente {
    private int id;
    private String nome;
    private int idade;
    private Sexo sexo;
    private double dor;
    private double febre;
    private double dificuldadeRespiratoria;
    private double riscoContagio;
    private double urgenciaFinalFuzzy;
    private double necessidadeSuporte;
    private double necessidadeIsolamento;

    public Paciente(int id, String nome, int idade, Sexo sexo, double dor, double febre, double respira,
            double contagio) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.dor = dor;
        this.febre = febre;
        this.dificuldadeRespiratoria = respira;
        this.riscoContagio = contagio;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public double getDor() {
        return dor;
    }

    public double getFebre() {
        return febre;
    }

    public double getDificuldadeRespiratoria() {
        return dificuldadeRespiratoria;
    }

    public double getRiscoContagio() {
        return riscoContagio;
    }

    public double getUrgenciaFinalFuzzy() {
        return urgenciaFinalFuzzy;
    }

    public double getNecessidadeSuporte() {
        return necessidadeSuporte;
    }

    public double getNecessidadeIsolamento() {
        return necessidadeIsolamento;
    }

    public void setUrgenciaFinalFuzzy(double urgenciaFinalFuzzy) {
        this.urgenciaFinalFuzzy = urgenciaFinalFuzzy;
    }

    public void setNecessidadeSuporte(double necessidadeSuporte) {
        this.necessidadeSuporte = necessidadeSuporte;
    }

    public void setNecessidadeIsolamento(double necessidadeIsolamento) {
        this.necessidadeIsolamento = necessidadeIsolamento;
    }

}