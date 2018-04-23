package snake;

import java.awt.*;

public class Tail {
    private SnakeAgent snakeAgent;
    private Cell cell;
    private Color color;

    public Tail(SnakeAgent snakeAgent, Cell cell) {
        this.snakeAgent = snakeAgent;
        this.color = snakeAgent.getColor();
        this.cell = cell;
        if (cell != null)
            this.cell.setTail(this);
    }

    public Color getColor(){
        return color;
    }
}
