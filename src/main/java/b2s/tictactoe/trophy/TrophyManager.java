package b2s.tictactoe.trophy;

import com.gamejolt.GameJolt;
import com.gamejolt.Trophy;

import java.util.*;


public class TrophyManager {
    private final Map<Integer, TrophyAndRuleHolder> holders = new LinkedHashMap<Integer, TrophyAndRuleHolder>();
    private final GameJolt gameJolt;
    private List<TrophyManagerListener> listeners = new ArrayList<TrophyManagerListener>();
    private boolean batchListenerNotification;

    public TrophyManager(GameJolt gameJolt) {
        this.gameJolt = gameJolt;
    }

    public void registerRule(int trophyId, AcquiredTrophyRule trophyRule) throws TrophyNotFoundException {
        Trophy trophy = gameJolt.getTrophy(trophyId);
        if (trophy == null) throw new TrophyNotFoundException(trophyId);
        holders.put(trophyId, new TrophyAndRuleHolder(trophy, trophyRule));
    }

    public void manage(TrophyContext context) {
        List<Trophy> trophiesAcquired = new ArrayList<Trophy>();
        for (TrophyAndRuleHolder holder : holders.values()) {
            if (!holder.trophy.isAchieved() && holder.rule.acquired(context)) {
                // need to tell gamejolt still
                holder.trophy.setAchieved(true);
                if (batchListenerNotification) {
                    trophiesAcquired.add(holder.trophy);
                } else {
                    for (TrophyManagerListener listener : listeners) {
                        listener.trophiesAcquired(Arrays.asList(holder.trophy), context);
                    }
                }
            }
        }

        if (batchListenerNotification) {
            for (TrophyManagerListener listener : listeners) {
                listener.trophiesAcquired(trophiesAcquired, context);
            }
        }
    }

    public void addListener(TrophyManagerListener listener) {
        listeners.add(listener);
    }

    public void setBatchListenerNotification(boolean batchListenerNotification) {
        this.batchListenerNotification = batchListenerNotification;
    }

    private static class TrophyAndRuleHolder {
        public final Trophy trophy;
        public final AcquiredTrophyRule rule;

        private TrophyAndRuleHolder(Trophy trophy, AcquiredTrophyRule rule) {
            this.trophy = trophy;
            this.rule = rule;
        }
    }
}
