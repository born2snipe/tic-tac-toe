import b2s.tictactoe.GridCanvas;
import b2s.tictactoe.PlayerData;
import b2s.tictactoe.PlayerDataManager;
import b2s.tictactoe.rules.*;
import com.gamejolt.GameJolt;
import com.gamejolt.GameJoltException;
import com.gamejolt.trophy.TrophyContext;
import com.gamejolt.trophy.TrophyManager;

import javax.swing.*;
import java.awt.*;

public class App extends JApplet {

    private GridCanvas gridCanvas;
    private GameJolt gameJolt;
    private PlayerData data;
    private boolean verifiedUser;

    public void init() {
        gameJolt = new GameJolt(1591, "1356d9d49d9ac4b47aa8ebb700299065");
        gameJolt.setVerbose(true);
        verifiedUser = gameJolt.verifyUser(getParameter("gjapi_username"), getParameter("gjapi_token"));

        TrophyManager trophyManager = null;
        PlayerDataManager playerDataManager = null;
        TrophyContext context = new TrophyContext();

        if (verifiedUser) {
            trophyManager = initializeTrophyManager();
            try {
                data = (PlayerData) gameJolt.getUserData("data");
            } catch (GameJoltException err) {
                // incompatible serialization
            }
            if (data == null) {
                data = new PlayerData();
            }

            playerDataManager = new PlayerDataManager(data);
            playerDataManager.addListener(new PlayerDataManager.Listener() {
                public void newWinningStreak(int streakLength) {
                    if (streakLength == 1) return;
                    gameJolt.userAchievedHighscore(streakLength + " game winning streak", streakLength, "");
                }
            });

            context.put("data", data);
            context.put("gameJolt", gameJolt);
        } else {
            data = new PlayerData();
            playerDataManager = new PlayerDataManager(data);
        }

        gridCanvas = new GridCanvas(getSize(), trophyManager, context, playerDataManager, verifiedUser);
        setLayout(new BorderLayout());
        add(gridCanvas, BorderLayout.CENTER);
        setIgnoreRepaint(true);
    }

    private TrophyManager initializeTrophyManager() {
        TrophyManager trophyManager = new TrophyManager(gameJolt);

        trophyManager.registerRule(211, new RibbonCuttingRule());
        trophyManager.registerRule(203, new CakeWalkRule());
        trophyManager.registerRule(204, new OmgRule());
        trophyManager.registerRule(206, new TurkeyRule());
        trophyManager.registerRule(207, new CatLadyRule());
        trophyManager.registerRule(210, new DocBrownRule());

        return trophyManager;
    }

    public void start() {
        gridCanvas.start();
    }

    public void stop() {
        gridCanvas.stop();
    }

    public void destroy() {
        if (verifiedUser) gameJolt.storeUserData("data", data);
    }

}