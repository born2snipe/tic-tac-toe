package b2s.tictactoe.trophy;

import com.gamejolt.GameJoltException;


public class TrophyNotFoundException extends GameJoltException {
    public TrophyNotFoundException(int trophyId) {
        super("Could not locate trophy with id=" + trophyId);
    }
}
