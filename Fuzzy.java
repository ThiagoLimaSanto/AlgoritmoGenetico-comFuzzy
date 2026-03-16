public class Fuzzy {

    public void realizarTriagem(Paciente p) {
        double suporteCalculado = funcaoTrapezoidal(p.getDificuldadeRespiratoria(), 20, 50, 70, 110) * 100.0;
        p.setNecessidadeSuporte(suporteCalculado);
        double isolamentoCalculado = funcaoTrapezoidal(p.getRiscoContagio(), 30, 60, 100, 110) * 100.0;
        p.setNecessidadeIsolamento(isolamentoCalculado);

        double gravidadeBase = Math.max(suporteCalculado, isolamentoCalculado);

        double pesoFebre = funcaoTrapezoidal(p.getFebre(), 37.5, 38.5, 40.0, 41.0);
        double pesoDor = funcaoTrapezoidal(p.getDor(), 40, 70, 100, 110);
        double bonusSintomas = (pesoFebre + pesoDor) * 5.0;

        double bonusIdade = fuzzyIdade(p) * 15.0;

        double urgenciaCalculada = gravidadeBase + bonusSintomas + bonusIdade;
        p.setUrgenciaFinalFuzzy(Math.min(100.0, urgenciaCalculada));
    }

    public double calcularFuzzy(Paciente paciente, Quarto quarto, int ocupacao) {
        if (quarto == null) {
            return avaliarFila(paciente);
        }

        double grauSexo = fuzzySexo(paciente, quarto);
        if (grauSexo == 0.0)
            return 0.0;

        double grauSuporte = fuzzySuporte(paciente, quarto);
        double grauIsolamento = fuzzyIsolamento(paciente, quarto);
        double grauUrgencia = fuzzyUrgencia(paciente, quarto);
        double grauSuperLotacao = fuzzySuperLotacao(quarto, ocupacao);

        return combinarGraus(grauSuporte, grauIsolamento, grauUrgencia, grauSuperLotacao, grauSexo);
    }

    private double combinarGraus(double grauSuporte, double grauIsolamento, double grauUrgencia,
            double grauSuperLotacao, double grauSexo) {
        double resultado = 0.3 * grauSuporte
                + 0.3 * grauIsolamento
                + 0.25 * grauUrgencia
                + 0.1 * grauSuperLotacao
                + 0.05 * grauSexo;
        return Math.min(1.0, resultado);
    }

    private double fuzzyIdade(Paciente p) {
        int idade = p.getIdade();
        return funcaoTrapezoidal(idade, 55, 65, 100, 110);
    }

    private double fuzzySuperLotacao(Quarto q, int ocupacao) {
        double taxa = ocupacao / (double) q.getCapacidade();
        return funcaoTrapezoidal(taxa, 0.8, 1.0, 1.2, 1.5);
    }

    private double fuzzyUrgencia(Paciente p, Quarto q) {
        double diff = q.getNivelDeCuidado() - p.getUrgenciaFinalFuzzy();
        return funcaoTrapezoidal(diff, -30, 0, 40, 100);
    }

    private double fuzzySuporte(Paciente p, Quarto q) {
        double diff = q.getNivelSuporte() - p.getNecessidadeSuporte();
        return funcaoTrapezoidal(diff, -80, -40, 30, 50);
    }

    private double fuzzyIsolamento(Paciente p, Quarto q) {
        double diff = q.getNivelIsolamento() - p.getNecessidadeIsolamento();
        return funcaoTrapezoidal(diff, -80, -40, 50, 50);
    }

    private double fuzzySexo(Paciente p, Quarto q) {
        if (q.getSexoPermitido() == Sexo.MISTO || p.getSexo() == q.getSexoPermitido())
            return 1.0;
        return 0.0;
    }

    private double avaliarFila(Paciente p) {
        double urgencia = p.getUrgenciaFinalFuzzy();
        double penalidade = 1.0 - funcaoTrapezoidal(urgencia, 0, 30, 80, 100);

        if (urgencia > 70) {
            penalidade *= 0.5;
        }

        return Math.max(0.01, 0.1 * penalidade);
    }

    private double funcaoTrapezoidal(double x, double a, double b, double c, double d) {
        if (x <= a || x >= d)
            return 0.0;
        if (x >= b && x <= c)
            return 1.0;
        if (x > a && x < b)
            return (x - a) / (b - a);
        return (d - x) / (d - c);
    }
}