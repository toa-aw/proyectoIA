package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.ga.experiments.Experiment;
import snake.snakeAI.ga.experiments.ExperimentsFactory;

import java.io.IOException;

public class MutationNormal<I extends RealVectorIndividual> extends Mutation<I> {
    private Double delta;

    public MutationNormal(double probability, double delta) {
        super(probability);
        this.delta = delta;
    }

    @Override
    public void run(I ind) {
        for (int i = 0; i < ind.getNumGenes(); i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability){
                double alelo = ind.getGene(i);
                alelo = alelo + (GeneticAlgorithm.random.nextDouble() * 2 - 1) * delta;
                ind.setGene(i, alelo);
            }
        }
    }

    @Override
    public String toString(){
        return "Normal mutation (" + probability + ")";
    }
}
