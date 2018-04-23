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
        Action action;
        switch (Environment.random.nextInt(4)) {
            case 1:
                action = Action.EAST;
                break;
            case 2:
                action = Action.WEST;
                break;
            case 3:
                action = Action.NORTH;
                break;
            default:
                action = Action.SOUTH;
        }
        if(action == Action.NORTH && (n == null || n.hasAgent() || n.hasTail())){
                action = Action.WEST;
        }
        if(action == Action.SOUTH && (s == null || s.hasAgent() || s.hasTail())){
            action = Action.EAST;
        }
        if(action == Action.WEST && (w == null || w.hasAgent() || w.hasTail())){
            action = Action.NORTH;
        }
        if(action == Action.EAST && (e == null || e.hasAgent() || e.hasTail())){
            action = Action.SOUTH;
        }

        if(s != null && s.hastFood())
            action = Action.SOUTH;
        if(e != null && e.hastFood())
            action = Action.EAST;
        if (w != null && w.hastFood())
            action = Action.WEST;
        if (n != null && n.hastFood())
            action = Action.NORTH;

        return action;
    }
}
