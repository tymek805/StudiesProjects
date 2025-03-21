import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageAlteration {
    public static void main(String[] args) {
        BufferedImage image1 = Utils.imageFromPath("pp_logo.bmp");
        BufferedImage image2 = Utils.imageFromPath("pwr_logo.bmp");

        String pattern = "grid"; // [chessboard, grid, rings]
        String filename = "image_alteration_" + pattern + ".bmp";

        int xRes = image1.getWidth();
        int yRes = image1.getHeight();

        BufferedImage mask = Mask.buildMask(pattern, xRes, yRes);
        BufferedImage outputImage = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < yRes; y++) {
            for (int x = 0; x < xRes; x++) {
                Color color = new Color(mask.getRGB(x, y));
                outputImage.setRGB(x, y, color.equals(Color.WHITE) ? image1.getRGB(x, y) : image2.getRGB(x, y));
            }
        }
        Utils.saveToFile(outputImage, filename);
    }
}
