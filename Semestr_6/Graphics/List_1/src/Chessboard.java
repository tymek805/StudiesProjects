import java.awt.*;
import java.awt.image.BufferedImage;

public class Chessboard {
    public static void main(String[] args) {
        String filename = "chessboard.bmp";
        int xRes = 200;
        int yRes = 200;
        int size = 20;
        Color colorEven = new Color(255, 255, 255);
        Color colorOdd = new Color(0, 0, 0);

        BufferedImage image = fromParams(xRes, yRes, size, colorEven, colorOdd);
        Utils.saveToFile(image, filename);
    }

    static BufferedImage fromParams(int xRes, int yRes, int size, Color colorEven, Color colorOdd) {
        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        boolean even = false;
        for (int y = 0; y < yRes; y++) {
            if (y % size == 0) {
                even = !even;
            }

            for (int x = 0; x < xRes; x++) {
                if (x % size == 0) {
                    even = !even;
                }
                image.setRGB(x, y, even ? colorEven.getRGB() : colorOdd.getRGB());
            }
        }
        return image;
    }
}
