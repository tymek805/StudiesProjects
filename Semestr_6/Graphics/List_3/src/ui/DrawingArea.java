package ui;

import shapes.*;
import shapes.Rectangle;
import shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.function.Consumer;

public class DrawingArea extends JPanel {
    private final static int SENSITIVITY = 20;

    private final Controller controller;
    private Shape newShape;
    private Shape selectedShape;
    private PositionType positionType;

    public DrawingArea(Controller controller) {
        super();

        this.controller = controller;
        this.newShape = null;
        this.positionType = null;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();

                for (Shape shape : controller.getShapes()) {
                    positionType = shape.getPositionType(point, SENSITIVITY);
                    if (positionType == PositionType.CENTER && e.getButton() == MouseEvent.BUTTON3) {
                        drawXOR(shape, null, null);
                        controller.removeShape(shape);
                    }

                    if (positionType != null) {
                        selectedShape = shape;
                        return;
                    }
                }

                if (e.getButton() == MouseEvent.BUTTON1) {
                    newShape = createNewShape(point);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (newShape != null) {
                    controller.addShape(newShape);
                    newShape = null;
                }
                if (selectedShape != null) {
                    selectedShape = null;
                    positionType = null;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point newPoint = e.getPoint();

                if (newShape != null) {
                    drawXOR(newShape, newPoint, newShape::setEnd);
                }

                if (selectedShape == null) {
                    return;
                }

                if (positionType == PositionType.CENTER) {
                    drawXOR(selectedShape, newPoint, selectedShape::setCenter);
                } else if (positionType == PositionType.START) {
                    drawXOR(selectedShape, newPoint, selectedShape::setStart);
                } else if (positionType == PositionType.END) {
                    drawXOR(selectedShape, newPoint, selectedShape::setEnd);
                }
            }
        });

        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Shape shape : controller.getShapes()) {
            shape.draw(g2d);
        }
    }

    private Shape createNewShape(Point point) {
        ShapeType shapeType = controller.getSelectedShapeType();
        Color color = controller.getColor();

        if (shapeType == ShapeType.LINE) {
            return new Line(point, point, color);
        } else if (shapeType == ShapeType.RECTANGLE) {
            return new Rectangle(point, point, color);
        } else if (shapeType == ShapeType.CIRCLE) {
            return new Circle(point, point, color);
        }

        return null;
    }

    private void drawXOR(Shape shape, Point newPoint, Consumer<Point> updateFunction) {
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.setXORMode(Color.WHITE);
        shape.draw(g2d);
        if (updateFunction != null) {
            updateFunction.accept(newPoint);
            shape.draw(g2d);
        }
        g2d.setPaintMode();
    }
}
