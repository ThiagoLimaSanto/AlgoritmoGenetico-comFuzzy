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
        int geracoes = 10000;

        // ================
        // Melhor cenario
        // ================

        quartos.add(new Quarto(1, 3, Sexo.MISTO, Tipo.UTI, 3));

        quartos.add(new Quarto(2, 3, Sexo.MISTO, Tipo.ISOLAMENTO, 2));

        quartos.add(new Quarto(3, 3, Sexo.F, Tipo.NORMAL, 1));

        quartos.add(new Quarto(4, 4, Sexo.M, Tipo.NORMAL, 2));

        quartos.add(new Quarto(5, 3, Sexo.MISTO, Tipo.NORMAL, 1));

        pacientes.add(new Paciente(1, "João", Sexo.M, 65, 3, true, false));
        pacientes.add(new Paciente(2, "Maria", Sexo.F, 72, 3, true, false));
        pacientes.add(new Paciente(3, "Pedro", Sexo.M, 60, 3, true, false));

        pacientes.add(new Paciente(4, "Carlos", Sexo.M, 58, 2, false, true));
        pacientes.add(new Paciente(5, "Fernanda", Sexo.F, 49, 2, false, true));

        pacientes.add(new Paciente(6, "Patricia", Sexo.F, 36, 1, false, false));
        pacientes.add(new Paciente(7, "Beatriz", Sexo.F, 19, 1, false, false));
        pacientes.add(new Paciente(8, "Camila", Sexo.F, 26, 1, false, false));
        pacientes.add(new Paciente(9, "Sofia", Sexo.F, 30, 1, false, false));

        pacientes.add(new Paciente(10, "Bruno", Sexo.M, 40, 2, false, false));
        pacientes.add(new Paciente(11, "Lucas", Sexo.M, 40, 2, false, false));
        pacientes.add(new Paciente(12, "Rafael", Sexo.M, 47, 2, false, false));
        pacientes.add(new Paciente(13, "Thiago", Sexo.M, 41, 2, false, false));

        pacientes.add(new Paciente(14, "Marcos", Sexo.M, 22, 1, false, false));
        pacientes.add(new Paciente(15, "Juliana", Sexo.F, 29, 1, false, false));

        // ==============
        // SUPERLOTAÇÃO
        // ==============

        // quartos.add(new Quarto(1, 2, Sexo.MISTO, Tipo.UTI, 3));
        // quartos.add(new Quarto(2, 1, Sexo.MISTO, Tipo.ISOLAMENTO, 2));
        // quartos.add(new Quarto(3, 5, Sexo.MISTO, Tipo.NORMAL, 1));

        // pacientes.add(new Paciente(1, "João", Sexo.M, 65, 3, true, false));
        // pacientes.add(new Paciente(2, "Maria", Sexo.F, 72, 3, true, false));
        // pacientes.add(new Paciente(3, "Pedro", Sexo.M, 60, 3, true, false));

        // pacientes.add(new Paciente(4, "Carlos", Sexo.M, 58, 2, false, true));
        // pacientes.add(new Paciente(5, "Fernanda", Sexo.F, 49, 2, false, true));

        // pacientes.add(new Paciente(6, "Patricia", Sexo.F, 36, 2, false, false));
        // pacientes.add(new Paciente(7, "Beatriz", Sexo.F, 19, 2, false, false));
        // pacientes.add(new Paciente(8, "Bruno", Sexo.M, 40, 2, false, false));
        // pacientes.add(new Paciente(9, "Lucas", Sexo.M, 40, 2, false, false));

        // pacientes.add(new Paciente(10, "Camila", Sexo.F, 26, 1, false, false));
        // pacientes.add(new Paciente(11, "Sofia", Sexo.F, 30, 1, false, false));
        // pacientes.add(new Paciente(12, "Rafael", Sexo.M, 47, 1, false, false));
        // pacientes.add(new Paciente(13, "Thiago", Sexo.M, 41, 1, false, false));
        // pacientes.add(new Paciente(14, "Marcos", Sexo.M, 22, 1, false, false));
        // pacientes.add(new Paciente(15, "Juliana", Sexo.F, 29, 1, false, false));

        // ============
        // ABUNDÂNCIA
        // ============

        // quartos.add(new Quarto(1, 4, Sexo.M, Tipo.UTI, 3));
        // quartos.add(new Quarto(2, 4, Sexo.F, Tipo.UTI, 3));
        // quartos.add(new Quarto(3, 2, Sexo.MISTO, Tipo.ISOLAMENTO, 2));
        // quartos.add(new Quarto(4, 4, Sexo.F, Tipo.NORMAL, 1));
        // quartos.add(new Quarto(5, 4, Sexo.M, Tipo.NORMAL, 1));
        // quartos.add(new Quarto(6, 4, Sexo.MISTO, Tipo.NORMAL, 2));

        // pacientes.add(new Paciente(1, "João", Sexo.M, 65, 3, true, false));

        // pacientes.add(new Paciente(2, "Maria", Sexo.F, 72, 3, true, false));

        // pacientes.add(new Paciente(3, "Carlos", Sexo.M, 58, 2, false, true));

        // pacientes.add(new Paciente(4, "Patricia", Sexo.F, 36, 2, false, false));
        // pacientes.add(new Paciente(5, "Bruno", Sexo.M, 40, 2, false, false));

        // pacientes.add(new Paciente(6, "Camila", Sexo.F, 26, 1, false, false));
        // pacientes.add(new Paciente(7, "Sofia", Sexo.F, 30, 1, false, false));

        // pacientes.add(new Paciente(8, "Rafael", Sexo.M, 47, 1, false, false));


        // ===============
        // teste Massivo
        // ===============

        // quartos.add(new Quarto(1, 4, Sexo.MISTO, Tipo.UTI, 3)); // UTI Geral
        // quartos.add(new Quarto(2, 2, Sexo.F, Tipo.UTI, 3)); // UTI Feminina
        // quartos.add(new Quarto(3, 3, Sexo.MISTO, Tipo.ISOLAMENTO, 2)); // Isolamento Crítico
        // quartos.add(new Quarto(4, 8, Sexo.F, Tipo.NORMAL, 1)); // Ala Feminina Leve
        // quartos.add(new Quarto(5, 8, Sexo.M, Tipo.NORMAL, 2)); // Ala Masculina Semi-Intensiva
        // quartos.add(new Quarto(6, 6, Sexo.MISTO, Tipo.NORMAL, 1)); // Enfermaria Geral
        // quartos.add(new Quarto(7, 4, Sexo.MISTO, Tipo.NORMAL, 2)); // Ala de Observação

        // pacientes.add(new Paciente(1, "João", Sexo.M, 85, 3, true, false)); // Prioridade Máxima (UTI + 80 anos)
        // pacientes.add(new Paciente(2, "Maria", Sexo.F, 82, 3, true, false)); // Prioridade Máxima (UTI + 80 anos)
        // pacientes.add(new Paciente(3, "Pedro", Sexo.M, 45, 3, true, false));
        // pacientes.add(new Paciente(4, "Ana", Sexo.F, 30, 3, true, false));
        // pacientes.add(new Paciente(5, "Carlos", Sexo.M, 50, 3, true, false));
        // pacientes.add(new Paciente(6, "Zilda", Sexo.F, 75, 3, true, false));
        // pacientes.add(new Paciente(7, "Bruno", Sexo.M, 40, 3, false, true)); // Isolamento
        // pacientes.add(new Paciente(8, "Julia", Sexo.F, 22, 3, false, true)); // Isolamento
        // pacientes.add(new Paciente(9, "Marcos", Sexo.M, 68, 3, false, true)); // Isolamento Idoso
        // pacientes.add(new Paciente(10, "Heitor", Sexo.M, 12, 3, true, true)); // Caso Extremo (UTI + Isolamento)
        // pacientes.add(new Paciente(11, "Vitor", Sexo.M, 33, 3, true, false));
        // pacientes.add(new Paciente(12, "Lorena", Sexo.F, 29, 3, true, false));

        // for (int i = 13; i <= 27; i++) {
        //     Sexo sexo = (i % 2 == 0) ? Sexo.F : Sexo.M;
        //     int idade = 20 + (i * 2);
        //     pacientes.add(new Paciente(i, "P_U2_" + i, sexo, idade, 2, false, false));
        // }

        // for (int i = 28; i <= 40; i++) {
        //     Sexo sexo = (i % 2 == 0) ? Sexo.F : Sexo.M;
        //     pacientes.add(new Paciente(i, "P_U1_" + i, sexo, 18 + i, 1, false, false));
        // }

        List<Individuo> populacao = algoritmoGenetico.init(pacientes, quartos.size());
        algoritmoGenetico.avaliarFitness(populacao, quartos);
        while (geracaoAtual < geracoes) {
            populacao = algoritmoGenetico.cruzamentos(populacao, quartos);
            algoritmoGenetico.avaliarFitness(populacao, quartos);
            Individuo melhor = Collections.max(populacao, Comparator.comparing(Individuo::getFitness));
            if (geracaoAtual % 100 == 0) {
                double porcentagem = (melhor.getFitness() / 10000.0) * 100.0;
                porcentagem = Math.round(porcentagem * 100.0) / 100.0;
                System.out.println("Geração " + geracaoAtual + ": melhor fitness = " + porcentagem + "%");
            }

            geracaoAtual++;
        }

        Individuo melhor = Collections.max(populacao, Comparator.comparing(Individuo::getFitness));
        algoritmoGenetico.imprimirAlocacao(melhor, quartos);
    }
}
