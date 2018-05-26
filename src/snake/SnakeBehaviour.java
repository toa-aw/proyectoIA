package snake;

public class SnakeBehaviour {
    private int comidas;
    private int iteracoes;

    public SnakeBehaviour(int comidas, int iteracoes) {
        this.comidas = comidas;
        this.iteracoes = iteracoes;
    }

    public int getComidas() {
        return comidas;
    }

    public void setComidas(int comidas) {
        this.comidas = comidas;
    }

    public int getIteracoes() {
        return iteracoes;
    }

    public void setIteracoes(int iteracoes) {
        this.iteracoes = iteracoes;
    }
}
