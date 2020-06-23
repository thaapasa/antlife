package fi.tuska.antlife.simulator;

import java.util.Iterator;
import java.util.Map.Entry;

import fi.tuska.antlife.util.Coordinate;
import fi.tuska.antlife.util.Grid2D;

public class LifeSimulator implements Simulator {

    private final Grid2D<Cell> grid;
    private boolean phase;

    public LifeSimulator(Grid2D<Cell> grid) {
        this.grid = grid;
        this.phase = false;
    }

    @Override
    public Grid2D<Cell> getGrid() {
        return grid;
    }

    @Override
    public boolean getPhase() {
        return phase;
    }

    @Override
    public void setPhase(boolean phase) {
        this.phase = phase;
    }

    @Override
    public void step() {
        phase = !phase;

        Grid2D<Cell> additions = new Grid2D<Cell>();

        // Toggle all cells, add border areas
        for (Entry<Coordinate, Cell> e : grid) {
            Coordinate coord = e.getKey();
            Cell cell = e.getValue();

            toggleCell(cell, coord.x(), coord.y(), 1, additions);
        }

        // Compact border areas (remove non-alive cells)
        Iterator<Entry<Coordinate, Cell>> it = additions.iterator();
        while (it.hasNext()) {
            Entry<Coordinate, Cell> e = it.next();
            if (!e.getValue().get(phase)) it.remove();
        }
        
        // Merge additions
        grid.merge(additions);
    }

    private void toggleCell(Cell cell, int x, int y, int recursion, Grid2D<Cell> additions) {
        int liveCount = 0;

        if (cell == null) {
            cell = new Cell();
            additions.put(x, y, cell);
        }

        liveCount += liveCount(x - 1, y + 1, recursion, additions);
        liveCount += liveCount(x, y + 1, recursion, additions);
        liveCount += liveCount(x + 1, y + 1, recursion, additions);
        liveCount += liveCount(x - 1, y, recursion, additions);
        liveCount += liveCount(x + 1, y, recursion, additions);
        liveCount += liveCount(x - 1, y - 1, recursion, additions);
        liveCount += liveCount(x, y - 1, recursion, additions);
        liveCount += liveCount(x + 1, y - 1, recursion, additions);

        boolean wasAlive = cell.get(!phase);
        boolean isAlive = liveCount == 3 || (wasAlive && liveCount == 2);
        cell.set(phase, isAlive);
    }

    private int liveCount(int x, int y, int recursion, Grid2D<Cell> additions) {
        Cell cell = grid.get(x, y);
        if (cell != null)
            return cell.get(!phase) ? 1 : 0;

        // cell is null
        if (recursion > 0 && !additions.contains(x, y)) {
            toggleCell(null, x, y, recursion - 1, additions);
        }
        return 0;
    }

}
