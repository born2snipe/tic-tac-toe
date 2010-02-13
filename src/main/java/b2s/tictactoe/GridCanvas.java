package b2s.tictactoe;

import b2s.tictactoe.trophy.TrophyContext;
import com.gamejolt.GameJolt;

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
    private final GameJolt gameJolt;
    private BufferedImage offscreenImage;
    private Graphics2D offscreenGraphics;
    private final int boxSize;
    private Grid grid;
    private PointToGridResolver pointToGridResolver;
    private List<Line> gridLines = new ArrayList<Line>();
    private Notification notification;
    private TrophyContext context = new TrophyContext();

    public GridCanvas(Dimension size, GameJolt gameJolt) {
        this.size = size;
        this.gameJolt = gameJolt;
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

        notification = new Notification(size, "You have achieved a new trophy!!");
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
        notification.tick(TICKS_PER_SECOND);
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
        drawGridLines(g);
        drawPieces(g);
        notification.render(g);
    }

    private void drawPieces(Graphics2D g) {
        g.setFont(new Font("Courier", Font.BOLD, 70));
        g.setColor(Color.blue);
        for (int row = 0; row < grid.cells.length; row++) {
            for (int col = 0; col < grid.cells[row].length; col++) {
                String toDraw = "";
                switch (grid.cells[row][col]) {
                    case ' ':
                        break;
                    case 'x':
                        toDraw = "X";
                        break;
                    case 'o':
                        toDraw = "O";
                        break;
                }
                Point point = pointToGridResolver.resolve(row, col);
                g.drawString(toDraw, point.x, point.y);
            }
        }
    }

    private void drawGridLines(Graphics2D g) {
        for (Line line : gridLines) {
            line.render(g);
        }
    }

    public void mouseClicked(MouseEvent e) {
        PointToGridResolver.GridLocation location = pointToGridResolver.resolve(e.getPoint());
        if (location != null) {
            grid.move(location.row, location.column);

            if (location == PointToGridResolver.GridLocation.MID_MID) {
                notification.show();
            }
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

    }
}
