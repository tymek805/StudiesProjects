package ui;

import shapes.ShapeType;

import javax.swing.*;
import java.net.URL;

public class Toolbar extends JToolBar {
    private final Controller controller;
    private final RGBColorPicker rgbColorPicker;

    public Toolbar(Controller controller){
        super();
        this.controller = controller;
        this.rgbColorPicker = new RGBColorPicker(controller);

        addButtons();
        add(rgbColorPicker);
        addFileMenu();

        setFloatable(false);
    }

    private void addButtons() {
        ButtonGroup buttonGroup = new ButtonGroup();

        JToggleButton lineButton = createToolbarButton("line", ShapeType.LINE);
        this.add(lineButton);
        buttonGroup.add(lineButton);

        JToggleButton squareButton = createToolbarButton("rectangle", ShapeType.RECTANGLE);
        this.add(squareButton);
        buttonGroup.add(squareButton);

        JToggleButton circleButton = createToolbarButton("circle", ShapeType.CIRCLE);
        this.add(circleButton);
        buttonGroup.add(circleButton);
    }

    private JToggleButton createToolbarButton(String imageName, ShapeType shapeType) {
        String imgLocation = "/resources/" + imageName + ".png";
        URL imageURL = getClass().getResource(imgLocation);

        JToggleButton button = new JToggleButton();
        button.addActionListener(e -> this.controller.setSelectedShapeType(shapeType));

        if (imageURL != null) {
            button.setIcon(new ImageIcon(imageURL, imageName));
        } else {
            button.setText(imageName);
            System.err.println("Resource not found: " + imgLocation);
        }
        button.setFocusPainted(false);

        return button;
    }

    private void addFileMenu() {
        String imageName = "file";
        URL imageURL = getClass().getResource("/resources/folder.png");

        JButton menuButton = new JButton("File");
        if (imageURL != null) {
            menuButton.setIcon(new ImageIcon(imageURL, imageName));
        } else {
            menuButton.setText(imageName);
            System.err.println("Resource not found: " + imageName);
        }
        menuButton.setFocusPainted(false);

        JPopupMenu popupMenu = getPopupMenu();
        menuButton.addActionListener(e -> {
            popupMenu.show(menuButton, 0, menuButton.getHeight());
        });
        menuButton.setFocusPainted(false);
        add(menuButton);
    }

    private JPopupMenu getPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem exportItem = new JMenuItem("Export");
        JMenuItem newItem = new JMenuItem("New");
        exportItem.addActionListener(e -> controller.exportToJpg());
        saveItem.addActionListener(e -> controller.saveShapes());
        loadItem.addActionListener(e -> controller.loadShapes());
        newItem.addActionListener(e -> controller.newFile());
        popupMenu.add(newItem);
        popupMenu.add(saveItem);
        popupMenu.add(loadItem);
        popupMenu.add(exportItem);
        return popupMenu;
    }
}
