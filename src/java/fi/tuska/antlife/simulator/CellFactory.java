package fi.tuska.antlife.simulator;

public class CellFactory implements GridObjectFactory<Cell> {

    @Override
    public Cell create(int x, int y) {
        return new Cell();
    }

}
