package snake.snakeAI.ga.geneticOperators;

import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.ga.RealVectorIndividual;
import snake.snakeAI.ga.utils.Maths;

//PLEASE, MODIFY THE CLASS NAME
public class MutationGaussian<I extends RealVectorIndividual> extends Mutation<I> {

    public MutationGaussian(double probability ) {
        super(probability);
    }

    @Override
    public void run(I ind) {
        double[] genes = new double[ind.getNumGenes()];
        double sd;
        double media;

        for (int i = 0; i < ind.getNumGenes(); i++){
            genes[i] = ind.getGene(i);
        }

        media = Maths.average(genes);
        sd = Maths.standardDeviation(genes, media);

        for (int i = 0; i < ind.getNumGenes(); i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                double alelo = ind.getGene(i);
                alelo = alelo + 0.5 * GeneticAlgorithm.random.nextGaussian();
                ind.setGene(i, alelo);
            }
        }
    }

    @Override
    public String toString() {
        return "Gaussian Mutation (" + probability ;
    }
}