package snake.snakeAdhoc;

import snake.Action;
import snake.Cell;
import snake.Perception;
import snake.SnakeAgent;

import java.awt.*;


public class SnakeAdhocAgent extends SnakeAgent {
    public SnakeAdhocAgent(Cell cell, Color color) {
        super(cell, color);
    }

    @Override
    protected Action decide(Perception perception) {
        Cell n = perception.getN();
        Cell s = perception.getS();
        Cell e = perception.getE();
        Cell w = perception.getW();
        Cell foodPosition = perception.getFood().getCell();

        int foodLine = foodPosition.getLine();
        int foodColumn = foodPosition.getColumn();
        int snakeLine = this.getCell().getLine();
        int snakeColumn = this.getCell().getColumn();

        if (foodLine > snakeLine) {
            if (s != null && !s.hasAgent() && !s.hasTail()) {
                return Action.SOUTH;
            }
            if (e != null && !e.hasTail() && !e.hasAgent()) {
                return Action.EAST;
            }
            if (w != null && !w.hasTail() && !w.hasAgent()) {
                return Action.WEST;
            }
        }

        if (foodLine < snakeLine) {
            if (n != null && !n.hasTail() && !n.hasAgent()) {
                return Action.NORTH;
            }
            if (e != null && !e.hasTail() && !e.hasAgent()) {
                return Action.EAST;
            }
            if (w != null && !w.hasTail() && !w.hasAgent()) {
                return Action.WEST;
            }
        }

        if (foodColumn > snakeColumn) {
            if (e != null && !e.hasTail() && !e.hasAgent()) {
                return Action.EAST;
            }
            if (n != null && !n.hasTail() && !n.hasAgent()) {
                return Action.NORTH;
            }
            if (s != null && !s.hasAgent() && !s.hasTail()) {
                return Action.SOUTH;
            }

        }

        if (foodColumn < snakeColumn) {
            if (w != null && !w.hasTail() && !w.hasAgent()) {
                return Action.WEST;
            }
            if (n != null && !n.hasTail() && !n.hasAgent()) {
                return Action.NORTH;
            }
            if (s != null && !s.hasAgent() && !s.hasTail()) {
                return Action.SOUTH;
            }
        }
        return null;
    }

}
