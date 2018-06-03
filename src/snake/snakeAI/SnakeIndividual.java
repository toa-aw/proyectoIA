package snake.snakeAI;

import snake.Cell;
import snake.Environment;
import snake.SnakeAgent;
import snake.SnakeBehaviour;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private float somaComidas = 0;
    private float somaIteracoes = 0;

    public SnakeIndividual(SnakeProblem problem, int size) {
        super(problem, size);
    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        somaComidas = original.somaComidas;
        somaIteracoes = original.somaIteracoes;

    }


    @Override
    public double computeFitness() {
        int numEvironmentSimulations = problem.getNumEvironmentSimulations();
        somaComidas = 0;
        somaIteracoes = 0;
        List<SnakeAIAgent> listAI;

        for (int i = 0; i < numEvironmentSimulations; i++) {
            problem.getEnvironment().initialize(i);

            listAI = problem.getEnvironment().getSnakesAI();
            for (SnakeAIAgent aiAgent: listAI) {
                aiAgent.setWeights(genome);
            }

//            problem.getEnvironment().getSnakeAI().setWeights(genome);
            SnakeBehaviour snakeBehaviour = problem.getEnvironment().simulateHeadless();
            somaComidas += snakeBehaviour.getComidas();
            somaIteracoes += snakeBehaviour.getIteracoes();
        }

        somaComidas = somaComidas/ numEvironmentSimulations;
        somaIteracoes = somaIteracoes/ numEvironmentSimulations;

        fitness = somaComidas*1000 + somaIteracoes;
        return fitness;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        sb.append("\nfood: ");
        sb.append(somaComidas);
        sb.append("\nmovements: ");
        sb.append(somaIteracoes);
        sb.append("\ngenome: ");
        sb.append(Arrays.toString(genome));
        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(SnakeIndividual i) {
        if(i.getFitness() > this.fitness){
            return -1;
        }

        if(i.getFitness() < this.fitness){
            return 1;
        }
        return 0;
    }

    @Override
    public SnakeIndividual clone() {
        return new SnakeIndividual(this);
    }
}
