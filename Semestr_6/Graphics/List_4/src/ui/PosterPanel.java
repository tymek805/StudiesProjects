package ui;

import controllers.KeyboardListener;
import elements.PosterElement;
import elements.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PosterPanel extends JPanel {
    
    private final Color backgroundColor = Color.WHITE;
    private final ArrayList<PosterElement> elements = new ArrayList<>();
    private final KeyboardListener keyboardListener;

    private PosterElement selectedElement = null;
    private Point lastMousePosition = null;
    private Vertex selectedVertex = Vertex.NONE;
    private int mouseButton = MouseEvent.NOBUTTON;

    
    public PosterPanel() {
        setBackground(backgroundColor);
        keyboardListener = setupKeyboardListener();
        addKeyListener(keyboardListener);
        setFocusable(true);

        addMouseListener(new MouseInputHandler());
        addMouseMotionListener(new MouseDragHandler());
    }

    
    private class MouseInputHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            mouseButton = e.getButton();
            lastMousePosition = e.getPoint();
            PosterElement clicked = getElementAt(e.getPoint());
            requestFocusInWindow();

            if (mouseButton == MouseEvent.BUTTON1 || mouseButton == MouseEvent.BUTTON2) {
                setSelectedElement(clicked);
                if (selectedElement != null) {
                    selectedVertex = selectedElement.getSelectedVertex(e.getPoint());
                }
                repaint();
            } else if (mouseButton == MouseEvent.BUTTON3 && clicked != null) {
                elements.remove(clicked);
                setSelectedElement(null);
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseButton = MouseEvent.NOBUTTON;
            selectedVertex = Vertex.NONE;
        }
    }

    private class MouseDragHandler extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectedElement == null || lastMousePosition == null || selectedVertex == Vertex.NONE)
                return;

            Point current = e.getPoint();
            int dx = current.x - lastMousePosition.x;
            int dy = current.y - lastMousePosition.y;

            if (selectedVertex == Vertex.CENTER) {
                selectedElement.move(dx, dy);
            } else if (mouseButton == MouseEvent.BUTTON2) {
                rotateElement(current);
            } else {
                scaleElement(current);
            }

            lastMousePosition = current;
            repaint();
        }
    }

    
    private KeyboardListener setupKeyboardListener() {
        KeyboardListener listener = new KeyboardListener();
        listener.addCallback(this::repaint);
        listener.setMoveUpCallback(() -> moveElementUp(selectedElement));
        listener.setMoveDownCallback(() -> moveElementDown(selectedElement));
        return listener;
    }

    
    private void rotateElement(Point current) {
        double cx = selectedElement.getCenterX();
        double cy = selectedElement.getCenterY();
        double prevAngle = angleBetweenPoints(cx, cy, lastMousePosition.x, lastMousePosition.y);
        double currAngle = angleBetweenPoints(cx, cy, current.x, current.y);
        selectedElement.rotate(Math.toRadians(Math.toDegrees(currAngle - prevAngle)));
    }

    private void scaleElement(Point current) {
        Point2D anchor = getAnchorPoint(selectedVertex); 
    
        double dx1 = lastMousePosition.x - anchor.getX();
        double dy1 = lastMousePosition.y - anchor.getY();
        double dx2 = current.x - anchor.getX();
        double dy2 = current.y - anchor.getY();
    
        double sx = dx2 / dx1;
        double sy = dy2 / dy1;

        selectedElement.scaleFromAnchor(anchor, sx, sy);
    }

    private double angleBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.atan2(y2 - y1, x2 - x1);
    }
    private Point2D getAnchorPoint(Vertex selected) {
        Point2D.Double[] vertices = selectedElement.getVertices();
        return switch (selected) {
            case TOP_LEFT -> vertices[3];       
            case TOP_RIGHT -> vertices[2];     
            case BOTTOM_LEFT -> vertices[1];    
            case BOTTOM_RIGHT -> vertices[0];   
            default -> new Point2D.Double(selectedElement.getCenterX(), selectedElement.getCenterY());
        };
    }
    

    
    public PosterElement getElementAt(Point point) {
        for (int i = elements.size() - 1; i >= 0; i--) {
            PosterElement el = elements.get(i);
            if (el.contains(point) || el.getSelectedVertex(point) != Vertex.NONE)
                return el;
        }
        return null;
    }

    public void setSelectedElement(PosterElement element) {
        if (selectedElement != null) selectedElement.setSelected(false);
        selectedElement = element;
        if (selectedElement != null) selectedElement.setSelected(true);
        this.keyboardListener.setSelectedElement(selectedElement);
        repaint();
    }

    public void addElement(PosterElement element) {
        elements.add(element);
        repaint();
    }

    public List<PosterElement> getPosterElements() {
        return elements;
    }

    public void setPosterElements(List<PosterElement> newElements) {
        elements.clear();
        elements.addAll(newElements);
        repaint();
    }

    public void moveElementUp(PosterElement element) {
        if (element == null) return;
        int i = elements.indexOf(element);
        if (i >= 0 && i < elements.size() - 1) {
            elements.remove(i);
            elements.add(i + 1, element);
            repaint();
        }
    }

    public void moveElementDown(PosterElement element) {
        if (element == null) return;
        int i = elements.indexOf(element);
        if (i > 0) {
            elements.remove(i);
            elements.add(i - 1, element);
            repaint();
        }
    }

    public BufferedImage getImage(int width, int height) {
        int minWidth = elements.stream().mapToInt(el -> el.getBounds().x + el.getBounds().width).max().orElse(width);
        int minHeight = elements.stream().mapToInt(el -> el.getBounds().y + el.getBounds().height).max().orElse(height);
        width = Math.max(width, minWidth);
        height = Math.max(height, minHeight);

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();

        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);

        for (PosterElement el : elements) {
            el.draw(g2d);
        }
        g2d.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (PosterElement el : elements) {
            el.draw(g2d);
        }
    }
}
