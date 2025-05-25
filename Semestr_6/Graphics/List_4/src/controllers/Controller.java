package controllers;

import elements.PosterElement;
import ui.DraggablePanel;
import ui.PosterPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller {
    private final PosterPanel posterPanel;
    private PosterElement selectedElement;
    private Point lastMousePosition;
    private final DraggablePanel draggablePanel;

    public Controller(PosterPanel posterPanel, DraggablePanel draggablePanel) {
        this.posterPanel = posterPanel;
        this.draggablePanel = draggablePanel;
        draggablePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                onMouseReleased(e);
            }
        });

        draggablePanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                onMouseDragged(e);
            }
        });
    }


    public void onElementSelected(MouseEvent e, PosterElement element) {
        selectedElement = element;
        lastMousePosition = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), draggablePanel);
        element.setPosition(lastMousePosition.x - element.getBounds().width / 2, lastMousePosition.y - element.getBounds().height / 2);


        draggablePanel.setSelectedElement(selectedElement, lastMousePosition);
        draggablePanel.setVisible(true);
        draggablePanel.setEnabled(true);
    }

    public void onMouseDragged(MouseEvent e) {
        if (selectedElement != null) {
            Point currentMousePosition = e.getPoint();
            int dx = currentMousePosition.x - lastMousePosition.x;
            int dy = currentMousePosition.y - lastMousePosition.y;
            selectedElement.move(dx, dy);
            lastMousePosition = currentMousePosition;
            draggablePanel.repaint();
        }
    }

    public void onMouseReleased(MouseEvent e) {
        if (selectedElement != null) {
            Point dropPoint = SwingUtilities.convertPoint(draggablePanel, e.getPoint(), posterPanel);
            if (!(dropPoint.x < 0 || dropPoint.x > posterPanel.getWidth() || dropPoint.y < 0 || dropPoint.y > posterPanel.getHeight())) {

                selectedElement.setPosition(dropPoint.x - selectedElement.getBounds().width / 2, dropPoint.y - selectedElement.getBounds().height / 2);
                posterPanel.addElement(selectedElement);
            }

            posterPanel.repaint();
            selectedElement = null;
            draggablePanel.clearSelectedElement();
        }
        draggablePanel.setVisible(false);
        draggablePanel.setEnabled(false);
    }

    public void forwardMouseEventToDraggablePanel(MouseEvent e, Component source) {
        MouseEvent converted = SwingUtilities.convertMouseEvent(source, e, draggablePanel);
        draggablePanel.dispatchEvent(converted);
    }
}
