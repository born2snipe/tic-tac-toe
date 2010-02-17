package b2s.tictactoe;

import java.util.ArrayList;
import java.util.List;


public class SmarterComputerPlayer {
    private StupidComputerPlayer dummy = new StupidComputerPlayer();

    public void makeMove(Grid grid) {
        List<Grid.Position> positions = grid.getOpenSpots();
        List<Grid.Position> gameWinners = findGameWinners(grid);
        List<Grid.Position> blockers = findPlayerBlockers(grid);

        if (positions.size() == 1) {
            move(grid, positions.get(0));
        } else if (gameWinners.size() > 0) {
            move(grid, gameWinners.get(0));
        } else if (blockers.size() > 0) {
            move(grid, blockers.get(0));
        } else {
            dummy.makeMove(grid);
        }
    }

    private List<Grid.Position> findPlayerBlockers(Grid grid) {
        List<Grid.Position> blockers = new ArrayList<Grid.Position>();
        // check rows
        blockers.addAll(findGameWinnerInRow(grid, 0, 'x'));
        blockers.addAll(findGameWinnerInRow(grid, 1, 'x'));
        blockers.addAll(findGameWinnerInRow(grid, 2, 'x'));

        // check columns
        blockers.addAll(findGameWinnerInCol(grid, 0, 'x'));
        blockers.addAll(findGameWinnerInCol(grid, 1, 'x'));
        blockers.addAll(findGameWinnerInCol(grid, 2, 'x'));

        // check top-left to lower-right
        blockers.addAll(findGameWinnerInTopLeftToLowerRight(grid, 'x'));
        blockers.addAll(findGameWinnerInLowerLeftToTopRight(grid, 'x'));


        return blockers;
    }

    private List<Grid.Position> findGameWinners(Grid grid) {
        List<Grid.Position> winners = new ArrayList<Grid.Position>();
        // check rows
        winners.addAll(findGameWinnerInRow(grid, 0, 'o'));
        winners.addAll(findGameWinnerInRow(grid, 1, 'o'));
        winners.addAll(findGameWinnerInRow(grid, 2, 'o'));

        // check columns
        winners.addAll(findGameWinnerInCol(grid, 0, 'o'));
        winners.addAll(findGameWinnerInCol(grid, 1, 'o'));
        winners.addAll(findGameWinnerInCol(grid, 2, 'o'));

        // check top-left to lower-right
        winners.addAll(findGameWinnerInTopLeftToLowerRight(grid, 'o'));
        winners.addAll(findGameWinnerInLowerLeftToTopRight(grid, '0'));

        return winners;
    }

    private List<Grid.Position> findGameWinnerInLowerLeftToTopRight(Grid grid, char gameWinnerCharacter) {
        List<Grid.Position> spots = new ArrayList<Grid.Position>();
        int spaceCount = 0;
        int characterCount = 0;

        for (int row = 0, col = 2; row < 3 && col >= 0; row++, col--) {
            char cell = grid.cells[row][col];
            if (cell == ' ') {
                spaceCount++;
                spots.add(new Grid.Position(row, col));
            } else if (cell == gameWinnerCharacter) {
                characterCount++;
            }
        }


        if (spaceCount > 1 || characterCount != 2) {
            spots.clear();
        }
        return spots;
    }

    private List<Grid.Position> findGameWinnerInTopLeftToLowerRight(Grid grid, char gameWinnerCharacter) {
        List<Grid.Position> spots = new ArrayList<Grid.Position>();
        int spaceCount = 0;
        int characterCount = 0;

        for (int row = 0, col = 0; row < 3 && col < 3; row++, col++) {
            char cell = grid.cells[row][col];
            if (cell == ' ') {
                spaceCount++;
                spots.add(new Grid.Position(row, col));
            } else if (cell == gameWinnerCharacter) {
                characterCount++;
            }
        }


        if (spaceCount > 1 || characterCount != 2) {
            spots.clear();
        }
        return spots;
    }

    private List<Grid.Position> findGameWinnerInCol(Grid grid, int col, char gameWinnerCharacter) {
        int spaceCount = 0;
        int characterCount = 0;
        List<Grid.Position> spots = new ArrayList<Grid.Position>();
        for (int row = 0; row < 3; row++) {
            char cell = grid.cells[row][col];
            if (cell == ' ') {
                spaceCount++;
                spots.add(new Grid.Position(row, col));
            } else if (cell == gameWinnerCharacter) {
                characterCount++;
            }
        }
        if (spaceCount > 1 || characterCount != 2) {
            spots.clear();
        }
        return spots;
    }

    private List<Grid.Position> findGameWinnerInRow(Grid grid, int row, char gameWinnerCharacter) {
        int spaceCount = 0;
        int characterCount = 0;
        List<Grid.Position> spots = new ArrayList<Grid.Position>();
        for (int col = 0; col < 3; col++) {
            char cell = grid.cells[row][col];
            if (cell == ' ') {
                spaceCount++;
                spots.add(new Grid.Position(row, col));
            } else if (cell == gameWinnerCharacter) {
                characterCount++;
            }
        }
        if (spaceCount > 1 || characterCount != 2) {
            spots.clear();
        }
        return spots;
    }

    private void move(Grid grid, Grid.Position position) {
        grid.move(position.row, position.col, 'o');
    }

}
