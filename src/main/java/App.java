import b2s.tictactoe.GridCanvas;
import b2s.tictactoe.PlayerData;
import b2s.tictactoe.rules.*;
import com.gamejolt.GameJolt;
import com.gamejolt.trophy.TrophyContext;
import com.gamejolt.trophy.TrophyManager;

import javax.swing.*;
import java.awt.*;

public class App extends JApplet {

    private GridCanvas gridCanvas;
    private GameJolt gameJolt;
    private PlayerData data;

    public void init() {
        gameJolt = new GameJolt(1591, "1356d9d49d9ac4b47aa8ebb700299065");
        gameJolt.setVerbose(true);
        gameJolt.verifyUser(getParameter("gjapi_username"), getParameter("gjapi_token"));

        TrophyManager trophyManager = initializeTrophyManager();
        TrophyContext context = new TrophyContext();

        data = (PlayerData) gameJolt.getUserData("data");
        if (data == null) {
            data = new PlayerData();
        }
        context.put("data", data);
        context.put("gameJolt", gameJolt);

        gridCanvas = new GridCanvas(getSize(), trophyManager, context);
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
        gameJolt.storeUserData("data", data);
    }

}