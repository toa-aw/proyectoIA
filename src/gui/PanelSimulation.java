package gui;

import snake.Environment;
import snake.EnvironmentListener;
import snake.snakeAI.SnakeIndividual;
import snake.snakeAI.nn.SnakeAIAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PanelSimulation extends JPanel implements EnvironmentListener {

    public static final int PANEL_SIZE = 250;
    public static final int CELL_SIZE = 20;
    public static final int GRID_TO_PANEL_GAP = 20;
    final JButton buttonSimulate = new JButton("Simulate");
    MainFrame mainFrame;
    JPanel environmentPanel = new JPanel();
    private Environment environment;
    private Image image;

    public PanelSimulation(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        environmentPanel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        setLayout(new BorderLayout());

        add(environmentPanel, java.awt.BorderLayout.NORTH);
        add(buttonSimulate, java.awt.BorderLayout.SOUTH);
        buttonSimulate.addActionListener(new SimulationPanel_jButtonSimulate_actionAdapter(this));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setJButtonSimulateEnabled(boolean enabled) {
        buttonSimulate.setEnabled(enabled);
    }

    public void jButtonSimulate_actionPerformed(ActionEvent e) {

        environment = mainFrame.getProblem().getEnvironment();
        environment.addEnvironmentListener(this);

        buildImage(environment);

        final PanelSimulation simulationPanel = this;

        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {

                int environmentSimulations = mainFrame.getProblem().getNumEvironmentSimulations();
                SnakeIndividual bestInRun = mainFrame.getBestInRun();
                List<SnakeAIAgent> listAI;
                //TODO coment/uncoment for IA agents (heterogeneus)
//                double[] genome = bestInRun.getGenome();
//                double[] genome1 = new double[(genome.length+1)/2];
//                double[] genome2 = new double[(genome.length+1)/2];

                for (int i = 0; i < environmentSimulations; i++) {
                    environment.initialize(i);
                    environmentUpdated();

                    listAI = environment.getSnakesAI();
                    //TODO coment/uncoment for IA agents
                    if(!listAI.isEmpty()){
                        //TODO coment/uncoment for one IA agent or two homogeneus
                        for (SnakeAIAgent aiAgent: listAI) {
                            aiAgent.setWeights(bestInRun.getGenome());
                        }
                        //TODO coment/uncoment for IA agents (heterogeneus)
//                        System.arraycopy(genome, 0, genome1, 0, genome1.length);
//                        System.arraycopy(genome, genome1.length, genome2, 0, genome2.length);
//                        listAI.get(0).setWeights(genome1);
//                        listAI.get(1).setWeights(genome2);
                    }
                    environment.simulate();
                }
                return null;
            }

            @Override
            public void done() {
                environment.removeEnvironmentListener(simulationPanel);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException ignore) {
                }

            }
        };
        worker.execute();
    }

    public void buildImage(Environment environment) {
        image = new BufferedImage(
                environment.getSize() * CELL_SIZE + 1,
                environment.getSize() * CELL_SIZE + 1,
                BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void environmentUpdated() {
        int n = environment.getSize();
        Graphics g = image.getGraphics();

        //Fill the cells color
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                g.setColor(environment.getCellColor(y, x));
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        //Draw the grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= n; i++) {
            g.drawLine(0, i * CELL_SIZE, n * CELL_SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, n * CELL_SIZE);
        }

        g = environmentPanel.getGraphics();
        g.drawImage(image, GRID_TO_PANEL_GAP, GRID_TO_PANEL_GAP, null);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignore) {
        }
    }
}

//--------------------
class SimulationPanel_jButtonSimulate_actionAdapter implements ActionListener {

    final private PanelSimulation adaptee;

    SimulationPanel_jButtonSimulate_actionAdapter(PanelSimulation adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonSimulate_actionPerformed(e);
    }
}
