import javax.swing.*;
import java.awt.*;

public class SolarSystem extends JPanel {
    private int centerX, centerY;
    private final double startTime;
    private int earthX, earthY;

    public SolarSystem() {
        super();

        this.startTime = System.currentTimeMillis();
    }

    private void drawPlanet(int planetSize, double duration, int radius, Color color, double angle, Graphics g, boolean isEarth) {
        int ballX = (int) (centerX + radius * Math.sin(angle / duration));
        int ballY = (int) (centerY + radius * Math.cos(angle / duration));

        g.setColor(Color.WHITE);
        g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        g.setColor(color);
        g.fillOval(ballX - planetSize / 2, ballY - planetSize / 2, planetSize, planetSize);

        if (isEarth) {
            earthX = ballX;
            earthY = ballY;
        }
    }

    private void drawSun(int size, Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(centerX - size / 2, centerY - size / 2, size, size);
    }

    private void drawMoon(int size, double duration, int radius, double angle, Graphics g) {
        int ballX = (int) (earthX + radius * Math.sin(angle / duration));
        int ballY = (int) (earthY + radius * Math.cos(angle / duration));

        g.setColor(Color.WHITE);
        g.drawOval(earthX - radius, earthY - radius, radius * 2, radius * 2);

        g.setColor(Color.GRAY);
        g.fillOval(ballX - size / 2, ballY - size / 2, size, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double durationModifier = 0.002;

        Dimension size = getSize();

        centerX = size.width / 2;
        centerY = size.height / 2;

        double angle = (System.currentTimeMillis() - startTime) / 1000;

        drawSun(30, g);

        int[] sizes = {10, 20, 20, 15, 100, 80, 40, 40};
        int[] durations = {88, 225, 365, 387, 4333, 10759, 30687, 60190};
        int[] distances = {10, 10, 10, 10, 10, 10, 10, 10};
        String[] colors = {"#e6641e", "#e6e6e6", "#2f6a69", "#993d00", "#b07f35", "#b08f36", "#5580aa", "#366896"};
        int lastDistance = 15;

        for (int i = 0; i < sizes.length; i++) {
            drawPlanet(sizes[i], durations[i] * durationModifier, distances[i] + lastDistance + sizes[i] / 2, Color.decode(colors[i]), angle, g, i == 2);
            lastDistance += sizes[i] + distances[i];
        }
        drawMoon(10, 1, 5 + sizes[2] / 2, angle, g);
        setBackground(Color.DARK_GRAY);
    }

    public static void main(String[] args) {
        DisplayJFrame frame = new DisplayJFrame(false);
        frame.getContentPane().add(new SolarSystem());
        frame.setTitle("Solar system");
        frame.setMinimumSize(new Dimension(900, 900));
        frame.center();
        frame.start();
    }
}
