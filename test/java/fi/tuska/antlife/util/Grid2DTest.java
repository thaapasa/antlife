package fi.tuska.antlife.util;

import junit.framework.TestCase;

public class Grid2DTest extends TestCase {

    public void testOperations() {
        Grid2D<Integer> g = new Grid2D<Integer>();

        g.put(0, 1, 1);
        g.put(1, 1, 2);
        g.put(0, 0, 0);
        g.put(5, 4, 5);

        assertEquals(0, g.get(0, 0).intValue());
        assertEquals(2, g.get(1, 1).intValue());
        assertEquals(1, g.get(0, 1).intValue());
        assertEquals(5, g.get(5, 4).intValue());
        assertNull(g.get(1, 0));
        assertNull(g.get(4, 5));
    }
    
    public void testNeighbors() {
        Grid2D<Integer> g = new Grid2D<Integer>();
        g.put(0, 0, 0);
        g.put(0, 1, 1);
        g.put(1, 1, 2);
        g.put(-1, 0, -1);
        
        Integer[] n = g.neighbors(0, 0, Integer.class);
        assertNull(n[0]);
        assertEquals(1, n[1].intValue());
        assertEquals(2, n[2].intValue());
        assertEquals(-1, n[3].intValue());
        assertNull(n[4]);
        assertNull(n[5]);
        assertNull(n[6]);
        assertNull(n[7]);
    }

}
