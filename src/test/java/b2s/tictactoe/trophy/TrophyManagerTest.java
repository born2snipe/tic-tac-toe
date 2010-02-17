package b2s.tictactoe.trophy;

import com.gamejolt.GameJolt;
import com.gamejolt.Trophy;
import com.gamejolt.trophy.*;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class TrophyManagerTest {
    private GameJolt gameJolt;
    private TrophyManager manager;
    private AcquiredTrophyRule rule;
    private TrophyContext context;
    private TrophyManagerListener listener;

    @Before
    public void setUp() throws Exception {
        gameJolt = mock(GameJolt.class);
        rule = mock(AcquiredTrophyRule.class);
        listener = mock(TrophyManagerListener.class);

        manager = new TrophyManager(gameJolt);
        manager.addListener(listener);

        context = new TrophyContext();
    }

    @Test
    public void test_manage_singleRule_MultipleListeners() {
        TrophyManagerListener listener2 = mock(TrophyManagerListener.class);
        manager.addListener(listener2);

        Trophy trophy = new Trophy();
        when(gameJolt.getTrophy(123)).thenReturn(trophy);
        when(rule.acquired(context)).thenReturn(true);

        manager.registerRule(123, rule);
        manager.manage(context);

        verify(listener).trophiesAcquired(asList(trophy), context);
        verify(listener2).trophiesAcquired(asList(trophy), context);
    }

    @Test
    public void test_manage_singleRule_TrophyNotAcquired() {
        Trophy trophy = new Trophy();
        when(gameJolt.getTrophy(123)).thenReturn(trophy);
        when(rule.acquired(context)).thenReturn(false);

        manager.registerRule(123, rule);
        manager.manage(context);

        verifyZeroInteractions(listener);
    }

    @Test
    public void test_manage_singleRule_TrophyAlreadyAcquired() {
        Trophy trophy = new Trophy();
        trophy.setAchieved(true);
        when(gameJolt.getTrophy(123)).thenReturn(trophy);

        manager.registerRule(123, rule);
        manager.manage(context);

        verifyZeroInteractions(rule, listener);
    }

    @Test
    public void test_manage_singleRule_TrophyAcquired() {
        Trophy trophy = new Trophy();
        trophy.setId(123);
        when(gameJolt.getTrophy(123)).thenReturn(trophy);
        when(rule.acquired(context)).thenReturn(true);

        manager.registerRule(123, rule);
        manager.manage(context);

        verify(rule).acquired(context);
        verify(listener).trophiesAcquired(asList(trophy), context);
        verify(gameJolt).achievedTrophy(123);
    }

    @Test
    public void test_manage_mutipleRules_AllTrophiesAcquired() {
        Trophy trophy = new Trophy();
        Trophy trophy2 = new Trophy();
        AcquiredTrophyRule rule2 = mock(AcquiredTrophyRule.class);

        when(gameJolt.getTrophy(123)).thenReturn(trophy);
        when(gameJolt.getTrophy(456)).thenReturn(trophy2);

        when(rule.acquired(context)).thenReturn(true);
        when(rule2.acquired(context)).thenReturn(true);

        manager.registerRule(123, rule);
        manager.registerRule(456, rule2);
        manager.manage(context);

        verify(rule).acquired(context);
        verify(listener).trophiesAcquired(asList(trophy), context);
        verify(listener).trophiesAcquired(asList(trophy2), context);
        verifyNoMoreInteractions(listener);
    }

    @Test
    public void test_manage_mutipleRules_AllTrophiesAcquired_BatchNotification() {
        manager.setBatchListenerNotification(true);

        Trophy trophy = new Trophy();
        Trophy trophy2 = new Trophy();
        AcquiredTrophyRule rule2 = mock(AcquiredTrophyRule.class);

        when(gameJolt.getTrophy(123)).thenReturn(trophy);
        when(gameJolt.getTrophy(456)).thenReturn(trophy2);

        when(rule.acquired(context)).thenReturn(true);
        when(rule2.acquired(context)).thenReturn(true);

        manager.registerRule(123, rule);
        manager.registerRule(456, rule2);
        manager.manage(context);

        verify(rule).acquired(context);
        verify(listener).trophiesAcquired(asList(trophy, trophy2), context);
        verifyNoMoreInteractions(listener);

        assertTrue(trophy.isAchieved());
        assertTrue(trophy2.isAchieved());
    }

    @Test
    public void test_manage_mutipleRules_AllTrophiesAcquired_BatchNotification_MultipleListeners() {
        manager.setBatchListenerNotification(true);

        TrophyManagerListener listener2 = mock(TrophyManagerListener.class);
        manager.addListener(listener2);

        Trophy trophy = new Trophy();
        Trophy trophy2 = new Trophy();
        AcquiredTrophyRule rule2 = mock(AcquiredTrophyRule.class);

        when(gameJolt.getTrophy(123)).thenReturn(trophy);
        when(gameJolt.getTrophy(456)).thenReturn(trophy2);

        when(rule.acquired(context)).thenReturn(true);
        when(rule2.acquired(context)).thenReturn(true);

        manager.registerRule(123, rule);
        manager.registerRule(456, rule2);
        manager.manage(context);

        verify(rule).acquired(context);
        verify(listener).trophiesAcquired(asList(trophy, trophy2), context);
        verify(listener2).trophiesAcquired(asList(trophy, trophy2), context);
        verifyNoMoreInteractions(listener, listener2);
    }


    @Test
    public void test_registerRule_CouldNotFindTrophy() {
        when(gameJolt.getTrophy(123)).thenReturn(null);

        try {
            manager.registerRule(123, rule);
            fail();
        } catch (TrophyNotFoundException err) {
            assertEquals("Could not locate trophy with id=123", err.getMessage());
        }
    }

    @Test
    public void test_registerRule_FoundTrophy() {
        when(gameJolt.getTrophy(123)).thenReturn(new Trophy());

        manager.registerRule(123, rule);

        verify(gameJolt).getTrophy(123);
    }


}
