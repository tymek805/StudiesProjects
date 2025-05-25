import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Triangle extends JPanel {
    private final Vertex topVertex;
    private final Vertex leftVertex;
    private final Vertex rightVertex;

    private final int width;
    private final int height;

    private final int offsetX;
    private final int offsetY;

    private int area = 0;
    private BufferedImage image;

    public Triangle(Vertex topVertex, Vertex rightVertex, Vertex leftVertex) {
        this.topVertex = topVertex;
        this.rightVertex = rightVertex;
        this.leftVertex = leftVertex;

        this.width = Math.max(topVertex.x(), Math.max(leftVertex.x(), rightVertex.x()));
        this.height = Math.max(topVertex.y(), Math.max(leftVertex.y(), rightVertex.y()));

        offsetX = 0;
        offsetY = 0;

        setPreferredSize(new Dimension(width, height));
        generateTriangleImage();
        saveToFile();
    }

    public Triangle(Vertex topVertex, Vertex rightVertex, Vertex leftVertex, int offsetX, int offsetY) {
        this.topVertex = topVertex;
        this.rightVertex = rightVertex;
        this.leftVertex = leftVertex;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        this.width = Math.max(topVertex.x(), Math.max(leftVertex.x(), rightVertex.x())) + offsetX;
        this.height = Math.max(topVertex.y(), Math.max(leftVertex.y(), rightVertex.y())) + offsetY;

        setPreferredSize(new Dimension(width, height));
        generateTriangleImage();
    }

    private void generateTriangleImage() {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        int dxTopRight = topVertex.y() - rightVertex.y();
        int dxRightLeft = rightVertex.y() - leftVertex.y();
        int dxLeftTop = leftVertex.y() - topVertex.y();

        int area = edgeFunction(topVertex.x(), topVertex.y(), rightVertex, leftVertex);
        if (area == 0) return;
        float invArea = 1.0f / area;

        for (int y = 0; y < height; y++) {
            int edgeTopRight = edgeFunction(0, y, topVertex, rightVertex);
            int edgeRightLeft = edgeFunction(0, y, rightVertex, leftVertex);
            int edgeLeftTop = edgeFunction(0, y, leftVertex, topVertex);

            float weight1 = edgeFunction(0, y, rightVertex, leftVertex) * invArea;
            float weight2 = edgeFunction(0, y, leftVertex, topVertex) * invArea;
            float weight3 = 1.0f - weight1 - weight2;

            float dxWeight1 = (rightVertex.y() - leftVertex.y()) * invArea;
            float dxWeight2 = (leftVertex.y() - topVertex.y()) * invArea;

            for (int x = 0; x < width; x++) {
                if (edgeTopRight >= 0 && edgeRightLeft >= 0 && edgeLeftTop >= 0) {
                    this.area++;
                    image.setRGB(x, y, calculateColor(new float[]{weight1, weight2, weight3}).getRGB());
                }

                edgeTopRight += dxTopRight;
                edgeRightLeft += dxRightLeft;
                edgeLeftTop += dxLeftTop;

                weight1 += dxWeight1;
                weight2 += dxWeight2;
                weight3 = 1.0f - weight1 - weight2;
            }
        }
    }

    private double[] calculateWeights(int x, int y) {
        double denominator = (leftVertex.y() - rightVertex.y()) * (topVertex.x() - rightVertex.x()) +
                (rightVertex.x() - leftVertex.x()) * (topVertex.y() - rightVertex.y());

        double a = ((leftVertex.y() - rightVertex.y()) * (x - rightVertex.x()) +
                (rightVertex.x() - leftVertex.x()) * (y - rightVertex.x())) / denominator;
        double b = ((rightVertex.y() - topVertex.y()) * (x - rightVertex.x()) +
                (topVertex.x() - rightVertex.x()) * (y - rightVertex.y())) / denominator;
        double c = 1.0 - a - b;
        return new double[]{a, b, c};
    }

    private boolean isInside(double[] weights) {
        return weights[0] >= 0 && weights[1] >= 0 && weights[2] >= 0;
    }

    private Color calculateColor(float[] weights) {
        Color c1 = topVertex.color();
        Color c2 = rightVertex.color();
        Color c3 = leftVertex.color();

        int red = (int) (c1.getRed() * weights[0] + c2.getRed() * weights[1] + c3.getRed() * weights[2]);
        int green = (int) (c1.getGreen() * weights[0] + c2.getGreen() * weights[1] + c3.getGreen() * weights[2]);
        int blue = (int) (c1.getBlue() * weights[0] + c2.getBlue() * weights[1] + c3.getBlue() * weights[2]);

        return new Color(red, green, blue);
    }

    private int edgeFunction(int x, int y, Vertex a, Vertex b) {
        return (b.x() - a.x()) * (y - a.y()) - (b.y() - a.y()) * (x - a.x());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    private void saveToFile() {
        try {
            File outputfile = new File("triangle_output.png");
            ImageIO.write(image, "png", outputfile);
            System.out.println("Image saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getArea() {
        return area;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Triangle color interpolation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Triangle triangle = new Triangle(
                    new Vertex(400, 0, Color.RED),
                    new Vertex(800, 800, Color.GREEN),
                    new Vertex(0, 600, Color.BLUE)
            );
            frame.add(triangle);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        });
    }
}
