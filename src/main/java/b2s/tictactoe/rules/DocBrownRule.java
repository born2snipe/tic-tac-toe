package b2s.tictactoe.rules;

import b2s.tictactoe.trophy.AcquiredTrophyRule;
import b2s.tictactoe.trophy.TrophyContext;
import com.gamejolt.GameJolt;
import com.gamejolt.Trophy;

import java.util.List;

public class DocBrownRule implements AcquiredTrophyRule {
    private List<Trophy> trophies;

    public boolean acquired(TrophyContext context) {
        GameJolt gameJolt = context.get("gameJolt", GameJolt.class);
        if (trophies == null) {
            trophies = gameJolt.getAllTrophies();
        }
        int achievedCount = 0;
        for (Trophy trophy : trophies) {
            if (trophy.isAchieved()) achievedCount++;
        }
        return achievedCount == trophies.size();
    }
}