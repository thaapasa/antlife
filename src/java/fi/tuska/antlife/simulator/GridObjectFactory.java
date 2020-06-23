package fi.tuska.antlife.simulator;

public interface GridObjectFactory<T> {

    T create(int x, int y);

}
