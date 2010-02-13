package b2s.tictactoe;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static b2s.tictactoe.PointToGridResolver.GridLocation.*;
import static junit.framework.Assert.assertEquals;


public class PointToGridResolverTest {
    private PointToGridResolver resolver;

    @Before
    public void setUp() throws Exception {
        resolver = new PointToGridResolver(new Dimension(100, 100), 1);
    }

    @Test
    public void test_fromRowAndCol() {
        assertEquals(new Point(20, 60), resolver.resolve(0, 0));
        assertEquals(new Point(53, 60), resolver.resolve(0, 1));
        assertEquals(new Point(86, 60), resolver.resolve(0, 2));
        assertEquals(new Point(20, 93), resolver.resolve(1, 0));
        assertEquals(new Point(53, 93), resolver.resolve(1, 1));
        assertEquals(new Point(86, 93), resolver.resolve(1, 2));
        assertEquals(new Point(20, 126), resolver.resolve(2, 0));
        assertEquals(new Point(53, 126), resolver.resolve(2, 1));
        assertEquals(new Point(86, 126), resolver.resolve(2, 2));
    }

    @Test
    public void test_bottomRight_FromPoint() {
        assertEquals(BOTTOM_RIGHT, resolver.resolve(new Point(67, 67))); // upper-left
        assertEquals(BOTTOM_RIGHT, resolver.resolve(new Point(100, 67))); // upper-left
        assertEquals(BOTTOM_RIGHT, resolver.resolve(new Point(100, 100))); // lower-right
        assertEquals(BOTTOM_RIGHT, resolver.resolve(new Point(67, 100))); // lower-left
    }

    @Test
    public void test_bottomMid_FromPoint() {
        assertEquals(BOTTOM_MID, resolver.resolve(new Point(34, 67))); // upper-left
        assertEquals(BOTTOM_MID, resolver.resolve(new Point(65, 67))); // upper-right
        assertEquals(BOTTOM_MID, resolver.resolve(new Point(65, 100))); // lower-right
        assertEquals(BOTTOM_MID, resolver.resolve(new Point(34, 100))); // lower-left
    }

    @Test
    public void test_bottomLeft_FromPoint() {
        assertEquals(BOTTOM_LEFT, resolver.resolve(new Point(0, 67))); // upper-left
        assertEquals(BOTTOM_LEFT, resolver.resolve(new Point(32, 67))); // upper-right
        assertEquals(BOTTOM_LEFT, resolver.resolve(new Point(32, 100))); // bottom-right
        assertEquals(BOTTOM_LEFT, resolver.resolve(new Point(0, 100))); // bottom-left
    }

    @Test
    public void test_middleRight_FromPoint() {
        assertEquals(MID_RIGHT, resolver.resolve(new Point(67, 34))); // upper-left
        assertEquals(MID_RIGHT, resolver.resolve(new Point(100, 34))); // upper-right
        assertEquals(MID_RIGHT, resolver.resolve(new Point(100, 65))); // lower-right
        assertEquals(MID_RIGHT, resolver.resolve(new Point(67, 65))); // lower-left
    }

    @Test
    public void test_middleMiddle_FromPoint() {
        assertEquals(MID_MID, resolver.resolve(new Point(34, 34))); // upper-left
        assertEquals(MID_MID, resolver.resolve(new Point(34, 65))); // lower-left
        assertEquals(MID_MID, resolver.resolve(new Point(65, 65))); // lower-right
        assertEquals(MID_MID, resolver.resolve(new Point(65, 34))); // upper-right
    }

    @Test
    public void test_middleLeft_FromPoint() {
        assertEquals(MID_LEFT, resolver.resolve(new Point(0, 34))); // upper-left
        assertEquals(MID_LEFT, resolver.resolve(new Point(32, 34))); // upper-right
        assertEquals(MID_LEFT, resolver.resolve(new Point(0, 65))); // lower-left
        assertEquals(MID_LEFT, resolver.resolve(new Point(32, 65))); // lower-right
    }

    @Test
    public void test_topRight_FromPoint() {
        assertEquals(TOP_RIGHT, resolver.resolve(new Point(67, 0))); // upper-left
        assertEquals(TOP_RIGHT, resolver.resolve(new Point(67, 32))); // lower-left
        assertEquals(TOP_RIGHT, resolver.resolve(new Point(100, 0))); // upper-right
        assertEquals(TOP_RIGHT, resolver.resolve(new Point(100, 32))); // lower-right
    }

    @Test
    public void test_topMiddle_FromPoint() {
        assertEquals(TOP_MIDDLE, resolver.resolve(new Point(34, 0))); // upper-left
        assertEquals(TOP_MIDDLE, resolver.resolve(new Point(34, 32))); // lower-left
        assertEquals(TOP_MIDDLE, resolver.resolve(new Point(65, 0))); // upper-right
        assertEquals(TOP_MIDDLE, resolver.resolve(new Point(65, 32))); // lower-right
    }

    @Test
    public void test_topLeft_FromPoint() {
        assertEquals(TOP_LEFT, resolver.resolve(new Point(0, 0)));
        assertEquals(TOP_LEFT, resolver.resolve(new Point(32, 0)));
        assertEquals(TOP_LEFT, resolver.resolve(new Point(0, 32)));
        assertEquals(TOP_LEFT, resolver.resolve(new Point(32, 32)));
    }


}
