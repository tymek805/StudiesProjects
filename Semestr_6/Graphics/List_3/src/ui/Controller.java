package ui;

import shapes.Shape;
import shapes.ShapeType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Controller {
    private ShapeType selectedShapeType;
    private Color color;
    private ArrayList<Shape> shapes;
    private DrawingArea drawingArea;

    public Controller() {
        this.shapes = new ArrayList<>();
        this.color = Color.BLACK;
        this.drawingArea = null;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public void clearShapes() {
        this.shapes.clear();
        if (drawingArea != null) {
            drawingArea.repaint();
        }
    }

    public void newFile() {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new drawing?",
                "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            clearShapes();
        }
    }

    // Getters
    public ShapeType getSelectedShapeType() {
        return selectedShapeType;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public Color getColor() {
        return color;
    }

    // Setters
    public void setSelectedShapeType(ShapeType selectedShapeType) {
        this.selectedShapeType = selectedShapeType;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDrawingArea(DrawingArea drawingArea) {
        this.drawingArea = drawingArea;
    }

    public void exportToJpg() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export to JPG");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG Image", "jpg"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".jpg")) {
                filePath += ".jpg";
            }

            BufferedImage image = new BufferedImage(drawingArea.getWidth(), drawingArea.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            drawingArea.paint(g2d);
            g2d.dispose();

            try {
                ImageIO.write(image, "jpg", new File(filePath));
                System.out.println("Panel saved as " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveShapes() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save image");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Shape Files (*.shapes)", "shapes"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filename = fileToSave.getAbsolutePath();

            if (!filename.toLowerCase().endsWith(".shapes")) {
                filename += ".shapes";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (Shape shape : shapes) {
                    writer.write(shape.serialize());
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(null, "Shapes saved successfully to " + filename);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving shapes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadShapes() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Shape File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Shape Files (*.shapes)", "shapes"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            ArrayList<Shape> shapes = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen.getAbsolutePath()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    shapes.add(Shape.deserialize(line));
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading shapes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            this.shapes = shapes;
            drawingArea.repaint();
        }
    }
}
