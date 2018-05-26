package snake;

import java.awt.*;
import java.util.LinkedList;

public abstract class SnakeAgent {

    protected Cell cell;
    protected Color color;
    protected LinkedList<Tail> tails;
    protected int numIteraciones = 0;
    protected  int numComida = 0;
    protected boolean died = false;

    public SnakeAgent(Cell cell, Color color) {
        this.cell = cell;
        if (cell != null) {
            this.cell.setAgent(this);
        }
        this.color = color;
        tails = new LinkedList<>();
    }

    public void act(Environment environment) {
        Perception perception = buildPerception(environment);
        if(perception.getFood() == null){
            int i = 1;
        }
        Action action = decide(perception);
        execute(action, environment);
    }

    protected Perception buildPerception(Environment environment) {
        return new Perception(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell),
                environment.getFoodCell().getFood());
    }

    public int getNumIteraciones() {
        return numIteraciones;
    }

    public int getNumComida() {
        return numComida;
    }

    protected void execute(Action action, Environment environment) {

        Cell nextCell = null;

        if (action == Action.NORTH && cell.getLine() != 0) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH && cell.getLine() != environment.getNumLines() - 1) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST && cell.getColumn() != 0) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST && cell.getColumn() != environment.getNumColumns() - 1) {
            nextCell = environment.getEastCell(cell);
        }

        if (nextCell != null && !nextCell.hasAgent()) {
            if (nextCell.hastFood()) {
                environment.reload();
                numComida++;

            }
            setCell(nextCell);
            numIteraciones++;
        }
        else{
            died = true;
        }
    }

    protected abstract Action decide(Perception perception);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if (this.cell != null) {
            this.cell.setAgent(null);
        }
        Cell aux = this.cell;
        this.cell = newCell;

        if (newCell != null) {
            if (aux.hastFood()) {
                Tail tail = new Tail(this, aux);
                //newCell.setTail(tail);
                this.tails.add(tail);
                aux.setFood(null);
            } else if (!tails.isEmpty()) {
                Tail tail = new Tail(this, aux);
                this.tails.get(0).setCell(null);
                this.tails.remove();
                //this.cell.setTail(tail);
                this.tails.add(tail);
            }
            newCell.setAgent(this);
        }
    }

    public Color getColor() {
        return color;
    }

    public LinkedList<Tail> getTails() {
        return tails;
    }

    public void addTail(Tail tail) {
        tails.add(tail);
    }
}
