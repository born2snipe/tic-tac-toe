import b2s.tictactoe.GridCanvas;
import b2s.tictactoe.PlayerData;
import b2s.tictactoe.rules.RibbonCuttingRule;
import b2s.tictactoe.trophy.TrophyContext;
import b2s.tictactoe.trophy.TrophyManager;
import com.gamejolt.GameJolt;

import java.applet.Applet;
import java.awt.*;

public class App extends Applet {

    private GridCanvas gridCanvas;
    private GameJolt gameJolt;
    private PlayerData data;

    public void init() {
        gameJolt = new GameJolt(1591, "1356d9d49d9ac4b47aa8ebb700299065");
        gameJolt.setVerbose(true);
        gameJolt.verifyUser("born2snipe", "b7286a");

        TrophyManager trophyManager = initializeTrophyManager();
        TrophyContext context = new TrophyContext();

        data = (PlayerData) gameJolt.getUserData("data");
        if (data == null) {
            data = new PlayerData();
        }
        context.put("data", data);

        gridCanvas = new GridCanvas(getSize(), trophyManager, context);
        setLayout(new BorderLayout());
        add(gridCanvas, BorderLayout.CENTER);
        setIgnoreRepaint(true);
    }

    private TrophyManager initializeTrophyManager() {
        TrophyManager trophyManager = new TrophyManager(gameJolt);

        trophyManager.registerRule(211, new RibbonCuttingRule());

        return trophyManager;
    }

    public void start() {
        gridCanvas.start();
    }

    public void stop() {
        gridCanvas.stop();
    }

    public void destroy() {
        gameJolt.storeUserData("data", data);
    }

}