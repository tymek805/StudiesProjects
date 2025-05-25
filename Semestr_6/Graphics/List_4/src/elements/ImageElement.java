package elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageElement extends PosterElement {
    private final String path;
    private BufferedImage image;

    public ImageElement(String path) {
        this.path = path;
        loadImage();
        this.originalBounds = getBounds();
    }

    private void loadImage() {
        try {
            this.image = ImageIO.read(new File(this.path));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + e.getMessage());
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage image = getImage();
        g2d.drawImage(image, transform, null);
        drawSelected(g2d);
    }

    @Override
    public boolean contains(Point point) {
        try {
            AffineTransform inverse = transform.createInverse();
            Point2D local = inverse.transform(point, null);
            Rectangle imageBounds = new Rectangle(0, 0, getImage().getWidth(), getImage().getHeight());
            return imageBounds.contains(local);
        } catch (NoninvertibleTransformException e) {
            return false;
        }
    }

    @Override
    public Rectangle getBounds() {
        return transform.createTransformedShape(new Rectangle(0, 0, this.image.getWidth(), this.image.getHeight())).getBounds();
    }

    @Override
    public PosterElement copy() {
        return new ImageElement(this.path);
    }
}
