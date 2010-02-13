package b2s.tictactoe;

import java.util.Arrays;


public class Grid {
    public char[][] cells = new char[3][3];
    private char whosMove = 'x';

    public Grid() {
        for (char[] row : cells) {
            Arrays.fill(row, ' ');
        }
    }

    public boolean move(int row, int column) {
        if (cells[row][column] == ' ') {
            cells[row][column] = whosMove;
            whosMove = whosMove == 'x' ? 'o' : 'x';
            return true;
        }
        return false;
    }

    public char whosMove() {
        return whosMove;
    }
}
