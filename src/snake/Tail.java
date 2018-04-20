package snake;

import java.awt.*;

public class Tail {
    private SnakeAgent snakeAgent;
    private Cell cell;
    private Color color;

    public Tail(SnakeAgent snakeAgent, Cell cell) {
        this.snakeAgent = snakeAgent;
        this.cell = cell;
        this.color = snakeAgent.getColor();
    }

    public Color getColor(){
        return color;
    }
}
