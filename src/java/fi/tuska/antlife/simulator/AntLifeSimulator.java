package fi.tuska.antlife.simulator;

import fi.tuska.antlife.util.Grid2D;

public class AntLifeSimulator implements Simulator {

    private final Grid2D<Cell> grid;
    private final LifeSimulator lifeSim;
    private final AntSimulator antSim;
    private long turn = 0;
    private final int antPreTurns;
    private final int antMod;

    public AntLifeSimulator(Grid2D<Cell> grid, int antPreTurns, int antMod) {
        this.grid = grid;
        this.lifeSim = new LifeSimulator(grid);
        this.antSim = new AntSimulator(grid);
        this.antPreTurns = antPreTurns;
        this.antMod = antMod;
    }

    @Override
    public Grid2D<Cell> getGrid() {
        return grid;
    }

    @Override
    public boolean getPhase() {
        return lifeSim.getPhase();
    }

    @Override
    public void setPhase(boolean phase) {
        lifeSim.setPhase(phase);
    }

    @Override
    public void step() {
        antSim.setPhase(lifeSim.getPhase());
        antSim.step();
        turn++;

        if (turn > antPreTurns && ((turn % (long) antMod) == 0))
            lifeSim.step();
    }
}
