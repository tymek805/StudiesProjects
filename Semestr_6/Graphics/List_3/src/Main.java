import ui.Controller;
import ui.DrawingArea;
import ui.Toolbar;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class Main extends JPanel {
    public Main() {
        super(new BorderLayout());

        Controller controller = new Controller();

        Toolbar toolBar = new Toolbar(controller);
        DrawingArea drawingArea = new DrawingArea(controller);
        controller.setDrawingArea(drawingArea);
        setPreferredSize(new Dimension(1000, 600));

        add(toolBar, BorderLayout.PAGE_START);
        add(drawingArea, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Paint");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new Main());

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}