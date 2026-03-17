public class Fuzzy {

    public void realizarTriagem(Paciente p) {
        double[] grausResp = fuzzyDificuldadeRespiratoria(p.getDificuldadeRespiratoria());
        double[] grausCont = fuzzyRiscoContagio(p.getRiscoContagio());
        double[] grausFebre = fuzzyFebre(p.getFebre());
        double[] grausDor = fuzzyDor(p.getDor());
        double[] grausIdade = fuzzyIdade(p.getIdade());

        double suporteCalculado = grausResp[2] * 100.0;
        p.setNecessidadeSuporte(suporteCalculado);

        double isolamentoCalculado = grausCont[2] * 100.0;
        p.setNecessidadeIsolamento(isolamentoCalculado);

        double gravidadeBase = Math.max(suporteCalculado, isolamentoCalculado);

        double pesoFebre = grausFebre[2];
        double pesoDor = grausDor[2];
        double bonusSintomas = (pesoFebre + pesoDor) * 5.0;

        double bonusIdade = grausIdade[2] * 15.0;

        double urgenciaCalculada = gravidadeBase + bonusSintomas + bonusIdade;
        p.setUrgenciaFinalFuzzy(Math.min(100.0, urgenciaCalculada));
    }

    public double calcularFuzzy(Paciente paciente, Quarto quarto, int ocupacao) {
        if (quarto == null) {
            return avaliarFila(paciente, ocupacao);
        }

        double grauSexo = fuzzySexo(paciente, quarto);
        if (grauSexo == 0.0)
            return 0.0;

        double[] grausSuporte = fuzzySuporte(paciente, quarto);
        double[] grausIsolamento = fuzzyIsolamento(paciente, quarto);
        double[] grausUrgencia = fuzzyUrgencia(paciente, quarto);
        double grauSuperLotacao = fuzzySuperLotacao(quarto, ocupacao);

        return combinarGraus(
                grausSuporte[2],
                grausIsolamento[2],
                grausUrgencia[2],
                grauSuperLotacao,
                grauSexo);
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

    // --- FUNÇÕES COM NÍVEIS: [0] Baixo, [1] Médio, [2] Alto ---
    // Note que o índice [2] usa exatamente os números originais que você definiu

    private double[] fuzzyDificuldadeRespiratoria(double d) {
        return new double[] {
                funcaoTrapezoidal(d, 0, 0, 15, 30), // [0] Baixo
                funcaoTrapezoidal(d, 15, 30, 40, 60), // [1] Médio
                funcaoTrapezoidal(d, 20, 50, 70, 110) // [2] Alto (Seu original)
        };
    }

    private double[] fuzzyRiscoContagio(double r) {
        return new double[] {
                funcaoTrapezoidal(r, 0, 0, 20, 40), // [0] Baixo
                funcaoTrapezoidal(r, 20, 40, 50, 70), // [1] Médio
                funcaoTrapezoidal(r, 30, 60, 100, 110) // [2] Alto (Seu original)
        };
    }

    private double[] fuzzyFebre(double t) {
        return new double[] {
                funcaoTrapezoidal(t, 0, 0, 36.5, 37.5), // [0] Baixa
                funcaoTrapezoidal(t, 37.0, 37.5, 38.0, 39.0), // [1] Média
                funcaoTrapezoidal(t, 37.5, 38.5, 40.0, 41.0) // [2] Alta (Seu original)
        };
    }

    private double[] fuzzyDor(double d) {
        return new double[] {
                funcaoTrapezoidal(d, 0, 0, 20, 40), // [0] Baixa
                funcaoTrapezoidal(d, 20, 40, 50, 70), // [1] Média
                funcaoTrapezoidal(d, 40, 70, 100, 110) // [2] Alta (Seu original)
        };
    }

    private double[] fuzzyIdade(int idade) {
        return new double[] {
                funcaoTrapezoidal(idade, 0, 0, 18, 30), // [0] Jovem
                funcaoTrapezoidal(idade, 25, 35, 50, 60), // [1] Adulto
                funcaoTrapezoidal(idade, 55, 65, 100, 110) // [2] Idoso (Seu original)
        };
    }

    private double fuzzySuperLotacao(Quarto q, int ocupacao) {
        double taxa = ocupacao / (double) q.getCapacidade();
        return funcaoTrapezoidal(taxa, 0.8, 1.0, 1.2, 1.5);
    }

    private double[] fuzzyUrgencia(Paciente p, Quarto q) {
        double diff = q.getNivelDeCuidado() - p.getUrgenciaFinalFuzzy();
        return new double[] {
                funcaoTrapezoidal(diff, -100, -100, -30, 0), // [0] Baixa (Quarto muito fraco pro paciente)
                funcaoTrapezoidal(diff, -40, -20, 10, 30), // [1] Média (Quarto atende no limite)
                funcaoTrapezoidal(diff, -30, 0, 40, 100) // [2] Alta (O seu original intacto)
        };
    }

    private double[] fuzzySuporte(Paciente p, Quarto q) {
        double diff = q.getNivelSuporte() - p.getNecessidadeSuporte();
        return new double[] {
                funcaoTrapezoidal(diff, -100, -100, -80, -40), // [0] Baixa (Falta muito equipamento)
                funcaoTrapezoidal(diff, -90, -50, -20, 10), // [1] Média (Suporte marginal/médio)
                funcaoTrapezoidal(diff, -80, -40, 30, 50) // [2] Alta (O seu original intacto)
        };
    }

    private double[] fuzzyIsolamento(Paciente p, Quarto q) {
        double diff = q.getNivelIsolamento() - p.getNecessidadeIsolamento();
        return new double[] {
                funcaoTrapezoidal(diff, -100, -100, -80, -40), // [0] Baixa (Risco de contaminação alto)
                funcaoTrapezoidal(diff, -90, -50, -20, 10), // [1] Média (Isolamento razoável)
                funcaoTrapezoidal(diff, -80, -40, 50, 50) // [2] Alta (O seu original intacto)
        };
    }

    private double fuzzySexo(Paciente p, Quarto q) {
        if (q.getSexoPermitido() == Sexo.MISTO || p.getSexo() == q.getSexoPermitido())
            return 1.0;
        return 0.0;
    }

    private double avaliarFila(Paciente p, int ocupacao) {
        double urgencia = p.getUrgenciaFinalFuzzy();

        if (urgencia < 20.0) {
            return 0.85;
        }
        return 0.01;
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