package b2s.tictactoe.rules;

import b2s.tictactoe.PlayerData;
import b2s.tictactoe.trophy.AcquiredTrophyRule;
import b2s.tictactoe.trophy.TrophyContext;


public class OmgRule implements AcquiredTrophyRule {
    public boolean acquired(TrophyContext context) {
        PlayerData data = context.get("data", PlayerData.class);
        return data.losses > 0;
    }
}
