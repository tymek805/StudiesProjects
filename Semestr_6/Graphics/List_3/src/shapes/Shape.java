package shapes;

import java.awt.*;

public abstract class Shape {
    protected Point start;
    protected Point end;
    protected Point center;

    protected Color color;

    public Shape(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
        updateCenter();
    }

    public abstract void draw(Graphics2D g);

    public PositionType getPositionType(Point point, int sensitivity) {
        if (point.distance(this.center) <= sensitivity) {
            return PositionType.CENTER;
        }
        return null;
    }

    // Setters
    public void setStart(Point start) {
        this.start = start;
        updateCenter();
    }

    public void setEnd(Point end) {
        this.end = end;
        updateCenter();
    }

    private void updateCenter() {
        this.center = new Point((start.x + end.x) / 2, (start.y + end.y) / 2);
    }

    public void setCenter(Point center) {
        int dx = center.x - this.center.x;
        int dy = center.y - this.center.y;
        this.center = center;
        this.start.x += dx;
        this.start.y += dy;
        this.end.x += dx;
        this.end.y += dy;
    }

    public String serialize() {
        return String.format("%s %d %d %d %d %d %d %d",
                getClass().getSimpleName().toUpperCase(),
                start.x, start.y, end.x, end.y,
                color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Shape deserialize(String shapeValues) {
        String[] parts = shapeValues.split(" ");
        String shapeType = parts[0];
        int startX = Integer.parseInt(parts[1]);
        int startY = Integer.parseInt(parts[2]);
        int endX = Integer.parseInt(parts[3]);
        int endY = Integer.parseInt(parts[4]);
        int red = Integer.parseInt(parts[5]);
        int green = Integer.parseInt(parts[6]);
        int blue = Integer.parseInt(parts[7]);

        Color color = new Color(red, green, blue);
        Point start = new Point(startX, startY);
        Point end = new Point(endX, endY);

        return switch (shapeType.toUpperCase()) {
            case "LINE" -> new Line(start, end, color);
            case "RECTANGLE" -> new Rectangle(start, end, color);
            case "CIRCLE" -> new Circle(start, end, color);
            default -> null;
        };
    }
}
