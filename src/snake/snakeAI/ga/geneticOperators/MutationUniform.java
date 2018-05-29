package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;

import java.util.concurrent.ThreadLocalRandom;

public class MutationUniform<I extends RealVectorIndividual> extends Mutation<I> {
    public MutationUniform(double probability) {
        super(probability);
    }

    public void run(I ind) {
        for (int i = 0; i < ind.getNumGenes(); i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability){
                double alelo = GeneticAlgorithm.random.nextDouble() * 2 - 1;
                ind.setGene(i, alelo);
            }
        }
    }

    @Override
    public String toString(){
        return "Uniform mutation (" + probability + ")";
    }
}
