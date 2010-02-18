package b2s.tictactoe;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;


public class Notification implements Renderable, Tickable {
    private static final Font FONT = new Font("Courier", Font.BOLD, 14);
    private static final long TIME_FOR_VIEWING = 3000L;
    private static final int PADDING = 5;
    private final Dimension screenSize;
    private String currentMessage;
    private Dimension boxSize;
    private Point currentLocation;
    private boolean show = false;
    private long startViewingTime;
    private int fontHeight;
    private State state;
    private Queue<String> messages = new LinkedList<String>();

    public Notification(Dimension screenSize) {
        this.screenSize = screenSize;
    }

    public synchronized void tick(int ticksPerSecond) {
        if (show && currentLocation != null) {
            if (isFullyVisible() && state == State.MOVING_UP) {
                state = State.VIEWING;
                startViewingTime = System.currentTimeMillis();
            } else if (state == State.VIEWING) {
                if (System.currentTimeMillis() - startViewingTime >= TIME_FOR_VIEWING) {
                    state = State.MOVING_DOWN;
                }
            } else if (state == State.MOVING_DOWN) {
                currentLocation = new Point(0, currentLocation.y + 2);
                if (isFullyHidden()) {
                    show = false;
                }
            } else if (state == State.MOVING_UP) {
                currentLocation = new Point(0, currentLocation.y - 2);
            }
        } else if (isFullyHidden() && messages.size() > 0) {
            boxSize = null;
            state = State.MOVING_UP;
            synchronized (messages) {
                this.currentMessage = messages.remove();
            }
            show = true;
        }
    }

    public synchronized void render(Graphics2D g) {
        if (!show) return;
        if (boxSize == null) {
            // get metrics from the graphics
            FontMetrics metrics = g.getFontMetrics(FONT);
            // get the height of a line of text in this font and render context
            fontHeight = metrics.getHeight();
            // get the advance of my text in this font and render context
            int adv = metrics.stringWidth(currentMessage);
            // calculate the size of a box to hold the text with some padding.
            boxSize = new Dimension(adv + PADDING, fontHeight + PADDING);

            currentLocation = new Point(0, boxSize.height + screenSize.height);
        }
        g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setFont(FONT);
        g.setColor(new Color(38, 38, 38));
        g.fillRect(currentLocation.x, currentLocation.y, boxSize.width, boxSize.height);
        g.drawRect(currentLocation.x - 3, currentLocation.y, boxSize.width + 3, boxSize.height);
        g.setColor(Color.red);
        g.drawString(currentMessage, currentLocation.x + 2, currentLocation.y + fontHeight);
    }

    public void show(String message) {
        synchronized (messages) {
            this.messages.add(message);
        }
    }

    private boolean isFullyHidden() {
        return currentLocation == null || currentLocation.y > screenSize.height;
    }

    private boolean isFullyVisible() {
        return boxSize != null && currentLocation != null && screenSize.height - boxSize.height == currentLocation.y;
    }

    private static enum State {
        MOVING_UP, MOVING_DOWN, VIEWING, HIDDEN
    }
}
