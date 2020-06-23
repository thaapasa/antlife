package fi.tuska.antlife.simulator;

import fi.tuska.antlife.util.Grid2D;

public interface Simulator {

    void step();

    void setPhase(boolean phase);

    boolean getPhase();

    Grid2D<Cell> getGrid();

}
