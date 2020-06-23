package fi.tuska.antlife;

import fi.tuska.antlife.controller.Controller;
import fi.tuska.antlife.gui.GUI;
import fi.tuska.antlife.simulator.AntLifeSimulator;
import fi.tuska.antlife.simulator.Cell;
import fi.tuska.antlife.simulator.CellFactory;
import fi.tuska.antlife.simulator.Simulator;
import fi.tuska.antlife.util.Grid2D;

public class Runner {

    private static void gridSet(Grid2D<Cell> grid, int x, int y) {
        Cell c = grid.getNotNull(x, y);
        c.set(true);
    }

    @SuppressWarnings("unused")
    private static void initGrid(Grid2D<Cell> grid) {
        for (int y = -10; y < 10; y++) {
            for (int x = -10; x < 10; x++) {
                if (Math.random() < 0.5)
                    gridSet(grid, x, y);
            }
        }
    }

    public static void main(String[] args) {
        Grid2D<Cell> grid = new Grid2D<Cell>(new CellFactory());
        // initGrid(grid);
        // Simulator sim = new AntSimulator(grid);
        // Simulator sim = new LifeSimulator(grid);
         Simulator sim = new AntLifeSimulator(grid, 0, 4);
        GUI gui = new GUI(sim);
        Controller controller = new Controller(sim, gui);
        controller.start();
        controller.run();
    }

}
