package snake.snakeAI;

import snake.Cell;
import snake.Environment;
import snake.SnakeAgent;
import snake.SnakeBehaviour;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

import java.util.Random;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private double somaComidas = 0;
    private double somaIteracoes = 0;

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
        //para cada simulaccion (utilizar la var de iteraciones como seed)
        //ir al genoma e buscar el peso de las sinapses y colocarlos red neuronal
        //mandar a la serpiente a decidir
        //colocar los inputs con los vlaors percepcionados

        //mandar lla cobra a iterar maximo de x veces
        //recoge las estatisticas



        for (int i = 0; i < problem.getNumEvironmentSimulations(); i++) {
            problem.getEnvironment().initialize(i);
            problem.getEnvironment().getSnakeAI().setWeights(genome);
            SnakeBehaviour snakeBehaviour = problem.getEnvironment().simulateHeadless();
//            environmentUpdated();
//            environment.simulate();
            somaComidas += snakeBehaviour.getComidas();
            somaIteracoes += snakeBehaviour.getIteracoes();
            snakeBehaviour.setComidas(0);
            snakeBehaviour.setIteracoes(0);
        }

        somaComidas = somaComidas/problem.getNumEvironmentSimulations();
        somaIteracoes = somaIteracoes/problem.getNumEvironmentSimulations();

        fitness = somaComidas*10 + somaIteracoes;
        return fitness;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        //TODO
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
