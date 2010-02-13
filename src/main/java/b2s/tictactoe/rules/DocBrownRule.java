package b2s.tictactoe.rules;

import b2s.tictactoe.trophy.AcquiredTrophyRule;
import b2s.tictactoe.trophy.TrophyContext;
import com.gamejolt.GameJolt;
import com.gamejolt.Trophy;

import java.util.List;

public class DocBrownRule implements AcquiredTrophyRule {
    public boolean acquired(TrophyContext context) {
        GameJolt gameJolt = context.get("gameJolt", GameJolt.class);
        int achievedCount = 0;
        List<Trophy> trophies = gameJolt.getAllTrophies();
        for (Trophy trophy : trophies) {
            if (trophy.isAchieved()) achievedCount++;
        }
        return achievedCount == trophies.size();
    }
}
