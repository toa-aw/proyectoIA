package snake.snakeRandom;

import snake.*;

import java.awt.*;

public class SnakeRandomAgent extends SnakeAgent {
    public SnakeRandomAgent(Cell cell, Color color) {
        super(cell, color);
    }

    @Override
    protected Action decide(Perception perception) {
        Cell n = perception.getN();
        Cell s = perception.getS();
        Cell e = perception.getE();
        Cell w = perception.getW();
        Action action = generateAction();

        if (action == Action.NORTH) {
            if (n == null || n.hasAgent() || n.hasTail()) {
                decide(perception);
            }
        }
        if (action == Action.SOUTH) {
            if (s == null || s.hasAgent() || s.hasTail()) {
                decide(perception);
            }
        }
        if (action == Action.WEST) {
            if (w == null || w.hasAgent() || w.hasTail()) {
                decide(perception);
            }
        }
        if (action == Action.EAST) {
            if (e == null || e.hasAgent() || e.hasTail()) {
                decide(perception);
            }
        }

        return action;
    }

    protected Action generateAction() {
        switch (Environment.random.nextInt(4)) {
            case 1:
                return Action.EAST;
            case 2:
                return Action.WEST;
            case 3:
                return Action.NORTH;
            default:
                return Action.SOUTH;
        }
    }
}
