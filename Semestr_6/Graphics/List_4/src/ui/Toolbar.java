package ui;

import controllers.Controller;

import javax.swing.*;

public class Toolbar extends JToolBar {
    public Toolbar(Controller controller){
        super();

        ShapesPanel shapesPanel = new ShapesPanel(controller);
        add(shapesPanel);
        setFloatable(false);
    }
}
