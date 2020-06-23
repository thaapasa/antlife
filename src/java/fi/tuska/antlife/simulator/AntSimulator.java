package fi.tuska.antlife.simulator;

import fi.tuska.antlife.util.Grid2D;

public class AntSimulator implements Simulator {

    private final Grid2D<Cell> grid;

    /** 0=left, 1=down, 2=right, 3=up */
    private int dir;
    private int x;
    private int y;
    private boolean phase;

    public AntSimulator(Grid2D<Cell> grid) {
        this.grid = grid;

        x = 0;
        y = 0;
        Cell c = grid.getNotNull(x, y);
        c.setSpecial(true);
    }

    @Override
    public Grid2D<Cell> getGrid() {
        return grid;
    }

    @Override
    public void step() {
        // Turn ant, toggle tile
        Cell c = grid.getNotNull(x, y);
        if (c.get(phase)) {
            dir = (dir + 1) % 4;
        } else {
            dir = (dir + 3) % 4;
        }
        c.toggle(phase);
        c.setSpecial(false);

        // Move ant
        switch (dir) {
        case 0:
            x++;
            break;
        case 1:
            y--;
            break;
        case 2:
            x--;
            break;
        case 3:
            y++;
            break;
        default:
            throw new RuntimeException("Invalid direction " + dir);
        }
        c = grid.getNotNull(x, y);
        c.setSpecial(true);
    }

    @Override
    public void setPhase(boolean phase) {
        this.phase = phase;
    }

    @Override
    public boolean getPhase() {
        return phase;
    }

}
