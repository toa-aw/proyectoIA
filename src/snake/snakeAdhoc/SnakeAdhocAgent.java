package snake.snakeAdhoc;

import snake.*;

import java.awt.*;


public class SnakeAdhocAgent extends SnakeAgent {
    // TODO

    public SnakeAdhocAgent(Cell cell, Color color) {
        super(cell, color);
    }

    @Override
    protected Action decide(Perception perception) {
        // TODO
        Cell n = perception.getN();
        Cell s = perception.getS();
        Cell e = perception.getE();
        Cell w = perception.getW();
        Cell foodPosition = perception.getFood().getCell();

        int foodLine = foodPosition.getLine();
        int foodColumn = foodPosition.getColumn();
        int snakeLine = this.getCell().getLine();
        int snakeColumn = this.getCell().getColumn();

        Action action = null;

        if (!s.hasAgent() && !s.hasTail() && snakeLine < foodLine){
            action = Action.SOUTH;
        }

        if (!n.hasTail() && !n.hasAgent() && snakeLine > foodLine){
            action = Action.NORTH;
        }

        if (!e.hasTail() && !e.hasAgent() && snakeColumn < foodColumn){
            action = Action.EAST;
        }

        if (!w.hasTail() && !w.hasAgent() && snakeColumn > foodColumn){
            action = Action.WEST;
        }

        return action;
    }

}
