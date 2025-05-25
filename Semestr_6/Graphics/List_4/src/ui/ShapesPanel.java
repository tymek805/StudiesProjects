package ui;

import controllers.Controller;
import elements.ShapeElement;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ShapesPanel extends DraggablePosterElementsPanel<ShapeElement> {
    private Color currentColor = Color.BLACK;

    private final List<ShapeElement> shapes = List.of(
            new ShapeElement(new Rectangle2D.Double(0, 0, 50, 50), currentColor),
            new ShapeElement(new Ellipse2D.Double(0, 0, 50, 50), currentColor)
    );

    public ShapesPanel(Controller controller) {
        super(controller);
        contentPanel.setLayout(new GridLayout(1, 0, 10, 10));
        for (ShapeElement shape : shapes) {
            addElement(shape);
        }

        JButton colorButton = new JButton("Choose Color");
        colorButton.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(this, "Pick a Color", currentColor);
            if (chosen != null) {
                currentColor = chosen;
                refreshShapes();
            }
        });
        contentPanel.add(colorButton);
        contentPanel.revalidate();
    }

    private void refreshShapes() {
        for (ShapeElement shape : shapes) {
            shape.setColor(currentColor);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
