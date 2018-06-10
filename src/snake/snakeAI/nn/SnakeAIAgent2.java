package snake.snakeAI.nn;

import snake.*;

import java.awt.*;

import static java.lang.Math.abs;

public class SnakeAIAgent2 extends SnakeAgent {

    final private int inputLayerSize;
    final private int hiddenLayerSize;
    final private int outputLayerSize;
    private static final int INPUTS_ONE = 1;
    private static final int INPUTS_CERO = 0;


    /**
     * Network inputs array.
     */
    final private int[] inputs;
    /**
     * Hiddden layer weights.
     */
    final private double[][] w1;
    /**
     * Output layer weights.
     */
    final private double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final private double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    final private double[] output;

    public SnakeAIAgent2(Cell cell, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Color color) {
        super(cell, color);
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        inputs = new int[inputLayerSize];
        inputs[inputs.length - 1] = -1; //bias entry
        w1 = new double[inputLayerSize][hiddenLayerSize]; // the bias entry for the hidden layer neurons is already counted in inputLayerSize variable
        w2 = new double[hiddenLayerSize + 1][outputLayerSize]; // + 1 due to the bias entry for the output neurons
        hiddenLayerOutput = new double[hiddenLayerSize + 1];
        hiddenLayerOutput[hiddenLayerSize] = -1; // the bias entry for the output neurons
        output = new double[outputLayerSize];
    }
    /**
     * Initializes the network's weights
     *
     * @param weights vector of weights comming from the individual.
     */
    public void setWeights(double[] weights) {
        int cont = 0;
        for (int i = 0; i < inputLayerSize; i++) {
            for (int j = 0; j < hiddenLayerSize; j++) {
                w1[i][j] = weights[cont];
                cont++;
            }

        }

        for (int i = 0; i < hiddenLayerSize + 1; i++) {
            for (int j = 0; j < outputLayerSize; j++) {
                w2[i][j] = weights[cont];
                cont++;
            }
        }
    }


    /**
     * Computes the output of the network for the inputs saved in the class
     * vector "inputs".
     */
    private double[] forwardPropagation(int[] instance) {

        //implemetar algoritmo de la liña a) del ejercicio backpropagation
        float somaPesada = 0;

        for (int i = 0; i < hiddenLayerSize; i++) {
            somaPesada = 0;
            for (int j = 0; j < inputLayerSize; j++) {
                somaPesada += instance[j] * w1[j][i];
            }
            hiddenLayerOutput[i] = sigmoide(somaPesada);
        }

        for (int i = 0; i < outputLayerSize; i++) {
            somaPesada = 0;
            for (int j = 0; j < hiddenLayerSize + 1; j++) {
                somaPesada += hiddenLayerOutput[j] * w2[j][i];
            }
            output[i] = sigmoide(somaPesada);
        }

        return output;
    }

    private double sigmoide(float somaPesada) {
        //return 1 / (1 + Math.pow(Math.E, -somaPesada));
        return 1 / (1 + Math.exp(-somaPesada));
    }


    @Override
    protected Action decide(Perception perception) {
        setInputs(perception);
        forwardPropagation(inputs);

        //output 0 = north
        //output 1 = south
        //output 2 = east
        //output 3 = west
        //still

        double highestOutput = 0;
        int direction = -1;

        for (int i = 0; i < output.length; i++) {
            if (output[i] > highestOutput) {
                highestOutput = output[i];
                direction = i;
            }
        }

        switch (direction) {
            case 0:
                return Action.NORTH;

            case 1:
                return Action.SOUTH;

            case 2:
                return Action.EAST;

            case 3:
                return Action.WEST;

            default:
                return null;
        }
    }

    private void setInputs(Perception perception) {

        Cell n = perception.getN();
        Cell s = perception.getS();
        Cell e = perception.getE();
        Cell w = perception.getW();
        Food food = perception.getFood();

//        if(food == null){
//            //TODO NULL pointer exception
//            return;
//        }

        int foodLine = food.getCell().getLine();
        int foodColumn = food.getCell().getColumn();

        int snakeColumn = this.getCell().getColumn();
        int snakeLine = this.getCell().getLine();

        /* inputs 0 = n
         * inputs 1 = s
         * inputs 2 = e
         * inputs 3 = w
         * inputs 4 = foodN
         * inputs 5 = foodS
         * inputs 6 = foodE
         * inputs 7 = foodW*/

        if ( n != null && !n.hasTail() && !n.hasAgent() ) {
            inputs[0] = INPUTS_ONE;
        } else {
            inputs[0] = INPUTS_CERO;
        }

        if (s != null && !s.hasTail() && !s.hasAgent()) {
            inputs[1] = INPUTS_ONE;
        } else {
            inputs[1] = INPUTS_CERO;
        }

        if (e != null && !e.hasTail() && !e.hasAgent() ) {
            inputs[2] = INPUTS_ONE;
        } else {
            inputs[2] = INPUTS_CERO;
        }

        if (w != null && !w.hasTail() && !w.hasAgent()) {
            inputs[3] = INPUTS_ONE;
        } else {
            inputs[3] = INPUTS_CERO;
        }

        if (n.hastFood()) {
            inputs[4] = INPUTS_ONE;
        } else {
            inputs[4] = INPUTS_CERO;
        }

        if ( s.hastFood()) {
            inputs[5] = INPUTS_ONE;
        } else {
            inputs[5] = INPUTS_CERO;
        }

        if ( e.hastFood()) {
            inputs[6] = INPUTS_ONE;
        } else {
            inputs[6] = INPUTS_CERO;
        }

        if ( w.hastFood()) {
            inputs[7] = INPUTS_ONE;
        } else {
            inputs[7] = INPUTS_CERO;
        }
        inputs[8] = abs(snakeColumn - foodColumn) + abs(snakeLine - foodLine);
    }
}
