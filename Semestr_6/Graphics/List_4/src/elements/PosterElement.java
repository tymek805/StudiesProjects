package elements;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class PosterElement {
    private final Point2D.Double[] vertices;
    private final Point2D.Double center;

    protected AffineTransform transform;
    protected Rectangle2D originalBounds;

    private boolean isSelected;

    public PosterElement() {
        this.transform = new AffineTransform();
        this.vertices = new Point2D.Double[4];
        this.center = new Point2D.Double();
        this.isSelected = false;
    }

    public abstract void draw(Graphics2D g2d);
    public abstract boolean contains(Point p);
    public abstract Rectangle getBounds();
    public abstract  PosterElement copy();

    public void move(double dx, double dy) {
        AffineTransform move = new AffineTransform();
        move.translate(dx, dy);
        move.concatenate(transform);
        transform = move;
        updateVertices();
        updateCenter();
    }

    public void rotate(double angle) {
        AffineTransform rotate = new AffineTransform();
        rotate.translate(center.getX(), center.getY());
        rotate.rotate(angle);
        rotate.translate(-center.getX(), -center.getY());
        rotate.concatenate(transform);
        transform = rotate;
        updateVertices();
        updateCenter();
    }

    public void scale(double x, double y) {
        performScale(center, x, y);
    }

    public void scaleFromAnchor(Point2D anchor, double x, double y){
        performScale(anchor, x, y);
    }

    private void performScale(Point2D point, double x, double y) {
        AffineTransform scaleTransform = new AffineTransform();
        scaleTransform.translate(point.getX(), point.getY());
        scaleTransform.scale(x, y);
        scaleTransform.translate(-point.getX(), -point.getY());
        scaleTransform.concatenate(transform);
        transform = scaleTransform;
        updateVertices();
        updateCenter();
    }

    public void drawSelected(Graphics2D g2d) {
        if (!isSelected) return;
        g2d.setColor(Color.MAGENTA);
        for (Point2D point : this.vertices) {
            drawHandle(g2d, point);
        }
        drawHandle(g2d, this.center);
    }

    private void updateVertices() {
        vertices[0] = (Point2D.Double) transform.transform(new Point2D.Double(originalBounds.getMinX(), originalBounds.getMinY()), null);
        vertices[1] = (Point2D.Double) transform.transform(new Point2D.Double(originalBounds.getMaxX(), originalBounds.getMinY()), null);
        vertices[2] = (Point2D.Double) transform.transform(new Point2D.Double(originalBounds.getMinX(), originalBounds.getMaxY()), null);
        vertices[3] = (Point2D.Double) transform.transform(new Point2D.Double(originalBounds.getMaxX(), originalBounds.getMaxY()), null);
    }

    private void updateCenter() {
        Rectangle2D transformedBounds = transform.createTransformedShape(originalBounds).getBounds2D();
        center.setLocation(transformedBounds.getCenterX(), transformedBounds.getCenterY());
    }

    private void drawHandle(Graphics2D g2d, Point2D point) {
        int radius = 5;
        g2d.fillOval((int) (point.getX() - radius), (int) (point.getY() - radius), radius * 2, radius * 2);
    }

    public Point2D.Double[] getVertices() {
        return vertices;
    }

    public Vertex getSelectedVertex(Point2D point) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].distance(point) < 5) {
                return Vertex.values()[i];
            }
        }
        if (center.distance(point) < 5) {
            return Vertex.CENTER;
        }
        return Vertex.NONE;
    }

    public double getCenterX() {
        return transform.createTransformedShape(originalBounds).getBounds2D().getCenterX();
    }

    public double getCenterY() {
        return transform.createTransformedShape(originalBounds).getBounds2D().getCenterY();
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setPosition(int x, int y) {
        transform.setToTranslation(x, y);
        updateVertices();
        updateCenter();
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(";");
        sb.append(originalBounds.getX()).append(";").append(originalBounds.getY()).append(";");
        sb.append(originalBounds.getWidth()).append(";").append(originalBounds.getHeight());
        double[] matrix = new double[6];
        transform.getMatrix(matrix);
        for (double val : matrix) {
            sb.append(";").append(val);
        }
        return sb.toString();
    }

    public static PosterElement deserialize(String data) {
        String[] parts = data.split(";");
        String className = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double width = Double.parseDouble(parts[3]);
        double height = Double.parseDouble(parts[4]);

        double[] matrix = new double[6];
        for (int i = 0; i < 6; i++) {
            matrix[i] = Double.parseDouble(parts[5 + i]);
        }
        PosterElement element = switch (className) {
            case "ImageElement" -> {
                String imagePath = parts[11];
                yield new ImageElement(imagePath);
            }
            case "ShapeElement" -> {
                int r = Integer.parseInt(parts[11]);
                int g = Integer.parseInt(parts[12]);
                int b = Integer.parseInt(parts[13]);
                Color color = new Color(r, g, b);
                Shape shape;
                String shapeType = parts[14];
                if (shapeType.equalsIgnoreCase("Circle")) {
                    shape = new Ellipse2D.Double(x, y, width, height);
                } else if (shapeType.equals("Rectangle")) {
                    shape = new Rectangle2D.Double(x, y, width, height);
                }
                else {
                    throw new IllegalArgumentException("Unknown shape type: " + shapeType);
                }
                yield new ShapeElement(shape, color);
            }
            default -> throw new IllegalArgumentException("Unknown class name: " + className);
        };
        element.transform.setTransform(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
        element.originalBounds = new Rectangle2D.Double(x, y, width, height);
        element.updateVertices();
        element.updateCenter();

        return element;
    }
}
