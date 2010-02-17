package b2s.tictactoe.rules;

import b2s.tictactoe.PlayerData;
import com.gamejolt.trophy.AcquiredTrophyRule;
import com.gamejolt.trophy.TrophyContext;

public class CatLadyRule implements AcquiredTrophyRule {
    public boolean acquired(TrophyContext context) {
        PlayerData data = context.get("data", PlayerData.class);
        return data.currentCatStreak >= 5;
    }
}
