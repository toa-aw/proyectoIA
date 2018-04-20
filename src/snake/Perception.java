package snake;

public class Perception {
    private Cell n, s, e, w;
    private Food food;

    public Perception(Cell n, Cell s, Cell e, Cell w, Food food) {
        this.n = n;
        this.s = s;
        this.e = e;
        this.w = w;
        this.food = food;
    }

    public Cell getN() {
        return n;
    }

    public Cell getS() {
        return s;
    }

    public Cell getE() {
        return e;
    }

    public Cell getW() {
        return w;
    }

    public Food getFood() {
        return food;
    }
}
