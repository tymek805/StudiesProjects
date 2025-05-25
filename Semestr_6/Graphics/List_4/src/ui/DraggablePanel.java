package ui;

import controllers.Controller;
import elements.PosterElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
public class DraggablePanel extends JPanel {
    private PosterElement selectedElement;
    private Controller controller;

    public DraggablePanel(Controller controller) {
        this.controller = controller;
        setOpaque(false);
        setLayout(null);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
            if (selectedElement != null && controller != null) {
                controller.onMouseDragged(e);
            }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            if (selectedElement != null && controller != null) {
                controller.onMouseReleased(e);;
            }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (selectedElement != null) {
            Graphics2D g2d = (Graphics2D) g;
            selectedElement.draw(g2d);
        }
    }

    public void setSelectedElement(PosterElement element, Point posPoint) {
        this.selectedElement = element;
        repaint();
    }

    public void clearSelectedElement() {
        this.selectedElement = null;
        repaint();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }
}