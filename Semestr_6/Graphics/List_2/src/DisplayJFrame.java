import javax.swing.*;

public class DisplayJFrame extends JFrame {
    private final boolean isResizable;

    public DisplayJFrame(boolean isResizable) {
        this.isResizable = isResizable;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void center() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(this.isResizable);
    }

    public void start() {
        this.setVisible(true);

        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            this.repaint();
        }
    }
}
