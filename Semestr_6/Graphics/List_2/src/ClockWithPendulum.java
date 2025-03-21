import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.*;
import java.lang.Thread;
import java.lang.InterruptedException;

import javax.swing.*;

public class ClockWithPendulum {
    public static void main(String[] args) {
        double frequency = 2;
        double amplitude = 0.3;

        amplitude = Math.max(Math.min(amplitude, Math.PI / 2), -Math.PI / 2);

        System.out.println("Running with parameters:\n" + "Amplitude: " + amplitude + "\nFrequency: " + frequency);

        SmpWindow wnd = new SmpWindow(frequency, amplitude);

        // Closing window terminates the program
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setBounds(70, 70, 300, 500);
        wnd.setVisible(true);

        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            wnd.repaint();
        }
    }
}

class DrawWndPane extends JPanel {
    final int GAUGE_LEN = 10;
    int center_x, center_y;
    int r_outer, r_inner;
    GregorianCalendar calendar;

    double startTime;
    double frequency, amplitude;

    DrawWndPane(double frequency, double amplitude) {
        super();

        this.startTime = System.currentTimeMillis();
        this.frequency = frequency;
        this.amplitude = amplitude;

        setBackground(new Color(200, 200, 255));
        calendar = new GregorianCalendar();
    }

    public void drawGauge(double angle, Graphics g) {
        int xw, yw, xz, yz;

        angle = 3.1415 * angle / 180.0;
        xw = (int) (center_x + r_inner * Math.sin(angle));
        yw = (int) (center_y - r_inner * Math.cos(angle));
        xz = (int) (center_x + r_outer * Math.sin(angle));
        yz = (int) (center_y - r_outer * Math.cos(angle));

        g.drawLine(xw, yw, xz, yz);
    }

    public void drawHand(double angle, int length, Graphics g) {
        int xw, yw, xz, yz;

        angle = 3.1415 * angle / 180.0;
        xw = (int) (center_x + length * Math.sin(angle));
        yw = (int) (center_y - length * Math.cos(angle));

        angle += 3.1415;
        xz = (int) (center_x + GAUGE_LEN * Math.sin(angle));
        yz = (int) (center_y - GAUGE_LEN * Math.cos(angle));

        g.drawLine(xw, yw, xz, yz);
    }

    public void drawDial(Graphics g) {
        g.drawOval(center_x - r_outer, center_y - r_outer, 2 * r_outer, 2 * r_outer);

        for (int i = 0; i <= 11; i++) {
            drawGauge(i * 30.0, g);
        }
    }

    public void drawPendulum(double angle, Graphics g) {
        int anchorX = center_x;
        int anchorY = center_y * 2;
        int length = center_y * 4;

        int ballSize = length / 10;
        int ballX = (int) (anchorX + length * Math.sin(angle));
        int ballY = (int) (anchorY + length * Math.cos(angle)) - ballSize / 2;


        g.drawLine(anchorX, anchorY, ballX, ballY);
        g.fillOval(ballX - ballSize / 2, ballY - ballSize / 2, ballSize, ballSize);
    }

    public void paint(Graphics g) {
        paintComponent(g);
    }

    public void paintComponent(Graphics g) {
        int minute, second, hour;

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Dimension size = getSize();

        center_x = size.width / 2;
        center_y = size.height / 6;
        r_outer = Math.min(size.width, size.height / 3) / 2;
        r_inner = r_outer - GAUGE_LEN;

        Date time = new Date();
        calendar.setTime(time);

        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR);
        if (hour > 11) {
            hour = hour - 12;
        }
        second = calendar.get(Calendar.SECOND);

        drawDial(g);

        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(5));
        drawHand(360.0 * (hour * 60 + minute) / (60.0 * 12), (int) (0.75 * r_inner), g);

        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(3));
        drawHand(360.0 * (minute * 60 + second) / (3600.0), (int) (0.97 * r_outer), g);

        g2d.setColor(new Color(0, 0, 0));
        g2d.setStroke(new BasicStroke(1));
        drawHand(second * 6.0, (int) (0.97 * r_inner), g);
        double angle = amplitude *  Math.sin(2 * Math.PI * (System.currentTimeMillis() - startTime) / 1000.0 / frequency);
        drawPendulum(angle, g);
    }
}

class SmpWindow extends JFrame {
    public SmpWindow(double frequency, double amplitude) {
        Container contents = getContentPane();
        contents.add(new DrawWndPane(frequency, amplitude));
        setTitle("Clock with pendulum");
    }
}