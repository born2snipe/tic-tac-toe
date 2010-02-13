package b2s.tictactoe.trophy;

import com.gamejolt.Trophy;

import java.util.List;


public interface TrophyManagerListener {
    void trophiesAcquired(List<Trophy> trophies, TrophyContext context);
}
