import java.awt.*;
import java.awt.image.*;

public class Rings {
    public static void main(String[] args) {
        String filename = "rings.bmp";
        int circleWidth = 10;
        int xRes = 200;
        int yRes = 200;

        BufferedImage image = fromParams(xRes, yRes, circleWidth, true);
        Utils.saveToFile(image, filename);
    }

    static BufferedImage fromParams(int xRes, int yRes, int width, boolean blur) {
        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        int x_c = xRes / 2;
        int y_c = yRes / 2;

        int black = new Color(0, 0, 0).getRGB();
        int white = new Color(255, 255, 255).getRGB();

        for (int y = 0; y < yRes; y++) {
            for (int x = 0; x < xRes; x++) {
                double d = Math.sqrt((y - y_c) * (y - y_c) + (x - x_c) * (x - x_c));
                if (blur) {
                    int intensity = (int) Utils.intensity(d, width);
                    int color = new Color(intensity, intensity, intensity).getRGB();
                    image.setRGB(x, y, color);
                } else {
                    int r = (int) d / width;
                    if (r % 2 == 0) {
                        image.setRGB(x, y, black);
                    } else {
                        image.setRGB(x, y, white);
                    }
                }
            }
        }
        return image;
    }
}
