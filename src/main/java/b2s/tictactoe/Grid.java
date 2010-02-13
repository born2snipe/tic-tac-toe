package b2s.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Grid {
    public char[][] cells = new char[3][3];
    private char whosMove = 'x';
    public State state = State.KEEP_GOING;

    public Grid() {
        reset();
    }

    public boolean move(int row, int column) {
        boolean result = move(row, column, whosMove);
        whosMove = whosMove == 'x' ? 'o' : 'x';
        return result;
    }

    public boolean move(int row, int column, char value) {
        if (cells[row][column] == ' ') {
            cells[row][column] = value;
            return true;
        }
        return false;
    }

    public char whosMove() {
        return whosMove;
    }

    public void reset() {
        for (char[] row : cells) {
            Arrays.fill(row, ' ');
        }
        state = State.KEEP_GOING;
    }

    public boolean isGameOver() {
        return isTopRowTaken() || isMiddleRowTaken() || isBottomRowTaken()
                || isColumnTaken(0) || isColumnTaken(1) || isColumnTaken(2)
                || isTopLeftToLowerRightTaken() || isTopRightToLowerLeftTaken() || allSpotsAreTaken();
    }

    private boolean allSpotsAreTaken() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                if (cells[row][col] == ' ') return false;
            }
        }
        state = State.CAT;
        return true;
    }

    private boolean isTopRightToLowerLeftTaken() {
        char match = cells[0][2];
        boolean taken = cells[0][2] != ' ' && cells[1][1] != ' ' && cells[1][1] == match && cells[2][0] != ' ' && cells[2][0] == match;
        if (taken) {
            state = match == 'x' ? State.X_WINS : State.O_WINS;
        }
        return taken;
    }

    private boolean isTopLeftToLowerRightTaken() {
        char match = cells[0][0];
        boolean taken = cells[0][0] != ' ' && cells[1][1] != ' ' && cells[1][1] == match && cells[2][2] != ' ' && cells[2][2] == match;
        if (taken) {
            state = match == 'x' ? State.X_WINS : State.O_WINS;
        }
        return taken;
    }

    private boolean isRowTaken(int row) {
        int matchCount = 0;
        char matchAgainst = cells[row][0];
        if (matchAgainst == ' ') return false;
        for (int col = 0; col < cells[row].length; col++) {
            if (cells[row][col] == matchAgainst) {
                matchCount++;
            }
        }
        boolean taken = matchCount == 3;
        if (taken) {
            state = matchAgainst == 'x' ? State.X_WINS : State.O_WINS;
        }
        return taken;
    }

    private boolean isColumnTaken(int col) {
        int matchCount = 0;
        char matchAgainst = cells[0][col];
        if (matchAgainst == ' ') return false;
        for (int row = 0; row < cells.length; row++) {
            if (cells[row][col] == matchAgainst) {
                matchCount++;
            }
        }
        boolean taken = matchCount == 3;
        if (taken) {
            state = matchAgainst == 'x' ? State.X_WINS : State.O_WINS;
        }
        return taken;
    }

    private boolean isBottomRowTaken() {
        return isRowTaken(2);
    }

    private boolean isMiddleRowTaken() {
        return isRowTaken(1);
    }

    private boolean isTopRowTaken() {
        return isRowTaken(0);
    }


    public List<Position> getOpenSpots() {
        List<Position> positions = new ArrayList<Position>();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                if (cells[row][col] == ' ') positions.add(new Position(row, col));
            }
        }
        return positions;
    }

    public static class Position {
        public final int row, col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static enum State {
        X_WINS, O_WINS, CAT, KEEP_GOING
    }
}
