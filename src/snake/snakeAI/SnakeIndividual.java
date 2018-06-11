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
            //TODO coment/uncoment for one agent or homo
            listAI = problem.getEnvironment().getSnakesAI();
            for (SnakeAIAgent aiAgent: listAI) {
                aiAgent.setWeights(genome);
            }



            //TODO coment/uncoment for two hetero agents
//            int length = genome.length;
//            double[] genome1 = new double[(length+1)/2];
//            double[] genome2 = new double[(length+1)/2];
//
//            System.arraycopy(genome, 0, genome1, 0, genome1.length);
//            System.arraycopy(genome, genome1.length, genome2, 0, genome2.length);
//            listAI.get(0).setWeights(genome1);
//            listAI.get(1).setWeights(genome2);



            SnakeBehaviour snakeBehaviour = problem.getEnvironment().simulateHeadless();
            somaComidas += snakeBehaviour.getComidas();
            somaIteracoes += snakeBehaviour.getIteracoes();
        }

        somaComidas = somaComidas/ numEvironmentSimulations;
        somaIteracoes = somaIteracoes/ numEvironmentSimulations;

//        somaComidas2 = somaComidas2/ numEvironmentSimulations;
//        somaIteracoes2 = somaIteracoes2/ numEvironmentSimulations;
//
//        float fitness1 = (somaComidas*2000) + somaIteracoes;
//        float fitness2 = (somaComidas2*2000) + somaIteracoes2;
//
//        fitness = (fitness1+fitness2)/2 ;
        fitness = (somaComidas*1500) + somaIteracoes;
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
