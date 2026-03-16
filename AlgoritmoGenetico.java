import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AlgoritmoGenetico {

    public AlgoritmoGenetico() {
    }

    public List<Individuo> init(List<Paciente> pacientes, int qtdQuartos) {
        List<Individuo> populacao = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 300; i++) {
            Map<Paciente, Integer> genes = new HashMap<>();
            for (Paciente p : pacientes) {
                int quarto = random.nextInt(qtdQuartos + 1);
                genes.put(p, quarto);
            }
            populacao.add(new Individuo(genes));
        }
        return populacao;
    }

    public void avaliarFitness(List<Individuo> populacao, List<Quarto> quartos) {
        Fuzzy fuzzy = new Fuzzy();

        for (Individuo individuo : populacao) {
            double fitnessTotal = 0;
            Map<Integer, Integer> ocupacao = new HashMap<>();

            for (Integer quartoId : individuo.getGenes().values()) {
                if (quartoId != 0) {
                    ocupacao.put(quartoId, ocupacao.getOrDefault(quartoId, 0) + 1);
                }
            }

            // Passo 2: Calcular adequação de cada paciente (gene)
            for (Map.Entry<Paciente, Integer> entry : individuo.getGenes().entrySet()) {
                Paciente paciente = entry.getKey();
                Integer quartoId = entry.getValue();

                double adequacao;
                if (quartoId == 0) {
                    adequacao = fuzzy.calcularFuzzy(paciente, null, 0);
                } else {
                    Quarto quarto = quartos.get(quartoId - 1);
                    int ocupacaoAtual = ocupacao.getOrDefault(quartoId, 0);
                    adequacao = fuzzy.calcularFuzzy(paciente, quarto, ocupacaoAtual);
                }
                fitnessTotal += adequacao;
            }
            individuo.setFitness(fitnessTotal);
        }
    }

    public List<Individuo> cruzamentos(List<Individuo> populacao, List<Quarto> quartos) {
        List<Individuo> novaPopulacao = new ArrayList<>();
        Individuo melhor = Collections.max(populacao, Comparator.comparing(Individuo::getFitness));
        novaPopulacao.add(new Individuo(melhor));

        while (novaPopulacao.size() < populacao.size()) {
            Individuo pai1 = selecaoTorneio(populacao);
            Individuo pai2;
            do {
                pai2 = selecaoTorneio(populacao);
            } while (pai1 == pai2);

            Individuo filho;
            double rand = Math.random();
            if (rand < 0.8) {
                filho = crossover(pai1, pai2);
            } else {
                filho = new Individuo(rand < 0.9 ? pai1 : pai2);
            }

            mutacao(filho, quartos);
            novaPopulacao.add(filho);
        }
        return novaPopulacao;
    }

    private Individuo selecaoTorneio(List<Individuo> populacao) {
        Random random = new Random();
        Individuo melhor = null;

        for (int i = 0; i < 10; i++) {
            Individuo competidor = populacao.get(random.nextInt(populacao.size()));
            if (melhor == null || competidor.getFitness() > melhor.getFitness()) {
                melhor = competidor;
            }
        }
        return melhor;
    }

    private void mutacao(Individuo individuo, List<Quarto> quartos) {
        Random random = new Random();
        double taxaMutacao = 0.1;
        for (Paciente p : individuo.getGenes().keySet()) {
            if (random.nextDouble() < taxaMutacao) {
                int novoQuarto = random.nextInt(quartos.size() + 1);
                individuo.getGenes().put(p, novoQuarto);
            }
        }
    }

    private Individuo crossover(Individuo pai1, Individuo pai2) {
        Individuo filho = new Individuo();
        Random random = new Random();
        for (Paciente p : pai1.getGenes().keySet()) {
            if (random.nextBoolean()) {
                filho.getGenes().put(p, pai1.getGenes().get(p));
            } else {
                filho.getGenes().put(p, pai2.getGenes().get(p));
            }
        }
        return filho;
    }

    public void imprimirAlocacao(Individuo individuo, List<Quarto> quartos) {
        Fuzzy fuzzy = new Fuzzy();

        System.out.println("\n" + "=".repeat(145));
        System.out.println(
                "                                     RELATÓRIO HOSPITALAR DE ALOCAÇÃO INTELIGENTE (SINTOMAS -> FUZZY -> AG)");
        System.out.println("=".repeat(145));

        System.out.printf("%-10s | %-4s | %-12s | %-5s | %-5s | %-5s | %-5s | %-10s | %-8s | %-8s | %-30s | %-6s%n",
                "PACIENTE", "IDD", "ANAMNESE", "DOR", "FEB", "RESP", "CONT", "URG.FUZZY", "SUP.NEC", "ISO.NEC",
                "DESTINO FINAL", "FIT");
        System.out.println("-".repeat(145));

        Map<Integer, Integer> ocupacao = new HashMap<>();
        individuo.getGenes().values().forEach(id -> {
            if (id != 0)
                ocupacao.put(id, ocupacao.getOrDefault(id, 0) + 1);
        });

        individuo.getGenes().entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getKey().getId()))
                .forEach(entry -> {
                    Paciente p = entry.getKey();
                    Integer quartoId = entry.getValue();
                    String destinoInfo;
                    double fitnessIndividual;

                    if (quartoId == 0) {
                        destinoInfo = "FILA DE ESPERA";
                        fitnessIndividual = fuzzy.calcularFuzzy(p, null, 0);
                    } else {
                        Quarto q = quartos.get(quartoId - 1);
                        int lotacao = ocupacao.getOrDefault(quartoId, 0);
                        destinoInfo = String.format("Quarto %d (Cuidado %.0f)", q.getId(), q.getNivelDeCuidado());
                        fitnessIndividual = fuzzy.calcularFuzzy(p, q, lotacao);
                    }

                    System.out.printf(
                            "%-10s | %-4d | %-12s | %-5.0f | %-5.1f | %-5.0f | %-5.0f | %-10.1f | %-8.0f | %-8.0f | %-30s | %-6.2f%n",
                            p.getNome().toUpperCase(),
                            p.getIdade(),
                            p.getSexo(),
                            p.getDor(),
                            p.getFebre(),
                            p.getDificuldadeRespiratoria(),
                            p.getRiscoContagio(),
                            p.getUrgenciaFinalFuzzy(),
                            p.getNecessidadeSuporte(),
                            p.getNecessidadeIsolamento(),
                            destinoInfo,
                            fitnessIndividual);
                });

        System.out.println("-".repeat(145));
        double fitnessTotal = (individuo.getFitness() / individuo.getGenes().size()) * 100.0;
        System.out.printf("EFICIÊNCIA GERAL DA ALOCAÇÃO: %.2f%% | Total de Pacientes: %d%n",
                fitnessTotal, individuo.getGenes().size());
        System.out.println("=".repeat(145) + "\n");
    }
}