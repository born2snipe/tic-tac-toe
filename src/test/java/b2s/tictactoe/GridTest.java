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
    public void test_isGameOver_CAT() {
        grid.move(0, 0, 'x');
        grid.move(0, 1, 'o');
        grid.move(0, 2, 'o');

        grid.move(1, 0, 'o');
        grid.move(1, 1, 'o');
        grid.move(1, 2, 'x');

        grid.move(2, 0, 'x');
        grid.move(2, 1, 'x');
        grid.move(2, 2, 'o');

        assertTrue(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_TopRightToLowerLeft_Broken() {
        grid.move(0, 2, 'x');
        grid.move(1, 1, 'o');
        grid.move(2, 0, 'x');

        assertFalse(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_TopRightToLowerLeft() {
        grid.move(0, 2, 'x');
        grid.move(1, 1, 'x');
        grid.move(2, 0, 'x');

        assertTrue(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_TopLeftToLowerRight() {
        grid.move(0, 0, 'x');
        grid.move(1, 1, 'x');
        grid.move(2, 2, 'x');

        assertTrue(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_TopLeftToLowerRight_Broken() {
        grid.move(0, 0, 'x');
        grid.move(1, 1, 'o');
        grid.move(2, 2, 'x');

        assertFalse(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_RightColumn() {
        grid.move(0, 2, 'x');
        grid.move(1, 2, 'x');
        grid.move(2, 2, 'x');

        assertTrue(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_MiddleColumn() {
        grid.move(0, 1, 'x');
        grid.move(1, 1, 'x');
        grid.move(2, 1, 'x');

        assertTrue(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_LeftColumn() {
        grid.move(0, 0, 'x');
        grid.move(1, 0, 'x');
        grid.move(2, 0, 'x');

        assertTrue(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_BottomRow() {
        grid.move(2, 0, 'x');
        grid.move(2, 1, 'x');
        grid.move(2, 2, 'x');

        assertTrue(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_MiddleRow() {
        grid.move(1, 0, 'x');
        grid.move(1, 1, 'x');
        grid.move(1, 2, 'x');

        assertTrue(grid.isGameOver());
    }

    @Test
    public void test_isGameOver_TopRow() {
        grid.move(0, 0, 'x');
        grid.move(0, 1, 'x');
        grid.move(0, 2, 'x');

        assertTrue(grid.isGameOver());
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
