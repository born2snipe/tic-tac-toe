package b2s.tictactoe;

import java.awt.*;


public class Line implements Renderable, Tickable {
    private final Point from, to;
    private Color color = Color.black;
    private int lineWidth = 3;
    private Point currentTo;
    private static final int STEP = 10;

    public Line(Point from, Point to, int lineWidth) {
        this(from, to, lineWidth, true);
    }

    public Line(Point from, Point to, int lineWidth, boolean staticLine) {
        this.from = from;
        this.to = to;
        this.lineWidth = lineWidth;

        if (staticLine) {
            currentTo = to;
        } else {
            currentTo = new Point(from);
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawLine(from.x, from.y, currentTo.x, currentTo.y);
    }

    public void tick(int ticksPerSecond) {
        if (isVertical()) {
            currentTo = new Point(currentTo.x, currentTo.y + STEP);
        } else if (isHorizontal()) {
            currentTo = new Point(currentTo.x + STEP, currentTo.y);
        }
    }

    public boolean isDone() {
        return to.equals(currentTo);
    }

    private boolean isVertical() {
        return from.x == to.x;
    }

    private boolean isHorizontal() {
        return from.y == to.y;
    }
}
