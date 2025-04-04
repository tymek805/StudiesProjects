package shapes;

import java.awt.*;

public class Rectangle extends Shape {
    public int width;
    public int height;
    public int x;
    public int y;

    public Rectangle(Point start, Point end, Color color) {
        super(start, end, color);
        this.x = Math.min(start.x, end.x);
        this.y = Math.min(start.y, end.y);
        this.width = Math.abs(end.x - start.x);
        this.height = Math.abs(end.y - start.y);
    }

    @Override
    public void setEnd(Point point) {
        super.setEnd(point);
        this.x = Math.min(start.x, end.x);
        this.y = Math.min(start.y, end.y);
        this.width = Math.abs(end.x - start.x);
        this.height = Math.abs(end.y - start.y);
    }

    @Override
    public void setCenter(Point point) {
        this.center = point;
        this.x = center.x - (width / 2);
        this.y = center.y - (height / 2);
        this.start = new Point(x, y);
        this.end = new Point(x + width, y + height);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }
}
