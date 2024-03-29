package snake.snakeAI;

import snake.Environment;
import snake.snakeAI.ga.Problem;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SnakeProblem implements Problem<SnakeIndividual> {
    public static final int NUM_NN_INPUTS = 9;
    public static final int NUM_NN_INPUTS2 = 10;
    public static final int NUM_NN_OUTPUTS = 4;
    public static final int GENOME_SIZE = 134; // TODO change this number 134(1 agent or homo) 268 for (2 agents hetere)
    final private int numOutputs;
    final private int environmentSize;
    final private int maxIterations;
    final private int numInputs;
    final private int numHiddenUnits;
    final private int numEnvironmentRuns;

    final private Environment environment;

    public SnakeProblem(int environmentSize, int maxIterations, int numHiddenUnits, int numEnvironmentRuns) {
        this.environmentSize = environmentSize;
        this.maxIterations = maxIterations;
        this.numInputs = NUM_NN_INPUTS;
        this.numHiddenUnits = numHiddenUnits;
        this.numOutputs = NUM_NN_OUTPUTS;
        this.numEnvironmentRuns = numEnvironmentRuns;

        environment = new Environment(environmentSize, maxIterations);
    }



    // MODIFY IF YOU DEFINE OTHER PARAMETERS
    public static SnakeProblem buildProblemFromFile(File file) throws IOException {
        java.util.Scanner f = new java.util.Scanner(file);

        List<String> lines = new LinkedList<>();

        while (f.hasNextLine()) {
            String s = f.nextLine();
            if (!s.equals("") && !s.startsWith("//")) {
                lines.add(s);
            }
        }

        List<String> parametersValues = new LinkedList<>();
        for (String line : lines) {
            String[] tokens = line.split(":");
            parametersValues.add(tokens[1].trim());
        }

        int environmentSize = Integer.parseInt(parametersValues.get(0));
        int maxIterations = Integer.parseInt(parametersValues.get(1));
        int numHiddenUnits = Integer.parseInt(parametersValues.get(2));
        int numEnvironmentRuns = Integer.parseInt(parametersValues.get(3));

        return new SnakeProblem(environmentSize, maxIterations, numHiddenUnits, numEnvironmentRuns);
    }

    @Override
    public SnakeIndividual getNewIndividual() {
        return new SnakeIndividual(this, GENOME_SIZE);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public int getNumEvironmentSimulations() {
        return numEnvironmentRuns;
    }

    // MODIFY IF YOU DEFINE OTHER PARAMETERS
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Environment size: ");
        sb.append(environmentSize);
        sb.append("\n");
        sb.append("Maximum number of iterations: ");
        sb.append(maxIterations);
        sb.append("\n");
        sb.append("Number of inputs: ");
        sb.append(numInputs);
        sb.append("\n");
        sb.append("Number of hidden units: ");
        sb.append(numHiddenUnits);
        sb.append("\n");
        sb.append("Number of outputs: ");
        sb.append(numOutputs);
        sb.append("\n");
        sb.append("Number of environment simulations: ");
        sb.append(numEnvironmentRuns);
        return sb.toString();
    }

    public int getNumOutputs() {
        return numOutputs;
    }

    public int getEnvironmentSize() {
        return environmentSize;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public int getNumInputs() {
        return numInputs;
    }

    public int getNumHiddenUnits() {
        return numHiddenUnits;
    }

    public int getNumEnvironmentRuns() {
        return numEnvironmentRuns;
    }
}
