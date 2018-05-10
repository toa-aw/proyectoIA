package snake.snakeAI;

import snake.snakeAI.ga.RealVectorIndividual;

public class SnakeIndividual extends RealVectorIndividual<SnakeProblem, SnakeIndividual> {

    private int numberOfMovements;
    private int amountOfFoodEaten;

    public SnakeIndividual(SnakeProblem problem, int size /*TODO?*/) {
        super(problem, size);
        //TODO?
    }

    public SnakeIndividual(SnakeIndividual original) {
        super(original);
        this.amountOfFoodEaten = original.getAmountOfFoodEaten();
        this.numberOfMovements = original.getNumberOfMovements();

    }

    public int getNumberOfMovements() {
        return numberOfMovements;
    }

    public int getAmountOfFoodEaten() {
        return amountOfFoodEaten;
    }

    @Override
    public double computeFitness() {
        double fitness = 0;

        if(this.numberOfMovements > 0){
            fitness += this.numberOfMovements;
        }
        if(this.amountOfFoodEaten > 0){
           fitness += amountOfFoodEaten * 10;
        }

        return fitness;
    }

    public double[] getGenome() {
        return this.genome;
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
