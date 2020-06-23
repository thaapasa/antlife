package fi.tuska.antlife.gui;

import java.awt.FlowLayout;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import fi.tuska.antlife.simulator.Simulator;

public class GUI {

    private final JFrame frame;
    private final Renderer renderer;
    private final Simulator simulator;

    private int posX = 0;
    private int posY = 0;

    public GUI(Simulator simulator) {
        this.simulator = simulator;
        frame = new JFrame("Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());
        this.renderer = new Renderer(700, 700);

        frame.getContentPane().add(renderer);
    }

    public void addKeyListener(KeyListener listener) {
        frame.addKeyListener(listener);
    }

    public void start() {
        frame.pack();
        frame.setVisible(true);
    }

    public void update() {
        renderer.render(simulator.getGrid(), simulator.getPhase(), posX, posY);
        renderer.repaint();
    }

    public void movePosition(int dx, int dy) {
        posX += dx;
        posY += dy;
    }

    public void zoom(boolean in) {
        renderer.zoom(in);
    }
    
}
