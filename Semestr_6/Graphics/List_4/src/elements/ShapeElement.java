package elements;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class ShapeElement extends PosterElement {
    private final Shape shape;
    private Color color;
    private Shape transformedShape;

    public ShapeElement(Shape shape, Color color) {
        super();
        this.shape = shape;
        this.color = color;
        this.originalBounds = this.shape.getBounds2D();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(3));
        g2d.fill(transform.createTransformedShape(shape));
        drawSelected(g2d);
    }

    @Override
    public boolean contains(Point point) {
        try {
            AffineTransform inverse = transform.createInverse();
            Point2D local = inverse.transform(point, null);
            return shape.contains(local);
        } catch (NoninvertibleTransformException e) {
            return false;
        }
    }

    @Override
    public Rectangle getBounds() {
        return transform.createTransformedShape(shape).getBounds();
    }

    @Override
    public PosterElement copy() {
        return new ShapeElement(shape, color);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
