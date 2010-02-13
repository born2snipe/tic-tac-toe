import b2s.tictactoe.GridCanvas;
import com.gamejolt.GameJolt;

import java.applet.Applet;
import java.awt.*;

public class App extends Applet {

    private GridCanvas gridCanvas;

    public void init() {
        gridCanvas = new GridCanvas(getSize(), new GameJolt(1591, "1356d9d49d9ac4b47aa8ebb700299065"));
        setLayout(new BorderLayout());
        add(gridCanvas, BorderLayout.CENTER);
        setIgnoreRepaint(true);
    }

    public void start() {
        gridCanvas.start();
    }

    public void stop() {
        gridCanvas.stop();
    }

    public void destroy() {
        // persist user data
    }

}