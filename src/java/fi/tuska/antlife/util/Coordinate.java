package fi.tuska.antlife.util;

public class Coordinate implements Comparable<Coordinate> {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int hashCode() {
        return x * 45253151 + y;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Coordinate))
            return false;
        Coordinate c = (Coordinate) o;
        return c.x == x && c.y == y;
    }

    @Override
    public int compareTo(Coordinate c) {
        if (c.x == x && c.y == y)
            return 0;
        return x > c.x || (x == c.x && y > c.y) ? 1 : -1;
    }
}
