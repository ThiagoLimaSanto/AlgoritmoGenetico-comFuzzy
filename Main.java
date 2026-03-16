import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Quarto> quartos = new ArrayList<>();
        List<Paciente> pacientes = new ArrayList<>();
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico();

        int geracaoAtual = 0;
        int geracoes = 3000;

        // ==========================================
        // Melhor cenario
        // ==========================================

        // quartos.add(new Quarto(1, 3, Sexo.MISTO, 90.0, 100.0, 0.0));
        // quartos.add(new Quarto(2, 2, Sexo.MISTO, 50.0, 40.0, 100.0));
        // quartos.add(new Quarto(3, 3, Sexo.F, 10.0, 10.0, 0.0));
        // quartos.add(new Quarto(4, 4, Sexo.M, 20.0, 20.0, 0.0));
        // quartos.add(new Quarto(5, 3, Sexo.MISTO, 10.0, 10.0, 0.0));

        // pacientes.add(new Paciente(1, "João", 65, Sexo.M, 20.0, 38.5, 95.0, 0.0));
        // pacientes.add(new Paciente(2, "Maria", 72, Sexo.F, 10.0, 39.0, 90.0, 10.0));
        // pacientes.add(new Paciente(3, "Pedro", 60, Sexo.M, 30.0, 37.5, 92.0, 0.0));

        // pacientes.add(new Paciente(4, "Carlos", 58, Sexo.M, 80.0, 39.5, 20.0,
        // 100.0));
        // pacientes.add(new Paciente(5, "Fernanda", 49, Sexo.F, 70.0, 38.0, 15.0,
        // 95.0));

        // pacientes.add(new Paciente(6, "Patricia", 36, Sexo.F, 40.0, 37.0, 5.0, 0.0));
        // pacientes.add(new Paciente(7, "Beatriz", 19, Sexo.F, 20.0, 36.5, 0.0, 0.0));
        // pacientes.add(new Paciente(8, "Camila", 26, Sexo.F, 30.0, 37.2, 10.0, 5.0));
        // pacientes.add(new Paciente(9, "Sofia", 30, Sexo.F, 10.0, 36.0, 0.0, 0.0));

        // pacientes.add(new Paciente(10, "Bruno", 40, Sexo.M, 50.0, 38.0, 10.0, 0.0));
        // pacientes.add(new Paciente(11, "Lucas", 40, Sexo.M, 55.0, 37.8, 15.0, 0.0));
        // pacientes.add(new Paciente(12, "Rafael", 47, Sexo.M, 60.0, 37.5, 20.0, 0.0));
        // pacientes.add(new Paciente(13, "Thiago", 41, Sexo.M, 40.0, 37.0, 5.0, 0.0));

        // pacientes.add(new Paciente(14, "Marcos", 22, Sexo.M, 10.0, 36.5, 0.0, 0.0));
        // pacientes.add(new Paciente(15, "Juliana", 29, Sexo.F, 15.0, 36.8, 5.0, 0.0));

        // ==========================================
        // Cenário de superlotaca
        // ==========================================

        // quartos.add(new Quarto(1, 2, Sexo.MISTO, 90.0, 100.0, 0.0)); // UTI lotada
        // quartos.add(new Quarto(2, 2, Sexo.MISTO, 50.0, 40.0, 100.0)); // Isolamento
        // quartos.add(new Quarto(3, 3, Sexo.F, 10.0, 10.0, 0.0)); // Enfermaria
        // quartos.add(new Quarto(4, 3, Sexo.M, 10.0, 10.0, 0.0)); // Enfermaria

        // pacientes.add(new Paciente(1, "Joao Idoso", 85, Sexo.M, 20.0, 39.0, 95.0,
        // 0.0));
        // pacientes.add(new Paciente(2, "Maria Idosa", 82, Sexo.F, 10.0, 39.5, 90.0,
        // 0.0));
        // pacientes.add(new Paciente(3, "Pedro Novo", 25, Sexo.M, 30.0, 38.0, 92.0,
        // 0.0));
        // pacientes.add(new Paciente(4, "Ana Nova", 22, Sexo.F, 20.0, 38.5, 91.0,
        // 0.0));
        // pacientes.add(new Paciente(5, "Jose", 70, Sexo.M, 10.0, 39.0, 98.0, 0.0));
        // pacientes.add(new Paciente(6, "Rosa", 75, Sexo.F, 10.0, 39.0, 96.0, 0.0));

        // pacientes.add(new Paciente(7, "Carlos", 58, Sexo.M, 80.0, 39.5, 20.0,
        // 100.0));
        // pacientes.add(new Paciente(8, "Fernanda", 49, Sexo.F, 70.0, 38.0, 15.0,
        // 95.0));
        // pacientes.add(new Paciente(9, "Roberto", 62, Sexo.M, 60.0, 39.0, 10.0,
        // 98.0));
        // pacientes.add(new Paciente(10, "Julia", 30, Sexo.F, 50.0, 38.5, 5.0, 92.0));

        // for (int i = 11; i <= 22; i++) {
        // Sexo s = (i % 2 == 0) ? Sexo.F : Sexo.M;
        // pacientes.add(new Paciente(i, "Leve_" + i, 20 + i, s, 20.0, 37.0, 0.0, 0.0));
        // }

        // ==========================================
        // Cenario de abundacia
        // ==========================================

        // quartos.add(new Quarto(1, 5, Sexo.MISTO, 100.0, 100.0, 0.0));
        // quartos.add(new Quarto(2, 5, Sexo.MISTO, 100.0, 50.0, 100.0));
        // quartos.add(new Quarto(3, 10, Sexo.MISTO, 50.0, 50.0, 50.0));
        // quartos.add(new Quarto(4, 10, Sexo.M, 30.0, 30.0, 0.0));
        // quartos.add(new Quarto(5, 10, Sexo.F, 30.0, 30.0, 0.0));

        // pacientes.add(new Paciente(1, "Joao", 65, Sexo.M, 20.0, 38.5, 95.0, 0.0));
        // pacientes.add(new Paciente(2, "Maria", 72, Sexo.F, 10.0, 39.0, 90.0, 10.0));
        // pacientes.add(new Paciente(3, "Carlos", 58, Sexo.M, 80.0, 39.5, 20.0, 100.0));
        // pacientes.add(new Paciente(4, "Patricia", 36, Sexo.F, 40.0, 37.0, 5.0, 0.0));
        // pacientes.add(new Paciente(5, "Bruno", 40, Sexo.M, 50.0, 38.0, 10.0, 0.0));
        // pacientes.add(new Paciente(6, "Camila", 26, Sexo.F, 30.0, 37.2, 10.0, 5.0));
        // pacientes.add(new Paciente(7, "Sofia", 30, Sexo.F, 10.0, 36.0, 0.0, 0.0));
        // pacientes.add(new Paciente(8, "Rafael", 47, Sexo.M, 60.0, 37.5, 20.0, 0.0));

        // ==========================================
        // TRIAGEM FUZZY
        // ==========================================
        Fuzzy motorFuzzy = new Fuzzy();
        for (Paciente p : pacientes) {
            motorFuzzy.realizarTriagem(p);
        }

        List<Individuo> populacao = algoritmoGenetico.init(pacientes, quartos.size());
        algoritmoGenetico.avaliarFitness(populacao, quartos);

        while (geracaoAtual < geracoes) {
            populacao = algoritmoGenetico.cruzamentos(populacao, quartos);
            algoritmoGenetico.avaliarFitness(populacao, quartos);
            Individuo melhor = Collections.max(populacao, Comparator.comparing(Individuo::getFitness));

            double porcentagem = (melhor.getFitness() / pacientes.size()) * 100.0;
            porcentagem = Math.round(porcentagem * 100.0) / 100.0;

            if (geracaoAtual % 100 == 0) {
                System.out.println("Geração " + geracaoAtual + ": melhor fitness = " + porcentagem + "%");
            }
            geracaoAtual++;
        }

        Individuo melhor = Collections.max(populacao, Comparator.comparing(Individuo::getFitness));
        algoritmoGenetico.imprimirAlocacao(melhor, quartos);
    }
}