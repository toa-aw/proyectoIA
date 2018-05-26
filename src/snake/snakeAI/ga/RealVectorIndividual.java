package snake.snakeAI.ga;

import snake.Environment;

public abstract class RealVectorIndividual<P extends Problem, I extends RealVectorIndividual> extends Individual<P, I> {

    protected  double [] genome;
    public RealVectorIndividual(P problem, int size) {
        super(problem);

        this.genome = new double[size];
        for (int i = 0; i < size; i++) {
            genome[i] = Environment.random.nextDouble()*2-1;
        }
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) { //para que es necesario esto?
        super(original);
        this.genome = new double[original.getNumGenes()];
        System.arraycopy(original.genome, 0, this.genome, 0, this.getNumGenes());
    }

    @Override
    public int getNumGenes() {
        return this.genome.length;
    }

    public double getGene(int index) {
        return this.genome[index];
    }

    public void setGene(int index, double newValue) {
        this.genome[index] = newValue;
    }

    @Override
    public void swapGenes(RealVectorIndividual other, int index) {
        double geneThis = this.getGene(index);
        double geneOther = other.getGene(index);

        this.setGene(index, geneOther);
        other.setGene(index, geneThis);
    }
}
