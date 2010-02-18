package b2s.tictactoe;


public class PlayerDataManager {
    private final PlayerData data;
    private Listener listener;

    public PlayerDataManager(PlayerData data) {
        this.data = data;
    }

    public void manage(Grid.State state) {
        if (data.lastGame != state) {
            if (data.longestWinningStreak < data.currentWinningStreak) {
                data.longestWinningStreak = data.currentWinningStreak;
                listener.newWinningStreak(data.longestWinningStreak);
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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void newWinningStreak(int streakLength);
    }
}
