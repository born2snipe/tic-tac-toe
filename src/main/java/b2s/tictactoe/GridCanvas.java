package b2s.tictactoe;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class GridCanvas extends Canvas implements Runnable, MouseListener, MouseMotionListener {
    private static final int TICKS_PER_SECOND = 60;
    private static final int LINE_WIDTH = 3;
    private static final Color BACKGROUND = Color.white;

    private Thread thread;
    private boolean running;
    private long lastTime;
    private final Dimension size;
    private BufferedImage offscreenImage;
    private Graphics2D offscreenGraphics;
    private final int boxSize;
    private Grid grid;
    private PointToGridResolver pointToGridResolver;
    private PointToGridResolver.GridLocation currentLocation = PointToGridResolver.GridLocation.TOP_MIDDLE;
    private List<Line> gridLines = new ArrayList<Line>();

    public GridCanvas(Dimension size) {
        this.size = size;
        boxSize = size.width / 3 - (LINE_WIDTH * 2);
        setFocusable(true);
        this.pointToGridResolver = new PointToGridResolver(size, LINE_WIDTH);
        this.grid = new Grid();
        addMouseMotionListener(this);
        addMouseListener(this);

        gridLines.add(new Line(new Point(boxSize, 0), new Point(boxSize, size.height), LINE_WIDTH));
        gridLines.add(new Line(new Point(boxSize * 2, 0), new Point(boxSize * 2, size.height), LINE_WIDTH));
        gridLines.add(new Line(new Point(0, boxSize), new Point(size.width, boxSize), LINE_WIDTH));
        gridLines.add(new Line(new Point(0, boxSize * 2), new Point(size.width, boxSize * 2), LINE_WIDTH));
    }

    public void run() {
        long unprocessedTime = 0;
        long nsPerTick = 1000000000 / TICKS_PER_SECOND;

        while (running) {

            long now = System.nanoTime();
            long passedTime = now - lastTime;
            if (passedTime > 0) {
                unprocessedTime += passedTime;
                while (unprocessedTime >= nsPerTick) {
                    unprocessedTime -= nsPerTick;
                    tick();
                }
                lastTime = now;
            }

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

    private void tick() {
        for (Line line : gridLines) {
            line.tick(TICKS_PER_SECOND);
        }
    }

    public synchronized void start() {
        running = true;
        lastTime = System.nanoTime();

        offscreenImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        offscreenGraphics = (Graphics2D) offscreenImage.getGraphics();

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

    public void render(Graphics2D g) {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, size.width, size.height);
        drawGrid(g);

        synchronized (currentLocation) {
            g.setColor(Color.red);
            g.drawString(currentLocation.name(), 100, 100);
        }
    }

    private void drawGrid(Graphics2D g) {
        for (Line line : gridLines) {
            line.render(g);
        }
    }

    public void mouseClicked(MouseEvent e) {
        PointToGridResolver.GridLocation location = pointToGridResolver.resolve(e.getPoint());
        if (location != null) {
            grid.move(location.row, location.column);
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
