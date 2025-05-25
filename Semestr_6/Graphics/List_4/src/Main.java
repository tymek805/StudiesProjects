import ui.AppFrame;
import javax.swing.*;

public class Main extends JPanel {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "2.0");
        SwingUtilities.invokeLater(() -> {
            AppFrame appFrame = new AppFrame();
            appFrame.setVisible(true);
        });
    }
}
