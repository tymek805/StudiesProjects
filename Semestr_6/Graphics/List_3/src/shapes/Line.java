package shapes;

import java.awt.*;

public class Line extends Shape {
    public Line(Point start, Point end, Color color) {
        super(start, end, color);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    @Override
    public PositionType getPositionType(Point point, int sensitivity) {
        if (point.distance(center) <= sensitivity) {
            return PositionType.CENTER;
        }
        if (point.distance(start) <= sensitivity) {
            return PositionType.START;
        }
        if (point.distance(end) <= sensitivity) {
            return PositionType.END;
        }
        return null;
    }
}
