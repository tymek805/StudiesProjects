import java.awt.*;
import java.awt.image.BufferedImage;


public class Grid {
    public static void main(String[] args) {
        String filename = "grid.bmp";
        Color colorGrid = new Color(255, 255, 255);
        Color colorBg = new Color(0, 0, 0);
        int xRes = 200;
        int yRes = 200;
        int lineWidth = 5;
        int xSpacing = 10;
        int ySpacing = 10;

        BufferedImage image = fromParams(xRes, yRes, lineWidth, xSpacing, ySpacing, colorGrid, colorBg);
        Utils.saveToFile(image, filename);
    }

    static BufferedImage fromParams(int xRes, int yRes, int width, int xSpacing, int ySpacing, Color colorGrid, Color colorBg) {
        BufferedImage image = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < yRes; y++) {
            for (int x = 0; x < xRes; x++) {
                if ((x % (xSpacing + width)) < width || (y % (ySpacing + width)) < width) {
                    image.setRGB(x, y, colorGrid.getRGB());
                } else {
                    image.setRGB(x, y, colorBg.getRGB());
                }
            }
        }

        return image;
    }
}
