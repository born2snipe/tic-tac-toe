package b2s.tictactoe;

import java.util.List;


public class StupidComputerPlayer {
    public void makeMove(Grid grid) {
        List<Grid.Position> positions = grid.getOpenSpots();
        boolean foundSpot = false;
        while (!foundSpot) {
            int index = (int) (Math.random() * positions.size());
            Grid.Position position = positions.get(index);
            foundSpot = grid.move(position.row, position.col, 'o');
        }

    }
}
