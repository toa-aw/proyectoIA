package snake;

import snake.snakeAI.SnakeProblem;
import snake.snakeAI.ga.GeneticAlgorithm;
import snake.snakeAI.nn.SnakeAIAgent;
import snake.snakeAdhoc.SnakeAdhocAgent;
import snake.snakeRandom.SnakeRandomAgent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Environment {

    public static Random random;
    private final Cell[][] grid;
    private final List<SnakeAgent> agents;
    private final int maxIterations;
    private Food food;

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public Environment(int size, int maxIterations) {

        this.maxIterations = maxIterations;

        this.grid = new Cell[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        this.agents = new ArrayList<>();
        this.random = new Random();

    }

    public List<SnakeAIAgent> getSnakesAI (){
        List<SnakeAIAgent> listAI = new ArrayList<>();
        for (SnakeAgent a : agents) {
            if (a instanceof SnakeAIAgent) {
                listAI.add((SnakeAIAgent) a);
            }
        }
        return listAI;
    }

    public SnakeAIAgent getSnakeAI (){
        for (SnakeAgent a : agents) {
            if (a instanceof SnakeAIAgent) {
                return  ((SnakeAIAgent) a);
            }
        }
        return null;
    }

    public List<SnakeAgent> getAgents() {
        return agents;
    }

    public void initialize(int seed) {
        random.setSeed(seed);
        reset();
        placeAgents();
        if (food == null){
            placeFood();
        }else{
            moveFood();
        }
    }

    private void reset() {
        for (SnakeAgent agent : agents) {
            for (Tail tail: agent.getTails()) {
                tail.getCell().setTail(null);
            }

            agent.getTails().clear();
            agent.setCell(null);
        }
        if(food != null){
            food.getCell().setFood(null);
        }

        agents.clear();
    }


    public void reload() {
        if (food == null){
            placeFood();
        }else{
            moveFood();
        }
    }

    private void placeAgents() {
        Cell agentCell =  grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
        Cell agentCell2 = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
        if(agentCell == agentCell2){
            agentCell2 = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
        }
        //TODO: Coment/ Uncoment for number of snakes and type of snake.
//        SnakeRandomAgent snakeRandomAgent = new SnakeRandomAgent(agentCell, Color.GREEN);
//        agents.add(snakeRandomAgent);

//        SnakeAdhocAgent snakeAdhocAgent1 = new SnakeAdhocAgent(agentCell, Color.BLACK);
//        agents.add(snakeAdhocAgent1);
//        SnakeAdhocAgent snakeAdhocAgent2 = new SnakeAdhocAgent(agentCell, Color.BLACK);
//        agents.add(snakeAdhocAgent2);
//

        SnakeAIAgent snakeAIAgent1 = new SnakeAIAgent(agentCell, SnakeProblem.NUM_NN_INPUTS, 10, SnakeProblem.NUM_NN_OUTPUTS, Color.BLUE);
        agents.add(snakeAIAgent1);

//        SnakeAIAgent snakeAIAgent2 = new SnakeAIAgent(agentCell2, SnakeProblem.NUM_NN_INPUTS, 10, SnakeProblem.NUM_NN_OUTPUTS, Color.MAGENTA);
//        agents.add(snakeAIAgent2);
    }


    public Cell[][] getGrid() {
        return grid;
    }

    private void placeFood() {
        boolean foodIsSet = false;
        while (!foodIsSet) {
            Cell cellAux = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
            if (!cellAux.hasAgent() && !cellAux.hasTail()) {
                food = new Food(cellAux);
                foodIsSet = true;
            }
        }

    }

    public void moveFood()
    {
        boolean foodIsSet = false;
        while (!foodIsSet) {
            Cell cellAux = grid[random.nextInt(grid.length)][random.nextInt(grid.length)];
            if (!cellAux.hasAgent() && !cellAux.hasTail()) {
                food.setCell(cellAux);
                foodIsSet = true;
            }
        }
    }
    public void simulate() {
        for (int i = 0; i < 1000; i++) {
            if(agents.get(0).died){
                break;
            }
            for (SnakeAgent agent : agents) {
                agent.act(this);
                fireUpdatedEnvironment();
            }
        }
    }

    public SnakeBehaviour simulateHeadless() {
        for (int i = 0; i < maxIterations; i++) {
            if(agents.get(0).died ){
                break;
            }
            for (SnakeAgent agent : agents) {
                agent.act(this);
            }
        }
        //TODO coment/ uncoment for 1 or two snakes
//        int comidas = 0;
//        int movimientos = 0;
//        for (SnakeAgent agent : agents) {
//            comidas += agent.getNumComida();
//            movimientos += agent.getNumIteraciones();
//        }
//        comidas = comidas/2;
//        movimientos = movimientos/2;
        //return new snakeBehaviour(comidas, movimientos)
        return new SnakeBehaviour(agents.get(0).getNumComida(), agents.get(0).getNumIteraciones());
    }

    public Food getFood() {
        return food;
    }

    public int getSize() {
        return grid.length;
    }

    public Cell getNorthCell(Cell cell) {
        if (cell.getLine() > 0) {
            return grid[cell.getLine() - 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getSouthCell(Cell cell) {
        if (cell.getLine() < grid.length - 1) {
            return grid[cell.getLine() + 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getEastCell(Cell cell) {
        if (cell.getColumn() < grid[0].length - 1) {
            return grid[cell.getLine()][cell.getColumn() + 1];
        }
        return null;
    }

    public Cell getWestCell(Cell cell) {
        if (cell.getColumn() > 0) {
            return grid[cell.getLine()][cell.getColumn() - 1];
        }
        return null;
    }

    public Cell getFoodCell() {
        return food.getCell();
    }

    public int getNumLines() {
        return grid.length;
    }

    public int getNumColumns() {
        return grid[0].length;
    }

    public final Cell getCell(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public Color getCellColor(int linha, int coluna) {
        return grid[linha][coluna].getColor();
    }

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }
}
