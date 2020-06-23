package fi.tuska.antlife.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.log4j.Logger;

import fi.tuska.antlife.gui.GUI;
import fi.tuska.antlife.simulator.Cell;
import fi.tuska.antlife.simulator.Simulator;
import fi.tuska.antlife.util.Grid2D;

public class Controller implements KeyListener {

    private static final Logger log = Logger.getLogger(Controller.class);

    private static final int FORWARD_AMOUNT = 500;

    private final Simulator simulator;
    private final GUI gui;
    private volatile boolean running;

    private volatile float pauseSec = 0.01f;

    public Controller(Simulator simulator, GUI gui) {
        this.simulator = simulator;
        this.gui = gui;
        gui.addKeyListener(this);
    }

    public void setGrid(int x, int y) {
        Grid2D<Cell> grid = simulator.getGrid();
        Cell c = new Cell();
        c.set(simulator.getPhase(), true);
        grid.put(x, y, c);
    }

    public void start() {
        // Initialize grid
        // setGrid(1, 0);
        // setGrid(0, -2);
        // setGrid(-3, 0);
        // setGrid(0, 4);

        gui.start();
    }

    public void run() {
        running = true;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (running) {
                    gui.update();
                    pause();
                    synchronized (simulator) {
                        simulator.step();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void pause() {
        try {
            Thread.sleep((long) (pauseSec * 1000));
        } catch (InterruptedException e) {
        }
    }

    private void quit() {
        running = false;
        System.exit(0);
    }

    private void fastForward() {
        synchronized (simulator) {
            for (int i = 0; i < FORWARD_AMOUNT; i++) {
                simulator.step();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = Character.toLowerCase(e.getKeyChar());
        switch (c) {
        case 'q':
            quit();
            return;

        case 'f':
            fastForward();
            return;

        case '+':
            gui.zoom(true);
            return;
        case '-':
            gui.zoom(false);
            return;
        }

        switch (e.getKeyCode()) {
        case KeyEvent.VK_PAGE_UP:
            pauseSec *= 0.6666;
            log.info("Pause is now " + pauseSec + " seconds");
            return;
        case KeyEvent.VK_PAGE_DOWN:
            pauseSec /= 0.6666;
            log.info("Pause is now " + pauseSec + " seconds");
            return;

        case KeyEvent.VK_ESCAPE:
            quit();
            return;
        case KeyEvent.VK_LEFT:
            gui.movePosition(-1, 0);
            return;
        case KeyEvent.VK_RIGHT:
            gui.movePosition(1, 0);
            return;
        case KeyEvent.VK_UP:
            gui.movePosition(0, 1);
            return;
        case KeyEvent.VK_DOWN:
            gui.movePosition(0, -1);
            return;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
