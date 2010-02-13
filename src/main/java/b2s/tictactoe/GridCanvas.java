package b2s.tictactoe;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;


public class GridCanvas extends Canvas implements Runnable, MouseListener {
    private Thread thread;
    private boolean running;
    private long lastTime;
    private final Dimension size;
    private BufferedImage offscreenImage;
    private Graphics offscreenGraphics;
    private final int boxSize;
    private static final int LINE_WIDTH = 3;
    private static final Color BACKGROUND = Color.white;
    private static final Color LINE_COLOR = Color.black;

    public GridCanvas(Dimension size) {
        this.size = size;
        boxSize = size.width / 3 - (LINE_WIDTH * 2);
        setFocusable(true);
    }

    public void run() {
        while (running) {

            render(offscreenGraphics);

            Graphics g = getGraphics();
            g.drawImage(offscreenImage, 0, 0, size.width, size.height, null);
            g.dispose();

            try {
                Thread.sleep(2L);
            } catch (InterruptedException err) {

            }
        }
    }

    public synchronized void start() {
        running = true;
        lastTime = System.nanoTime();

        offscreenImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        offscreenGraphics = offscreenImage.getGraphics();

        thread = new Thread(this);
        thread.start();

        requestFocus();
    }

    public synchronized void stop() {
        running = false;
    }

    public void paint(Graphics g) {
    }

    public void update(Graphics g) {
    }

    public void render(Graphics g) {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, size.width, size.height);
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(LINE_COLOR);

        // vertical lines
        g.drawLine(boxSize, 0, boxSize, size.height);
        g.drawLine(boxSize * 2, 0, boxSize * 2, size.height);

        // horizontal lines
        g.drawLine(0, boxSize, size.width, boxSize);
        g.drawLine(0, boxSize * 2, size.width, boxSize * 2);
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
