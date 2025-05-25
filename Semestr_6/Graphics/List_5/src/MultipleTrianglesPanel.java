import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultipleTrianglesPanel extends JPanel {
    private final int width;
    private final int height;
    private final List<Triangle> triangles;
    private final BufferedImage compositeImage;

    public MultipleTrianglesPanel(int width, int height, int triangleCount) {
        this.width = width;
        this.height = height;
        this.triangles = new ArrayList<>();
        this.compositeImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        generateTriangles(triangleCount);
        drawAllTriangles();
        saveCompositeImageToFile();
        setPreferredSize(new Dimension(width, height));
    }

    private void generateTriangles(int count) {
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            int offsetX = rand.nextInt(width);
            int offsetY = rand.nextInt(height);

            Vertex v1 = new Vertex(rand.nextInt(width - offsetX), rand.nextInt(height - offsetY), randomColor());
            Vertex v2 = new Vertex(rand.nextInt(width - offsetX), rand.nextInt(height - offsetY), randomColor());
            Vertex v3 = new Vertex(rand.nextInt(width - offsetX), rand.nextInt(height - offsetY), randomColor());

            Triangle triangle = new Triangle(v1, v2, v3, offsetX, offsetY);
            triangles.add(triangle);
        }
    }

    private void saveCompositeImageToFile() {
        try {
            File outputfile = new File("multiple_triangles_output.png");
            ImageIO.write(compositeImage, "png", outputfile);
            System.out.println("Composite image saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Color randomColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    private void drawAllTriangles() {
        Graphics2D g2d = compositeImage.createGraphics();

        for (Triangle triangle : triangles) {
            BufferedImage img = triangle.getImage();
            g2d.drawImage(img, 0, 0, null);
        }

        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(compositeImage, 0, 0, null);
    }

    public int calculateTotalArea() {
        int total = 0;
        for (Triangle t : triangles) {
            total += t.getArea();
        }
        return total;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Multiple Triangles (Composited)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            int triangleCount = 500;

            long startTime = System.nanoTime();
            MultipleTrianglesPanel panel = new MultipleTrianglesPanel(800, 800, triangleCount);
            long endTime = System.nanoTime();
            frame.add(panel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);

            double elapsedMillis = (endTime - startTime) / 1_000_000.0;
            System.out.println("Triangles / ms: " + (triangleCount / elapsedMillis));
            System.out.println("Pixels / ms: " + (panel.calculateTotalArea() / elapsedMillis));
        });
    }
}
