package b2s.tictactoe;

import java.awt.*;

import static b2s.tictactoe.PointToGridResolver.GridLocation.*;


public class PointToGridResolver {
    private Dimension gridSize;
    private int lineWidth;
    private final int cellSize;

    public PointToGridResolver(Dimension gridSize, int lineWidth) {
        this.gridSize = gridSize;
        this.lineWidth = lineWidth;
        cellSize = gridSize.width / 3 - lineWidth;
    }

    public GridLocation resolve(Point point) {
        int x = point.x;
        int y = point.y;

        int leftLineX = cellSize + lineWidth;
        int rightLineX = cellSize * 2 + lineWidth * 2;
        int topLineY = cellSize + lineWidth;
        int bottomLineY = cellSize * 2 + lineWidth * 2;

        if (x <= cellSize && y < topLineY) {
            return TOP_LEFT;
        } else if (x > leftLineX && x < rightLineX && y < topLineY) {
            return TOP_MIDDLE;
        } else if (x >= rightLineX && y < topLineY) {
            return TOP_RIGHT;
        } else if (x < leftLineX && y < bottomLineY && y > topLineY) {
            return MID_LEFT;
        } else if (x > leftLineX && x < rightLineX && y < bottomLineY && y > topLineY) {
            return MID_MID;
        } else if (x > rightLineX && y < bottomLineY && y > topLineY) {
            return MID_RIGHT;
        } else if (x < leftLineX && y > bottomLineY) {
            return BOTTOM_LEFT;
        } else if (x > leftLineX && x < rightLineX && y > bottomLineY) {
            return BOTTOM_MID;
        } else if (x > rightLineX && y > bottomLineY) {
            return BOTTOM_RIGHT;
        }
        return null;
    }

    public static enum GridLocation {
        TOP_LEFT(0, 0), TOP_MIDDLE(0, 1), TOP_RIGHT(0, 2),
        MID_LEFT(1, 0), MID_MID(1, 1), MID_RIGHT(1, 2),
        BOTTOM_LEFT(2, 0), BOTTOM_MID(2, 1), BOTTOM_RIGHT(2, 2);

        public final int row, column;

        GridLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
