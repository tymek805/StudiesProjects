package ui;

import controllers.Controller;
import elements.PosterElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class DraggablePosterElementsPanel<T extends PosterElement> extends JScrollPane {
    protected Controller controller;
    protected JPanel contentPanel;
    protected ArrayList<T> elements = new ArrayList<>();

    public DraggablePosterElementsPanel(Controller controller) {
        this.controller = controller;
        this.contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 2, 10, 10));
        prepareUI();
        setViewportView(contentPanel);
    }

    private void prepareUI() {
        setBackground(Color.LIGHT_GRAY);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    protected void addElement(T element) {
        PosterComponent component = new PosterComponent(element);
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    removeElement(component, element);
                } else {
                    controller.onElementSelected(e, element.copy());
                    controller.forwardMouseEventToDraggablePanel(e, component);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                controller.forwardMouseEventToDraggablePanel(e, component);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                controller.forwardMouseEventToDraggablePanel(e, component);
            }
        };
        component.addMouseListener(mouseHandler);
        component.addMouseMotionListener(mouseHandler); 
        contentPanel.add(component);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    protected void removeElement(PosterComponent component, T element) {
        contentPanel.remove(component);
        contentPanel.revalidate();
        contentPanel.repaint();
        elements.remove(element);
    }
}
