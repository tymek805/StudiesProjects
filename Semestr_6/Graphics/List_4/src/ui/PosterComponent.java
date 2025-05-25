package ui;

import elements.PosterElement;

import javax.swing.*;
import java.awt.*;

public class PosterComponent extends JComponent {
    private final PosterElement element;

    public PosterComponent(PosterElement element) {
        this.element = element;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        Rectangle bounds = element.getBounds();
        if (bounds.width <= 0 || bounds.height <= 0) return;

        double scaleX = (double) getWidth() / bounds.width;
        double scaleY = (double) getHeight() / bounds.height;
        double scale = Math.min(scaleX, scaleY);

        int scaledWidth = (int) (bounds.width * scale);
        int scaledHeight = (int) (bounds.height * scale);

        int x = (getWidth() - scaledWidth) / 2;
        int y = (getHeight() - scaledHeight) / 2;

        g2d.translate(x, y);
        g2d.scale(scale, scale); 
        element.draw(g2d);

        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Rectangle bounds = element.getBounds();
        int maxDim = 80;
        if (bounds.width == 0 || bounds.height == 0) return new Dimension(maxDim, maxDim);

        double scale = Math.min((double) maxDim / bounds.width, (double) maxDim / bounds.height);
        int w = (int) (bounds.width * scale);
        int h = (int) (bounds.height * scale);
        return new Dimension(w, h);
    }
}
