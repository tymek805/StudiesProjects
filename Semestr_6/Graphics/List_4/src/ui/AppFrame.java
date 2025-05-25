package ui;

import controllers.Controller;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    public AppFrame() {
        initialize();
    }

    private void initialize() {
        setTitle("Simple GIMP");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        PosterPanel posterPanel = new PosterPanel();
        DraggablePanel draggablePanel = new DraggablePanel(null);
        Controller controller = new Controller(posterPanel, draggablePanel);
        draggablePanel.setController(controller);

        ImagesPanel imagesPanel = new ImagesPanel(controller);
        imagesPanel.setPreferredSize(new Dimension(200, 0));

        Toolbar toolbar = new Toolbar(controller);

        mainPanel.add(toolbar, BorderLayout.PAGE_START);
        mainPanel.add(imagesPanel, BorderLayout.WEST);
        mainPanel.add(posterPanel, BorderLayout.CENTER);

        draggablePanel.setOpaque(false);
        draggablePanel.setVisible(false);

        setGlassPane(draggablePanel);
        getGlassPane().setVisible(false);

        add(mainPanel);

        MenuPanel menuPanel = new MenuPanel(posterPanel);
        menuPanel.setAddImagesCallback(imagesPanel::addImage);
        setJMenuBar(menuPanel);
        setVisible(true);
    }
}