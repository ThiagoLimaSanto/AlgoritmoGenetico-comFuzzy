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

    public List<Individuo> init(List<Paciente> pacientes, int qtdQuantos) {
        List<Individuo> populacao = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 30; i++) {
            Map<Paciente, Integer> genes = new HashMap<>();

            for (Paciente p : pacientes) {
                int quarto = random.nextInt(qtdQuantos);
                genes.put(p, quarto);
            }

            Individuo individuo = new Individuo(genes);
            populacao.add(individuo);
        }

        return populacao;
    }

    public void avaliarFitness(List<Individuo> populacao, List<Quarto> quartos) {

        int MAX_FITNESS = 10000;

        for (Individuo individuo : populacao) {

            Map<Integer, Integer> ocupacao = new HashMap<>();
            int penalidade = 0;
            int qtdFila = 0;

            for (Paciente paciente : individuo.getGenes().keySet()) {

                Integer quartoId = individuo.getGenes().get(paciente);

                if (quartoId == 0) {
                    qtdFila++;
                } else {
                    ocupacao.put(quartoId, ocupacao.getOrDefault(quartoId, 0) + 1);
                }

                Quarto quarto = (quartoId == 0) ? null : quartos.get(quartoId - 1);

                if (quarto == null) {
                    switch (paciente.getGrauDeUrgencia()) {
                        case 3 -> penalidade += 700;
                        case 2 -> penalidade += 500;
                        case 1 -> penalidade += 100;
                    }

                    if (paciente.isPrecisaDeUti())
                        penalidade += 500;
                    if (paciente.isPrecisaDeIsolamento())
                        penalidade += 400;

                    if (paciente.getIdade() >= 80)
                        penalidade += 100;
                    else if (paciente.getIdade() >= 60)
                        penalidade += 50;

                } else {

                    if (paciente.getGrauDeUrgencia() > quarto.getNivelDeCuidado())
                        penalidade += 80;

                    if (paciente.isPrecisaDeUti() && quarto.getTipoDeUrgencia() != Tipo.UTI)
                        penalidade += 300;

                    if (paciente.isPrecisaDeIsolamento() && quarto.getTipoDeUrgencia() != Tipo.ISOLAMENTO)
                        penalidade += 250;

                    if (!quarto.getSexoPermitido().equals(Sexo.MISTO) &&
                            !paciente.getSexo().equals(quarto.getSexoPermitido())) {
                        penalidade += 120;
                    }

                    if (!paciente.isPrecisaDeUti() && quarto.getTipoDeUrgencia() == Tipo.UTI)
                        penalidade += 200;

                    if (!paciente.isPrecisaDeIsolamento() && quarto.getTipoDeUrgencia() == Tipo.ISOLAMENTO)
                        penalidade += 150;
                }
            }

            penalidade += qtdFila * 20;

            for (Quarto q : quartos) {
                int ocupados = ocupacao.getOrDefault(q.getId(), 0);
                if (ocupados > q.getCapacidade()) {
                    int excesso = ocupados - q.getCapacidade();
                    penalidade += excesso * 550;
                }
            }

            individuo.setFitness(Math.max(1, MAX_FITNESS - penalidade));
        }
    }

    public List<Individuo> cruzamentos(List<Individuo> populacao, List<Quarto> quartos) {
        List<Individuo> novaPopulacao = new ArrayList<>();
        Individuo melhor = Collections.max(populacao, Comparator.comparing(Individuo::getFitness));
        novaPopulacao.add(new Individuo(melhor));

        while (novaPopulacao.size() < populacao.size()) {
            Individuo pai1 = selecaoTorneio(populacao);
            Individuo pai2;
            Individuo filho;
            double aleatorio = Math.random();

            do {
                pai2 = selecaoTorneio(populacao);
            } while (pai1 == pai2);

            if (aleatorio < 0.8) {
                filho = crossover(pai1, pai2);
            } else if (aleatorio < 0.9) {
                filho = new Individuo(pai1);
            } else {
                filho = new Individuo(pai2);
            }

            mutacao(filho, quartos);

            novaPopulacao.add(filho);
        }

        return novaPopulacao;
    }

    private Individuo selecaoTorneio(List<Individuo> populacao) {
        Random random = new Random();

        Individuo a = populacao.get(random.nextInt(populacao.size()));
        Individuo b = populacao.get(random.nextInt(populacao.size()));
        Individuo c = populacao.get(random.nextInt(populacao.size()));

        Individuo melhor = a;

        if (b.getFitness() > melhor.getFitness())
            melhor = b;
        if (c.getFitness() > melhor.getFitness())
            melhor = c;

        return melhor;
    }

    private void mutacao(Individuo individuo, List<Quarto> quartos) {
        Random random = new Random();
        double taxaMutacao = 0.1; // 10% é bom

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

        List<Paciente> pacientes = new ArrayList<>(pai1.getGenes().keySet());
        pacientes.sort(Comparator.comparingInt(Paciente::getId));

        int ponto1 = random.nextInt(pacientes.size());
        int ponto2 = random.nextInt(pacientes.size());

        if (ponto1 > ponto2) {
            int temp = ponto1;
            ponto1 = ponto2;
            ponto2 = temp;
        }

        for (int i = 0; i < pacientes.size(); i++) {

            Paciente p = pacientes.get(i);

            if (i >= ponto1 && i <= ponto2) {
                filho.getGenes().put(p, pai1.getGenes().get(p));
            } else {
                filho.getGenes().put(p, pai2.getGenes().get(p));
            }
        }

        return filho;
    }

    public void imprimirAlocacao(Individuo individuo, List<Quarto> quartos) {

        System.out.println("=== Alocação do Individuo ===");
        System.out.printf("%-10s %-6s %-12s %-8s %-10s %-10s%n",
                "Paciente", "Idade", "Sexo", "Urgência", "UTI", "Isolamento");

        individuo.getGenes().keySet().stream()
                .sorted(Comparator.comparingInt(Paciente::getId))
                .forEach(paciente -> {

                    Integer quartoId = individuo.getGenes().get(paciente);
                    String local;

                    if (quartoId == 0) {
                        local = "Fila";
                    } else {

                        Quarto quarto = quartos.stream()
                                .filter(q -> q.getId() == quartoId)
                                .findFirst()
                                .orElse(null);

                        if (quarto == null) {
                            local = "Quarto não encontrado";
                        } else {
                            local = "Quarto " + quarto.getId() +
                                    " (" + quarto.getTipoDeUrgencia() + ")";
                        }
                    }

                    System.out.printf("%-10s %-6d %-12s %-8d %-10s %-10s -> %s%n",
                            paciente.getNome(),
                            paciente.getIdade(),
                            paciente.getSexo(),
                            paciente.getGrauDeUrgencia(),
                            paciente.isPrecisaDeUti() ? "Sim" : "Não",
                            paciente.isPrecisaDeIsolamento() ? "Sim" : "Não",
                            local);
                });
    }
}