import b2s.tictactoe.GridCanvas;

import java.applet.Applet;
import java.awt.*;

public class App extends Applet {

    private GridCanvas gridCanvas;

    public void init() {
        gridCanvas = new GridCanvas(getSize());
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