package b2s.tictactoe;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;


public class GridCanvas extends Canvas implements Runnable, MouseListener, MouseMotionListener {
    private Thread thread;
    private boolean running;
    private long lastTime;
    private final Dimension size;
    private BufferedImage offscreenImage;
    private Graphics offscreenGraphics;
    private final int boxSize;
    private static final int LINE_WIDTH = 1;
    private static final Color BACKGROUND = Color.white;
    private static final Color LINE_COLOR = Color.black;
    private Grid grid;
    private PointToGridResolver pointToGridResolver;
    private PointToGridResolver.GridLocation currentLocation = PointToGridResolver.GridLocation.TOP_MIDDLE;

    public GridCanvas(Dimension size) {
        this.size = size;
        boxSize = size.width / 3 - (LINE_WIDTH * 2);
        setFocusable(true);
        this.pointToGridResolver = new PointToGridResolver(size, LINE_WIDTH);
        this.grid = new Grid();
        addMouseMotionListener(this);
        addMouseListener(this);
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

        synchronized (currentLocation) {
            g.setColor(Color.red);
            g.drawString(currentLocation.name(), 100, 100);
        }
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
        PointToGridResolver.GridLocation location = pointToGridResolver.resolve(e.getPoint());

        switch (location) {
            case TOP_LEFT:
                grid.move(0, 0);
                break;
            case TOP_MIDDLE:
                grid.move(0, 1);
                break;
            case TOP_RIGHT:
                grid.move(0, 2);
                break;
            case MID_LEFT:
                grid.move(1, 0);
                break;
            case MID_MID:
                grid.move(1, 1);
                break;
            case MID_RIGHT:
                grid.move(1, 2);
                break;
            case BOTTOM_LEFT:
                grid.move(2, 0);
                break;
            case BOTTOM_MID:
                grid.move(2, 1);
                break;
            case BOTTOM_RIGHT:
                grid.move(2, 2);
                break;
        }

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void setPointToGridResolver(PointToGridResolver pointToGridResolver) {
        this.pointToGridResolver = pointToGridResolver;
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        synchronized (currentLocation) {
            PointToGridResolver.GridLocation location = pointToGridResolver.resolve(e.getPoint());
            if (location != null) currentLocation = location;
            else System.out.println("no idea");
        }
    }
}
