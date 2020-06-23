package fi.tuska.antlife.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import fi.tuska.antlife.simulator.GridObjectFactory;

public class Grid2D<T> implements Iterable<Entry<Coordinate, T>> {

    private final Map<Coordinate, T> objects = new TreeMap<Coordinate, T>();

    private GridObjectFactory<T> factory;

    public Grid2D() {
    }

    public Grid2D(GridObjectFactory<T> factory) {
        this.factory = factory;
    }

    public T get(int x, int y) {
        return objects.get(new Coordinate(x, y));
    }

    public T getNotNull(int x, int y) {
        T object = objects.get(new Coordinate(x, y));
        if (object == null) {
            if (factory == null)
                throw new NullPointerException("Factory not defined");
            object = factory.create(x, y);
            if (object == null)
                throw new NullPointerException("Factory returned null object for " + x + ", " + y);

            put(x, y, object);
        }
        return object;
    }

    public boolean contains(int x, int y) {
        return objects.containsKey(new Coordinate(x, y));
    }

    public void remove(int x, int y) {
        objects.remove(new Coordinate(x, y));
    }

    public void put(int x, int y, T object) {
        objects.put(new Coordinate(x, y), object);
    }

    /**
     * @return neighbors (null if no value entered), with indexes:
     * 
     * <pre>
     * 0 1 2
     * 3 . 4
     * 5 6 7
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public T[] neighbors(int x, int y, Class<T> clazz) {
        T[] n = (T[]) Array.newInstance(clazz, 8);
        n[0] = get(x - 1, y + 1);
        n[1] = get(x, y + 1);
        n[2] = get(x + 1, y + 1);
        n[3] = get(x - 1, y);
        n[4] = get(x + 1, y);
        n[5] = get(x - 1, y - 1);
        n[6] = get(x, y - 1);
        n[7] = get(x + 1, y - 1);
        return n;
    }

    public void merge(Grid2D<T> other) {
        objects.putAll(other.objects);
    }

    @Override
    public Iterator<Entry<Coordinate, T>> iterator() {
        return objects.entrySet().iterator();
    }

}
