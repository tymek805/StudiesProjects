package shapes;

import java.awt.*;

public class Circle extends Shape {
    private int radius;

    public Circle(Point start, Point end, Color color) {
        super(start, end, color);

        this.radius = (int) Math.sqrt(Math.pow(end.x - center.x, 2) + Math.pow(end.y - center.y, 2)) * 2;
    }

    @Override
    public void setEnd(Point end) {
        this.end = end;
        this.radius = (int) Math.sqrt(Math.pow(end.x - center.x, 2) + Math.pow(end.y - center.y, 2));
    }

    @Override
    public void setCenter(Point center) {
        int dx = center.x - this.center.x;
        int dy = center.y - this.center.y;
        this.start = center;
        this.center = center;
        this.end.x = end.x + dx;
        this.end.y = end.y + dy;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.drawOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
    }
}
