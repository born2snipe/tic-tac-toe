package b2s.tictactoe;

import java.util.ArrayList;
import java.util.List;


public class PlayerDataManager {
    private final PlayerData data;
    private List<Listener> listeners = new ArrayList<Listener>();

    public PlayerDataManager(PlayerData data) {
        this.data = data;
    }

    public void manage(Grid.State state) {
        if (data.lastGame != state) {
            if (data.longestWinningStreak < data.currentWinningStreak) {
                data.longestWinningStreak = data.currentWinningStreak;
                for (Listener listener : listeners) {
                    listener.newWinningStreak(data.longestWinningStreak);
                }
            }

            data.currentLosingStreak = 0;
            data.currentWinningStreak = 0;
            data.currentCatStreak = 0;
        }

        switch (state) {
            case CAT:
                data.cats++;
                data.currentCatStreak++;
                break;
            case X_WINS:
                data.wins++;
                data.currentWinningStreak++;
                break;
            case O_WINS:
                data.losses++;
                data.currentLosingStreak++;
                break;
        }
        data.lastGame = state;
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public interface Listener {
        void newWinningStreak(int streakLength);
    }
}
