package b2s.tictactoe;

import com.gamejolt.Trophy;
import com.gamejolt.trophy.TrophyContext;
import com.gamejolt.trophy.TrophyManager;
import com.gamejolt.trophy.TrophyManagerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class GridCanvas extends JPanel implements Runnable, MouseListener, MouseMotionListener, TrophyManagerListener, PlayerDataManager.Listener {
    private static final int TICKS_PER_SECOND = 60;
    private static final int LINE_WIDTH = 3;
    private static final Color BACKGROUND = Color.white;

    private Thread thread;
    private boolean running;
    private long lastTime;
    private final Dimension size;
    private final TrophyManager trophyManager;
    private BufferedImage offscreenImage;
    private Graphics2D offscreenGraphics;
    private final int boxSize;
    private Grid grid;
    private PointToGridResolver pointToGridResolver;
    private List<Line> gridLines = new ArrayList<Line>();
    private Notification notification;
    private final TrophyContext trophyContext;
    private SmarterComputerPlayer cpu = new SmarterComputerPlayer();
    private boolean gameOver;
    private Button playAgain = new Button("Play Again?");
    private JPanel glassPane;
    private PlayerDataManager playerDataManager;

    public GridCanvas(Dimension size, TrophyManager trophyManager, TrophyContext trophyContext, PlayerDataManager playerDataManager) {
        this.size = size;
        this.trophyManager = trophyManager;
        this.trophyContext = trophyContext;
        addMouseMotionListener(this);
        addMouseListener(this);
        setFocusable(true);
        trophyManager.addListener(this);
        this.pointToGridResolver = new PointToGridResolver(size, LINE_WIDTH);
        this.grid = new Grid();

        boxSize = size.width / 3 - (LINE_WIDTH * 2);

        gridLines.add(new Line(new Point(boxSize, 0), new Point(boxSize, size.height), LINE_WIDTH));
        gridLines.add(new Line(new Point(boxSize * 2, 0), new Point(boxSize * 2, size.height), LINE_WIDTH));
        gridLines.add(new Line(new Point(0, boxSize), new Point(size.width, boxSize), LINE_WIDTH));
        gridLines.add(new Line(new Point(0, boxSize * 2), new Point(size.width, boxSize * 2), LINE_WIDTH));

        notification = new Notification(size);

        playAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grid.reset();
                gameOver = false;
                glassPane.setVisible(false);
            }
        });

        this.playerDataManager = playerDataManager;
        this.playerDataManager.addListener(this);
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

            trophyManager.manage(trophyContext);
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

        String message = "";
        switch (grid.state) {
            case X_WINS:
                message = "You Win!";
                break;
            case O_WINS:
                message = "You Lose!";
                break;
            case CAT:
                message = "Meow!";
                break;
        }
        if (grid.state != Grid.State.KEEP_GOING) {
            g.setColor(new Color(250, 250, 250, 200));
            g.fillRect(0, 0, size.width, size.height);
            g.setColor(Color.red);
            g.setFont(new Font("Courier", Font.BOLD, 50));
            g.drawString(message, 30, 150);
        }

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
        if (gameOver) return;
        PointToGridResolver.GridLocation location = pointToGridResolver.resolve(e.getPoint());
        if (location != null) {
            if (grid.move(location.row, location.column, 'x')) {
                if (!grid.isGameOver()) {
                    cpu.makeMove(grid);
                    gameOver = grid.isGameOver();
                } else {
                    gameOver = true;
                }
            }
        }

        if (gameOver) {
            glassPane = (JPanel) getRootPane().getGlassPane();
            glassPane.add(playAgain);
            glassPane.setVisible(true);

            playerDataManager.manage(grid.state);
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

    public void trophiesAcquired(List<Trophy> trophies, TrophyContext context) {
        notification.show("Achieved \"" + trophies.get(0).getTitle() + "\" trophy");
    }

    public void newWinningStreak(int streakLength) {
        notification.show("New Winning Streak: " + streakLength + " games");
    }
}
