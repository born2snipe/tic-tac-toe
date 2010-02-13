package b2s.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;


public class GridTest {
    private Grid grid;

    @Before
    public void setUp() throws Exception {
        grid = new Grid();
    }

    @Test
    public void test_sameMove() {
        assertTrue(grid.move(0, 0));
        assertFalse(grid.move(0, 0));
        assertEquals('x', grid.cells[0][0]);
    }

    @Test
    public void test_singleMove() {
        assertTrue(grid.move(0, 0));
        assertEquals('x', grid.cells[0][0]);
        assertEquals('o', grid.whosMove());
        assertTrue(grid.move(1, 0));
        assertEquals('o', grid.cells[1][0]);
        assertEquals('x', grid.whosMove());
    }


}
