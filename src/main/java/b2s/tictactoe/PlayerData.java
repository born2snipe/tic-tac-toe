package b2s.tictactoe;

import java.io.Serializable;


public class PlayerData implements Serializable {
    public int numberOfTimesPlayed;
    public int wins;
    public int losses;
    public int cats;
    public int currentWinningStreak;
    public int currentLosingStreak;
    public int currentCatStreak;
    public Grid.State lastGame;
    public int longestWinningStreak;
}
