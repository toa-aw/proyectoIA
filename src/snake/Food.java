package snake;

import java.awt.*;

public class Food {
    public Color color;

    private Cell cell;

    public Food(Cell cell) {
        this.cell = cell;
        this.color = Color.red;
        if (cell != null)
            this.cell.setFood(this);
    }

    public Color getColor() {
        return color;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        if (this.cell != null){
            this.cell.setFood(null);
        }

        this.cell = cell;
        if (cell != null){
            cell.setFood(this);
        }

    }
}
