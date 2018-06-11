package snake;

import java.awt.*;

public class Tail {
    private SnakeAgent snakeAgent;
    private Cell cell;
    private Color color;

    public Tail(SnakeAgent snakeAgent, Cell cell, Color color) {
        this.snakeAgent = snakeAgent;
        this.color = color.brighter();
        this.cell = cell;
        if (cell != null)
            this.cell.setTail(this);
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if (this.cell != null) {
            this.cell.setTail(null);
        }
        this.cell = newCell;
        if (newCell != null) {
            newCell.setTail(this);
        }
    }

    public Color getColor() {
        return color;
    }
}
